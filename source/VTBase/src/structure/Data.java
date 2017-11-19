/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import algorithms.enrichment.EnrichmentAnalysis;
import algorithms.enrichment.HypergeomParameterContainer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.List;

import utils.Utils;
import utils.MongoDBConnect;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import utils.UserInputParser;

import vtbase.DataParsingException;

import data.transforms.Imputer;
import data.transforms.Normalizer;

/**
 *
 * @author soumitag
 */
public final class Data implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final int REPLICATE_HANDLING_NONE = 0;
    public static final int REPLICATE_HANDLING_MEAN = 1;
    public static final int REPLICATE_HANDLING_MEDIAN = 2;
    
    // each Sample object is a samples or a sample replicate group
    public ArrayList <Sample> samples;
    
    // each row or gene is a Feature object
    public ArrayList <Feature> features;
    
    // Mappings for column access
    public ArrayList <String> sampleNames;
    public ArrayList <String> timeStamps;
    public HashMap <String, ArrayList<String>> sampleNameToTimeMap;
    public HashMap <String, ArrayList<String>> timeToSampleNameMap;
    public HashMap <String, ArrayList<Integer>> sampleToColumnMap;
    
    // For faster access
    public HashMap <String, Integer> sampleNameToArrayListPositionMap;
    
    // the data
    public float[][] raw_data;
    public DataCells datacells;     // replicate handled data
    
    // miscellaneous experiment information
    public String species;
    public String exptype;
    public boolean isTimeSeries;
    public boolean isDataLogTransformed;
    
    // entrez_id to gene name mapping
    // entrezGeneMap is shared across all (sub-)analyses
    public HashMap <String, ArrayList <String>> entrezGeneMap;

    public float CLIPPING_MIN;
    public float CLIPPING_MAX;
    
    public String [] current_sample_names;
    
    public float RAW_DATA_MIN;
    public float RAW_DATA_NONZERO_MIN;
    public double[] DATA_MIN_MAX;
    
    public Data() {}    // used in cloneDB and for testing
    
    public Data(String filename, 
                int impute_type,
                String delimiter, 
                boolean hasHeader,
                String sample_series_mapping_filename,
                int start_row,
                int end_row,
                int[] data_height_width,    // the height in data_height_width includes header rows if any
                ArrayList <Integer> metacols, 
                int genesymbolcol, 
                int entrezcol, 
                String species,
                boolean isTimeSeries,
                boolean logTransformData,
                int column_normalization_strategy,
                int row_normalization_strategy,
                int replicate_handling,
                int imputeK
    ) throws DataParsingException {
        
        this.species = species;
        this.isTimeSeries = isTimeSeries;

        String[] colheaders = getColHeaders (hasHeader, filename, delimiter, data_height_width);
        //processSampleInfo(colheaders, datacols, exptype, ordcol, sample_info);
        
        createSampleSeriesMappings(sample_series_mapping_filename, isTimeSeries, colheaders);
        processSampleInfo(colheaders);
        
        String[][] genesymbol_entrez_ids = loadData(filename, start_row, end_row, data_height_width, delimiter, hasHeader, genesymbolcol, entrezcol, impute_type);
        processMetaCols(genesymbol_entrez_ids);
        
        processNonMetaCols(logTransformData);
        //transformData(heatmap_normalization_type, clustering_normalization_type);
        transformData(column_normalization_strategy, row_normalization_strategy);
        
        this.DATA_MIN_MAX = computeDataRange();
        
    }
    
    public Data(float[][] p_values,
                ArrayList <String> feature_list_names,
                ArrayList <String> functional_group_names,
                HashMap <String, String[]> functional_group_details,
                boolean[] functional_group_mask,
                int nonmasked_funcgrp_count,
                String species,
                boolean isTimeSeries,
                boolean logTransformData,
                int column_normalization_strategy,
                int row_normalization_strategy,
                int replicate_handling
    ) throws DataParsingException {
        
        this.species = species;
        this.isTimeSeries = isTimeSeries;

        //String[] colheaders = getColHeaders (hasHeader, filename, delimiter, data_height_width);
        
        this.raw_data = p_values;
        
        SampleMappings sm = new SampleMappings();
        for (int i=0; i<feature_list_names.size(); i++) {
            String sample_name = feature_list_names.get(i);
            sm.sampleNames.add(sample_name);
            ArrayList <Integer> col_ids = new ArrayList<Integer>();
            col_ids.add(i);
            sm.sampleToColumnMap.put(sample_name, col_ids);
        }
        
        this.sampleNames = sm.sampleNames;
        this.sampleToColumnMap = sm.sampleToColumnMap;
        
        String[] colheaders = new String[feature_list_names.size()];
        for (int i=0; i<feature_list_names.size(); i++) {
            colheaders[i] = feature_list_names.get(i);
        }
        
        processSampleInfo(colheaders);
        
        String[][] genesymbol_entrez_ids = new String[nonmasked_funcgrp_count][2];
        int count = 0;
        for (int i=0; i<functional_group_names.size(); i++) {
            if (functional_group_mask[i]) {
                String[] qualified_functional_name = functional_group_details.get(functional_group_names.get(i));
                genesymbol_entrez_ids[count][0] = qualified_functional_name[0];
                genesymbol_entrez_ids[count][1] = qualified_functional_name[1];
                count++;
            }
        }
        
        //processMetaCols(genesymbol_entrez_ids);
        entrezGeneMap = new HashMap<String, ArrayList<String>>();
        features = new ArrayList<Feature>();
        for (int i = 0; i < genesymbol_entrez_ids.length; i++) {
            Feature f = new Feature();
            f.setGeneSymbol(genesymbol_entrez_ids[i][1]);
            f.setEntrezID(genesymbol_entrez_ids[i][0]);
            f.setAliases(new ArrayList<String>());
            features.add(f);

            ArrayList<String> value = new ArrayList<String>();
            value.add(genesymbol_entrez_ids[i][1]);
            entrezGeneMap.put(genesymbol_entrez_ids[i][0], value);
        }
        
        processNonMetaCols(functional_group_mask, nonmasked_funcgrp_count);
        //transformData(heatmap_normalization_type, clustering_normalization_type);
        transformData(column_normalization_strategy, row_normalization_strategy);
        
        this.DATA_MIN_MAX = computeDataRange();
        
    }
    
    public void reprocessData_FeatureLevel(
                ArrayList <String> selectedSampleNames,
                boolean logTransformData,
                int column_normalization_strategy,
                int row_normalization_strategy,
                int replicate_handling,
                String groupBy,
                String clipping_type,
                float clip_min,
                float clip_max
    ) throws DataParsingException {
        
        setClippingRange(clipping_type, clip_min, clip_max);
        processNonMetaCols(selectedSampleNames, logTransformData, replicate_handling, groupBy);
        transformData(column_normalization_strategy, row_normalization_strategy);
        
        this.DATA_MIN_MAX = computeDataRange();
    }
    
    public void reprocessData_GroupLevel(
                ArrayList <String> selectedSampleNames,
                int column_normalization_strategy,
                int row_normalization_strategy,
                int replicate_handling,
                String groupBy,
                String clipping_type,
                float clip_min,
                float clip_max,
                EnrichmentAnalysis ea
    ) throws DataParsingException {
        
        String[][] genesymbol_entrez_ids = new String[ea.testParams.nonmasked_funcgrp_count][2];
        int count = 0;
        for (int i=0; i<ea.testParams.funcgrp_names.size(); i++) {
            if (ea.testParams.funcgrp_mask[i]) {
                String[] qualified_functional_name = ea.funcgrp_details_map.get(ea.testParams.funcgrp_names.get(i));
                genesymbol_entrez_ids[count][0] = qualified_functional_name[0];
                genesymbol_entrez_ids[count][1] = qualified_functional_name[1];
                count++;
            }
        }
        
        //processMetaCols(genesymbol_entrez_ids);
        entrezGeneMap = new HashMap<String, ArrayList<String>>();
        features = new ArrayList<Feature>();
        for (int i = 0; i < genesymbol_entrez_ids.length; i++) {
            Feature f = new Feature();
            f.setGeneSymbol(genesymbol_entrez_ids[i][1]);
            f.setEntrezID(genesymbol_entrez_ids[i][0]);
            f.setAliases(new ArrayList<String>());
            features.add(f);

            ArrayList<String> value = new ArrayList<String>();
            value.add(genesymbol_entrez_ids[i][1]);
            entrezGeneMap.put(genesymbol_entrez_ids[i][0], value);
        }
        
        processNonMetaCols(ea.testParams.funcgrp_mask, ea.testParams.nonmasked_funcgrp_count);
        //transformData(heatmap_normalization_type, clustering_normalization_type);
        transformData(column_normalization_strategy, row_normalization_strategy);
        
        this.DATA_MIN_MAX = computeDataRange();
    }
    
    public String[][] loadData (String filename, 
                        int start_row,
                        int end_row,
                        int[] data_height_width,    // the height in data_height_width includes header rows if any
                        String delimiter, 
                        boolean hasHeader, 
                        int genesymbolcol,
                        int entrezcol,
                        int impute_type
    ) throws DataParsingException {
        
        
        ArrayList <Integer> columns = new ArrayList <Integer> ();
        for (int i=0; i<samples.size(); i++) {
            columns.addAll(samples.get(i).file_column_numbers);
        }
        
        int row_count = end_row - start_row + 1;
        raw_data = new float[row_count][columns.size()];
        String[][] genesymbol_entrez_ids = new String[row_count][2];
        
        //ArrayList <ArrayList <Integer>> impute_indicator = new ArrayList <ArrayList <Integer>> ();
        
        Imputer imputer = new Imputer(raw_data.length, raw_data[0].length);
        
        RAW_DATA_MIN = Float.POSITIVE_INFINITY;
        
        try {
            
            String[][] unprocessed_data = Utils.loadDelimData(filename, delimiter, false);

            int count = 0;
            String[] lineData = null;
            
            int i = 0;
            for (int row=0; row<unprocessed_data.length; row++) {

                if (count >= start_row) {
                    
                    lineData = unprocessed_data[row];
                    
                    for(int j = 0; j < columns.size(); j++) {
                        try {
                            raw_data[i][j] = Float.parseFloat(lineData[columns.get(j)].trim().toLowerCase());
                        } catch (NumberFormatException e) {
                            if (impute_type == data.transforms.Imputer.IMPUTE_NONE) {
                                throw new DataParsingException(
                                    "Error while reading data file. Non-numeric value found at non-metadata column " + (j+1) + ", row" + (i+1)
                                );
                            } else {
                                raw_data[i][j] = Float.NEGATIVE_INFINITY;
                                imputer.markCell(i, j);
                            }
                        }
                    }
                    
                    //impute_indicator.add(impute_cols);
                    if (genesymbolcol > -1) {
                        genesymbol_entrez_ids[i][0] = lineData[genesymbolcol];
                    } else {
                        genesymbol_entrez_ids[i][0] = "";
                    }
                    
                    if (entrezcol > -1) {
                        genesymbol_entrez_ids[i][1] = lineData[entrezcol];
                    } else {
                        genesymbol_entrez_ids[i][1] = "";
                    }
                    
                    i++;
                    
                }
                
                if (count == end_row) {
                    break;
                }
                
                count++;
            }
            
            if (impute_type != Imputer.IMPUTE_NONE) {
                raw_data = imputer.impute(raw_data, impute_type);
            }
            
            //knnImpute(impute_indicator, imputeK, 0.5);
            
            return genesymbol_entrez_ids;
            
        } catch (DataParsingException e) {
            
            throw e;
            
        } catch (Exception e) {
            
            System.out.println("Error reading input data:");
            System.out.println(e);
            throw new DataParsingException("Error while reading data file.");
            
        }
        
    }
    
    public String[] getColHeaders (boolean hasHeader, 
                                   String filename,
                                   String delim,
                                   int[] data_height_width) {
        if (hasHeader) {
            return Utils.getFileHeader(filename, delim);
        } else {
            String[] colheaders = new String[data_height_width[1]];
            for (int i = 0; i < data_height_width[1]; i++) {
                colheaders[i] = "Column_" + i;
            }
            return colheaders;
        }

    }
    
    public void setClippingRange(String clipping_type, float clip_min, float clip_max) {
        if (clipping_type.equals("none")) {
            this.CLIPPING_MIN = Float.NEGATIVE_INFINITY;
            this.CLIPPING_MAX = Float.POSITIVE_INFINITY;
        } else if (clipping_type.equals("absv")) {
            this.CLIPPING_MIN = clip_min;
            this.CLIPPING_MAX = clip_max;
        } else if (clipping_type.equals("ptile")) {
            
            if (clip_max <= clip_min) {
                // bad params, do no clipping
                this.CLIPPING_MIN = Float.NEGATIVE_INFINITY;
                this.CLIPPING_MAX = Float.POSITIVE_INFINITY;
                
            } else {
            
                DescriptiveStatistics stats = new DescriptiveStatistics();
                for (int i = 0; i < raw_data.length; i++) {
                    for (int j = 0; j < raw_data[0].length; j++) {
                        stats.addValue(raw_data[i][j]);
                    }
                }
                
                if (clip_min < 1.0 || clip_min > 99.0) {
                    this.CLIPPING_MIN = Float.NEGATIVE_INFINITY;
                } else {
                    this.CLIPPING_MIN = (float)stats.getPercentile(clip_min);
                }

                if (clip_max < 1.0 || clip_max > 99.0) {
                    this.CLIPPING_MAX = Float.POSITIVE_INFINITY;
                } else {
                    this.CLIPPING_MAX = (float)stats.getPercentile(clip_max);
                }
            }
        }
    }
    
    public final void transformData(int column_normalization_strategy, int row_normalization_strategy){
        
        Normalizer normalizer = new Normalizer(datacells);
        
        switch (column_normalization_strategy) {
            case Normalizer.COL_NORMALIZATION_STANDARDIZE:
                normalizer.standardizeColumns();
                break;
            case Normalizer.COL_NORMALIZATION_AUTOSCALE:
                normalizer.autoscaleColumns();
                break;
            case Normalizer.COL_NORMALIZATION_PARETO:
                normalizer.paretoNormalizeColumns();
                break;
            case Normalizer.COL_NORMALIZATION_NONE:
                break;
            default:
                break;
        }
        
        switch (row_normalization_strategy) {
            case Normalizer.ROW_NORMALIZATION_STANDARDIZED:
                normalizer.standardizeRows();
                break;
            case Normalizer.ROW_NORMALIZATION_AUTOSCALE:
                normalizer.autoscaleRows();
                break;
            case Normalizer.ROW_NORMALIZATION_MEANCENTERED:
                normalizer.meanCenterRows();
                break;
            case Normalizer.ROW_NORMALIZATION_NONE:
                break;
            default:
                break;
        }
        
    }
    
    public Sample getSample (String samplename) {
        int position = sampleNameToArrayListPositionMap.get(samplename);
        return samples.get(position);
    }
    
    public ArrayList <String> getSampleNames() {
        return this.sampleNames;        
    }
    
    public ArrayList <String> getTimePoints() {
        return timeStamps;
    }
    
    public ArrayList <String> getTimePointSampleNames(String timepoint) {
        ArrayList <String> sample_names = this.timeToSampleNameMap.get(timepoint);
        sample_names = this.getOrderedSampleNames(sample_names);
        return sample_names;
    }
    
    /*
    public String[] getSampleNameTimePoints(String samplename) {
        ArrayList <String> time_points = this.sampleNameToTimeMap.get(samplename);
        String[] timePoints = new String[time_points.size()];
        for (int i=0; i<timePoints.length; i++) {
            timePoints[i] = time_points.get(i);
        }
        return timePoints;
    }
    
    */
    
    public String getSpecies(){
        return species;
    }
    
    public String getAssayType(){
        return exptype;
    }
    
    private ArrayList <String> getSamplesGroupedByTimePoint () {
        
        ArrayList <String> ordered_sample_names = new ArrayList <String> ();
        for (int i=0; i<timeStamps.size(); i++) {
            String time_stamp_i = timeStamps.get(i);
            ArrayList <String> sample_names = this.timeToSampleNameMap.get(time_stamp_i);
            ArrayList <String> sample_names_ordered = getOrderedSampleNames(sample_names);
            for (int j = 0; j<sample_names_ordered.size(); j++) {
                ordered_sample_names.add(sample_names_ordered.get(j));
            }
        }
        return ordered_sample_names;
    }
    
    private ArrayList <String> getGroupedSamples () {
        
        // find the unique sample names in the order they were provided
        ArrayList <String> uniqueSampleNames = new ArrayList <String> ();
        StringTokenizer st;
        for (int i=0; i<sampleNames.size(); i++) {
            st = new StringTokenizer(sampleNames.get(i), ",");
            String sample_name_i = st.nextToken();
            if (!uniqueSampleNames.contains(sample_name_i)) {
                uniqueSampleNames.add(sample_name_i);
            }
        }
        
        // extract the sampleNames that match for each sample_name_i and add them to arraylist
        ArrayList <String> sortedSampleNames = new ArrayList <String> ();
        for (int i=0; i<uniqueSampleNames.size(); i++) {
            String sample_name_i = uniqueSampleNames.get(i);
            for (int j=0; j<sampleNames.size(); j++) {
                st = new StringTokenizer(sampleNames.get(j), ",");
                String sample_name_j = st.nextToken();
                if (sample_name_j.equals(sample_name_i)) {
                    sortedSampleNames.add(sampleNames.get(j));
                }
            }
        }
        
        return sortedSampleNames;
        /*
        ArrayList <String> ordered_sample_names = new ArrayList <String> ();
        for (int i=0; i<sampleNames.size(); i++) {
            String sample_name_i = sampleNames.get(i);
            ArrayList <String> timepoints = sampleNameToTimeMap.get(sample_name_i);
            ArrayList <String> timepoints_ordered = getOrderedTimePoints(timepoints);
            for (int j = 0; j<timepoints_ordered.size(); j++) {
                String sample_name_time_point = sample_name_i + "_" + timepoints_ordered.get(j);
                ordered_sample_names.add(sample_name_time_point);
            }
        }
        return ordered_sample_names;
        */
    }
    
    private ArrayList <String> getOrderedTimePoints(ArrayList <String> selectedTimePoints) {
        
        ArrayList <String> selectedTimePoints2 = (ArrayList <String>) selectedTimePoints.clone();
        ArrayList <String> selectedTimePoints_Ordered = new ArrayList <String> ();
        for (int i=0; i<this.timeStamps.size(); i++) {
            String time_stamp_i = timeStamps.get(i);
            if (selectedTimePoints2.contains(time_stamp_i)) {
                selectedTimePoints_Ordered.add(time_stamp_i);
                selectedTimePoints2.remove(time_stamp_i);
            }
        }
        return selectedTimePoints_Ordered;
    }
    
    private ArrayList <String> getOrderedSampleNames(ArrayList <String> selectedSamples) {
        
        ArrayList <String> selectedSamples2 = (ArrayList <String>) selectedSamples.clone();
        ArrayList <String> selectedSamples_Ordered = new ArrayList <String> ();
        for (int i=0; i<this.sampleNames.size(); i++) {
            String sample_name_i = sampleNames.get(i);
            if (selectedSamples2.contains(sample_name_i)) {
                selectedSamples_Ordered.add(sample_name_i);
                selectedSamples2.remove(sample_name_i);
            }
        }
        return selectedSamples_Ordered;
    }
    
    
    
    // called in init; overloaded method called without row filtering mask; used in gene level analysis
    private void processNonMetaCols (boolean logTransformData)
    throws DataParsingException {
        
        // process all columns
        ArrayList <Integer> column_order = new ArrayList <Integer> ();
        for (int i=0; i<samples.size(); i++) {
            column_order.addAll(samples.get(i).raw_data_column_numbers);
        }
        
        int count = 0;
        current_sample_names = new String[column_order.size()];
        for (int i=0; i<samples.size(); i++) {
            for (int j=0; j<samples.get(i).num_replicates; j++) {
                current_sample_names[count++] = samples.get(i).samplename;
            }
        }
        
        datacells = new DataCells(raw_data.length, column_order.size());
        
        for(int i = 0; i < raw_data.length; i++){
            for(int j = 0; j < column_order.size(); j++){
                datacells.dataval[i][j] = raw_data[i][column_order.get(j)];
            }
        }
        
        computeRawDataMin();
        
        if (logTransformData) {
            log2Transform(datacells);
        }
        
    }
    
    // called in init; overloaded method called with row filtering mask; used in functional level analysis
    private void processNonMetaCols (boolean[] feature_mask, int nonmasked_funcgrp_count)
    throws DataParsingException {
        
        // process all columns
        ArrayList <Integer> column_order = new ArrayList <Integer> ();
        for (int i=0; i<samples.size(); i++) {
            column_order.addAll(samples.get(i).raw_data_column_numbers);
        }
        
        int count = 0;
        current_sample_names = new String[column_order.size()];
        for (int i=0; i<samples.size(); i++) {
            for (int j=0; j<samples.get(i).num_replicates; j++) {
                current_sample_names[count++] = samples.get(i).samplename;
            }
        }
        
        datacells = new DataCells(nonmasked_funcgrp_count, column_order.size());
        
        count = 0;
        for(int i = 0; i < raw_data.length; i++){
            if (feature_mask[i]) {
                for(int j = 0; j < column_order.size(); j++){
                    datacells.dataval[count][j] = raw_data[i][column_order.get(j)];
                }
                count++;
            }
        }
        
        computeRawDataMin();

    }
    
    // called in re-init; overloaded method called without row filtering mask; used in gene level analysis
    private void processNonMetaCols (ArrayList <String> selectedSampleNames, boolean logTransformData, int replicate_handling, String groupBy)
    throws DataParsingException {
        
        // process selected columns
        ArrayList <String> ordered_sample_names = null;
        if (this.isTimeSeries) {
            if (groupBy.equalsIgnoreCase("sample")) {
                ordered_sample_names = getGroupedSamples();
            } else if (groupBy.equalsIgnoreCase("time")) {
                ordered_sample_names = getSamplesGroupedByTimePoint();
            } 
        } else {
            ordered_sample_names = sampleNames;
        }
        
        
        int num_samples = 0;
        for (int i=0; i<ordered_sample_names.size(); i++) {
            if (replicate_handling == Data.REPLICATE_HANDLING_NONE) {
                num_samples += getSample(ordered_sample_names.get(i)).num_replicates;
            } else {
                num_samples++;
            }
        }
        
        current_sample_names = new String[num_samples];
        datacells = new DataCells(raw_data.length, num_samples);
        
        int sample_replicate_count = 0;
        for (int i=0; i<ordered_sample_names.size(); i++) {
            
            Sample S = getSample(ordered_sample_names.get(i));
            
            if (replicate_handling == Data.REPLICATE_HANDLING_NONE) {
                
                for (int k = 0; k < S.num_replicates; k++ ) {
                    int col = S.raw_data_column_numbers.get(k);
                    for(int j = 0; j < raw_data.length; j++) {
                        datacells.dataval[j][sample_replicate_count] = clip(raw_data[j][col]);
                    }
                    current_sample_names[sample_replicate_count] = S.samplename;
                    sample_replicate_count++;
                }
                
            } else {
                
                float[] replicate_data_ij = new float[S.num_replicates];
                for(int feature_j = 0; feature_j < raw_data.length; feature_j++) {
                    for (int k = 0; k < S.num_replicates; k++ ) {
                        int col = S.raw_data_column_numbers.get(k);
                        replicate_data_ij[k] = clip(raw_data[feature_j][col]);
                    }
                    datacells.dataval[feature_j][i] = getReplicateSummary(replicate_data_ij, replicate_handling);
                }
                current_sample_names[i] = S.samplename;
            }
        }
        
        computeRawDataMin();
        
        if (logTransformData) {
            log2Transform(datacells);
        }
        
    }
    
    private void computeRawDataMin() {
        RAW_DATA_NONZERO_MIN = Float.POSITIVE_INFINITY;
        RAW_DATA_MIN = Float.POSITIVE_INFINITY;
        for(int i = 0; i < datacells.height; i++ ){
            for(int j = 0; j < datacells.width; j++){
                if (datacells.dataval[i][j] < RAW_DATA_MIN) {
                    RAW_DATA_MIN = datacells.dataval[i][j];
                }
                if (datacells.dataval[i][j] > 0.0 && datacells.dataval[i][j] < RAW_DATA_NONZERO_MIN) {
                    RAW_DATA_NONZERO_MIN = datacells.dataval[i][j];
                }
            }
        }
    }
    
    private float clip(float value) {
        if (value > this.CLIPPING_MAX) {
            return this.CLIPPING_MAX;
        } 
        
        if (value < this.CLIPPING_MIN) {
            return this.CLIPPING_MIN;
        }
        
        return value;
    }
    
    public void log2Transform(DataCells datacells) throws DataParsingException {
        
        if (RAW_DATA_MIN < 0) {
            throw new DataParsingException("Data has negative values. Log 2 transform cannot be applied.");
        }
        
        float log2 = (float)Math.log(2);
        for(int i = 0; i < datacells.height; i++ ){
            for(int j = 0; j < datacells.width; j++){
                if (RAW_DATA_MIN == 0.0) {
                    //datacells.dataval[i][j] = datacells.dataval[i][j] + Float.MIN_VALUE;
                    datacells.dataval[i][j] = datacells.dataval[i][j] + RAW_DATA_NONZERO_MIN;
                }
                datacells.dataval[i][j] = (float)Math.log(datacells.dataval[i][j])/log2;
            }
        }
        isDataLogTransformed = true;               
    }
    
    public void antilog2Transform(Cell[][] datacells){
        
        for(int i = 0; i < datacells.length; i++ ){
            for(int j = 0; j < datacells[0].length; j++){
                datacells[i][j].dataval = Math.pow(2, datacells[i][j].dataval);
            }
        }
        isDataLogTransformed = false;               
    }
    
    public float getReplicateSummary(float[] replicates, int replicate_handling) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for(int i = 0; i < replicates.length; i++) {
            stats.addValue(replicates[i]);
        }
        if (replicate_handling == Data.REPLICATE_HANDLING_MEAN) {
            return (float)stats.getMean();
        } else {
            return (float)stats.getPercentile(50.0);
        }
    }
    
    // creates entrezGeneMap and features
    // entrezGeneMap is shared across all (sub-)analyses
    private void processMetaCols(String[][] genesymbol_entrez_id){
        
        int bad_entrez_ids = -1;
        ArrayList<String> gene_aliases;
        
        entrezGeneMap = new HashMap <String, ArrayList <String>> ();
        MongoDBConnect mdb = new MongoDBConnect(species, "geneMap");
        
        features = new ArrayList<Feature>();
        for (int i = 0; i < genesymbol_entrez_id.length; i++) {
            
            gene_aliases = new ArrayList<String>();

            String genesymbol = genesymbol_entrez_id[i][0].trim().toLowerCase();
            String entrez_id = genesymbol_entrez_id[i][1].trim().toLowerCase();
            
            String parsed_entrez_id = null;

            if (genesymbol.isEmpty() && entrez_id.isEmpty()) {
                
                gene_aliases.add(Integer.toString(bad_entrez_ids));
                parsed_entrez_id = Integer.toString(bad_entrez_ids);
                bad_entrez_ids--;

            } else if (genesymbol.isEmpty() && !(entrez_id.isEmpty())) {
                
                gene_aliases = mdb.getGeneSymbol(entrez_id);
                
                if(gene_aliases.isEmpty()){
                    gene_aliases.add("Unknown_" + entrez_id);
                }
                
                parsed_entrez_id = entrez_id;
                
            } else if (!(genesymbol.isEmpty()) && !(entrez_id.isEmpty())) {
                
                gene_aliases = mdb.getGeneSymbol(entrez_id);
                
                if(gene_aliases.isEmpty()){
                    gene_aliases.add("Unknown_" + entrez_id);
                } else {
                    if (gene_aliases.contains(genesymbol)) {
                        for (int ga = 0; ga < gene_aliases.size(); ga++) {
                            if (gene_aliases.get(ga).equals(genesymbol)) {
                                String temp = gene_aliases.get(ga);
                                gene_aliases.set(ga, gene_aliases.get(0));
                                gene_aliases.set(0, temp);
                                break;
                            }
                        }
                    }
                }
                
                parsed_entrez_id = entrez_id;
                
            } else if (!(genesymbol).isEmpty() && entrez_id.isEmpty()) {
                
                String eid = mdb.getEntrezID(genesymbol);
                
                if (eid != null) {
                    
                    parsed_entrez_id = eid;
                    gene_aliases = mdb.getGeneSymbol(eid);
                    
                    if(gene_aliases.isEmpty()){
                        gene_aliases.add(genesymbol);
                    } else {
                        //ArrayList <String> gene_aliases_cpy = new ArrayList <String> ();
                        if (gene_aliases.contains(genesymbol)){
                            for(int ga = 0; ga < gene_aliases.size(); ga++){
                                if(gene_aliases.get(ga).equals(genesymbol)){
                                    String temp = gene_aliases.get(ga);
                                    gene_aliases.set(ga, gene_aliases.get(0));
                                    gene_aliases.set(0, temp);
                                    break;
                                } 
                            }
                        }
                    }
                    
                } else {
                    
                    parsed_entrez_id = Integer.toString(bad_entrez_ids);
                    gene_aliases.add(genesymbol);
                    bad_entrez_ids--;
                    
                }
            }

            entrezGeneMap.put(parsed_entrez_id, gene_aliases);
            
            Feature f = new Feature();
            
            if(gene_aliases.contains(genesymbol)){
                f.setGeneSymbol(genesymbol);
            } else {
                f.setGeneSymbol(gene_aliases.get(0));
            }
            f.setEntrezID(parsed_entrez_id);
            f.setAliases(gene_aliases);
            
            features.add(f);
        }

        mdb.closeMongoDBConnection();     
    }
    
    private void processSampleInfo (String[] colheaders){
        
        if (this.isTimeSeries) {
            
            samples = new ArrayList <Sample> ();
            sampleNameToArrayListPositionMap = new HashMap <String,Integer>();
            int count = 0;
            for (int i = 0; i < sampleNames.size(); i++) {
                ArrayList <String> timestamps = sampleNameToTimeMap.get(sampleNames.get(i));
                for (int t = 0; t < timestamps.size(); t++) {
                    String name = sampleNames.get(i);
                    Sample s = new Sample(name);
                    ArrayList <Integer> replicate_columns = sampleToColumnMap.get(name);
                    s.setNumReplicates(replicate_columns.size());
                    for (int j=0; j<s.num_replicates; j++) {
                        s.addFileColNumber(replicate_columns.get(j));
                        s.addRawDataColNumber(count);
                        s.addSampleColHeaders(colheaders[replicate_columns.get(j)]);
                        s.timestamp = timestamps.get(t);
                        count++;
                    }
                    samples.add(s);
                    this.sampleNameToArrayListPositionMap.put(s.samplename, i);
                }
            }
            
        } else {
            
            samples = new ArrayList <Sample> ();
            sampleNameToArrayListPositionMap = new HashMap <String,Integer>();
            int count = 0;
            for (int i = 0; i < sampleNames.size(); i++) {
                Sample s = new Sample(sampleNames.get(i));
                ArrayList <Integer> replicate_columns = sampleToColumnMap.get(s.samplename);
                s.setNumReplicates(replicate_columns.size());
                for (int j=0; j<s.num_replicates; j++) {
                    s.addFileColNumber(replicate_columns.get(j));
                    s.addRawDataColNumber(count);
                    s.addSampleColHeaders(colheaders[replicate_columns.get(j)]);
                    count++;
                }
                samples.add(s);
                this.sampleNameToArrayListPositionMap.put(s.samplename, i);
            }
        }
        
        this.exptype = "Independent";
        for (int i=0; i<samples.size(); i++) {
            if (samples.get(i).num_replicates > 1) {
                this.exptype = "Replicate";
                break;
            }
        }
        
    }
    
    private void createSampleSeriesMappings (
            String metafilename, boolean isTimeSeries, String[] colheaders
    ) throws DataParsingException {
        
        SampleMappings sm = UserInputParser.parseSampleMappingsFile(metafilename, isTimeSeries, colheaders);
        
        this.sampleNames = sm.sampleNames;
        this.timeStamps = sm.timeStamps;
        this.sampleToColumnMap = sm.sampleToColumnMap;
        this.sampleNameToTimeMap = sm.sampleNameToTimeMap;
        this.timeToSampleNameMap = sm.timeToSampleNameMap;
    }
    
    
    public double[] computeDataRange() {
        
        double minval = Double.POSITIVE_INFINITY;
        double maxval = Double.NEGATIVE_INFINITY;
        
        for(int i = 0; i < datacells.height; i++){
            for(int j = 0; j < datacells.width; j++){
                if(minval > datacells.dataval[i][j]) {
                    minval = datacells.dataval[i][j];
                }
                if(maxval < datacells.dataval[i][j]) {
                    maxval = datacells.dataval[i][j];
                }
            }
        }
        
        double[] min_max = new double[2];
        min_max[0] = minval;
        min_max[1] = maxval;
        return min_max;
    }
    
    
    
    public Data cloneDB (ArrayList <Integer> filtered_row_indices) 
    throws DataParsingException {
        
        Data cloneData = new Data();
        
        // each Sample object is a samples or a sample replicate group
        cloneData.samples = this.samples;
    
        // Mappings for column access
        cloneData.sampleNames = this.sampleNames;
        cloneData.timeStamps = this.timeStamps;
        cloneData.sampleNameToTimeMap = this.sampleNameToTimeMap;
        cloneData.timeToSampleNameMap = this.timeToSampleNameMap;
        cloneData.sampleToColumnMap = this.sampleToColumnMap;
    
        // For faster access
        cloneData.sampleNameToArrayListPositionMap = this.sampleNameToArrayListPositionMap;
        
        // miscellaneous experiment information
        cloneData.species = this.species;
        cloneData.exptype = this.exptype;
        cloneData.isTimeSeries = this.isTimeSeries;
        cloneData.isDataLogTransformed = this.isDataLogTransformed;
        
        // cloneData.current_sample_names = this.current_sample_names; // now set in processNonMetaCols
        
        cloneData.entrezGeneMap = this.entrezGeneMap;
        
        /*
        ArrayList <Integer> filtered_row_indices = new ArrayList <Integer> ();
        for (int i=0; i<raw_data.length; i++) {
            String eid = features.get(i).entrezId;
            if (filtered_entrez_ids.contains(eid)) {
                filtered_row_indices.add(i);
            }
        }
        */
         
        // the data
        // cloneData.datacells = datacells.getFilteredDataCells(filtered_row_indices);     // replicate handled data
        
        float[][] filtered_raw_data = new float[filtered_row_indices.size()][datacells.width];
        // each row or gene is a Feature object
        ArrayList <Feature> filtered_features = new ArrayList <Feature> ();
        for (int i=0; i<filtered_row_indices.size(); i++) {
            //filtered_raw_data[i] = raw_data[filtered_row_indices.get(i)];
            filtered_raw_data[i] = datacells.dataval[filtered_row_indices.get(i)];
            filtered_features.add(features.get(filtered_row_indices.get(i)));
        }
        cloneData.raw_data = filtered_raw_data;
        cloneData.features = filtered_features;
        
        cloneData.processNonMetaCols(false);
        transformData(Normalizer.COL_NORMALIZATION_NONE, Normalizer.ROW_NORMALIZATION_NONE);
        cloneData.setClippingRange("none", Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
        cloneData.DATA_MIN_MAX = cloneData.computeDataRange();
        
        return cloneData;
    }
    
    public Data cloneDBForEnrichment (EnrichmentAnalysis ea) {
        
        HashMap <List <String>, Double> p_value_map = ea.testParams.funcgrp_featlist_pvalue_map;
        ArrayList <String> featlist_names = ea.testParams.featlist_names;
        ArrayList <String> funcgrp_names = ea.testParams.funcgrp_names;
        
        float[][] p_values = new float[funcgrp_names.size()][featlist_names.size()];
        for (int i=0; i<funcgrp_names.size(); i++) {
            for (int j=0; j<featlist_names.size(); j++) {
                if (ea.testParams.funcgrp_mask[i]) {
                    List <String> key = HypergeomParameterContainer.makeKey(funcgrp_names.get(i), featlist_names.get(j));
                    p_values[i][j] = p_value_map.get(key).floatValue();
                }
            }
        }

        try {
            return new Data(
                    p_values, 
                    ea.testParams.featlist_names, 
                    ea.testParams.funcgrp_names, 
                    ea.funcgrp_details_map,
                    ea.testParams.funcgrp_mask,
                    ea.testParams.nonmasked_funcgrp_count,
                    this.species, 
                    false, 
                    false, 
                    Normalizer.COL_NORMALIZATION_NONE,
                    Normalizer.ROW_NORMALIZATION_NONE, 
                    Data.REPLICATE_HANDLING_NONE);
        } catch (Exception e) {
            return null;
        }
        
    }
}
