/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbase;

import java.util.ArrayList;
import searcher.Searcher;
import searcher.GeneObject;
import searcher.PathwayObject;
import searcher.GoObject;
import utils.MongoDBConnect;
import algorithms.clustering.BinaryTree;
import algorithms.clustering.TreeNode;
import algorithms.enrichment.EnrichmentAnalysis;
import graphics.Heatmap;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.HashMap;
import utils.Utils;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import structure.AnalysisContainer;
import structure.Cell;
import structure.Data;
import structure.Feature;
import structure.SampleMappings;
import utils.UserInputParser;

/**
 *
 * @author soumitag
 */
public class VTBase {

    /**
     * @param args the command line arguments
     */
    
    static ArrayList <ArrayList <Integer>> impute_indicator;
    
    public static void main(String[] args) {
        
        //double p_value = 0.38415;
        //System.out.println("P: " + Math.log10(p_value));
        //float p = (float) -Math.log10(p_value);
        
        /*
        Data data = new Data();
        float[][] raw_data = loadData("C:\\20_May_2017\\Visualization_toolbox\\Visualization_Toolbox_0.0.3\\FlatFilesProcessing_0.0.3\\data\\final_lethal_infection_part_3.txt", 
                1, 11, new int[]{11,13}, "\t", true, 0, 2, 5);
        data.raw_data = raw_data;
        data.knnImpute(impute_indicator, 5, 0.5);
        */
        
        HashMap <String, ArrayList <Integer>> selected_filter_list_maps = new HashMap <String, ArrayList <Integer>> ();
        
        /*
        for (int i=0; i<5; i++) {
            ArrayList <Integer> list_i = new ArrayList <Integer> ();
            for (int j=i*100; j<(i*100)+100; j++) {
                list_i.add(j);
            }
            selected_filter_list_maps.put("List_" + i, list_i);
        }
        */
        
        ArrayList <Integer> row_numbers_a8 = new ArrayList <Integer> ();
        BufferedReader br = null;
        String line = null;
        try {
            br = new BufferedReader(new FileReader("D:\\SOUMITA\\4_Sep_2017\\data\\A8_Row_Numbers.txt"));
            while ((line = br.readLine()) != null) {
                row_numbers_a8.add(Integer.parseInt(line.trim().toLowerCase()));
            }
        } catch (Exception e) {
            System.out.println("Error reading input data:");
            System.out.println(e);
        }
        selected_filter_list_maps.put("A8", row_numbers_a8);
        
        ArrayList <Integer> row_numbers_b7 = new ArrayList <Integer> ();
        try {
            br = new BufferedReader(new FileReader("D:\\SOUMITA\\4_Sep_2017\\data\\B7_Row_Numbers.txt"));
            while ((line = br.readLine()) != null) {
                row_numbers_b7.add(Integer.parseInt(line.trim().toLowerCase()));
            }
        } catch (Exception e) {
            System.out.println("Error reading input data:");
            System.out.println(e);
        }
        selected_filter_list_maps.put("B7", row_numbers_b7);
        
        ArrayList <Feature> features = null;

        try (ObjectInputStream ois
                = new ObjectInputStream(new FileInputStream("D:\\SOUMITA\\4_Sep_2017\\data\\features.obj"))) {

            features = (ArrayList <Feature>) ois.readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        int min_big_K = 5;
        int min_small_k = 4;
        double significance_level = 0.05;
        EnrichmentAnalysis ea = new EnrichmentAnalysis(
                features, selected_filter_list_maps, "mouse", "pathway",
                min_big_K, min_small_k, significance_level, new String[]{"BP"});
        ea.run();
        
        System.out.println("Done.");
        
        /*
        String[] geneNames = {"ABCA5","ADAMTS9","APOL6","BCL2L14","BLZF1","BST2","CXCL10"};
        int bgNum = 20000;
        String species = "human";
        
        EnrichmentAnalysis ea = new EnrichmentAnalysis(bgNum, geneNames, species, "genesymbol", "pathway");
        ea.compute_K();
        ea.compute_k();
        ea.computeHyperGeom();
        */
        
        // TODO code application logic here
        
        /*
        String datafilename = "D:\\ad\\sg\\19_April_2017\\Visualization_toolbox\\Visualization_Toolbox_0.0.3\\FlatFilesProcessing_0.0.3\\data\\final_lethal_infection_part.txt";
        String metafilename = "D:\\ad\\sg\\19_April_2017\\Visualization_toolbox\\Visualization_Toolbox_0.0.3\\FlatFilesProcessing_0.0.3\\data\\final_lethal_infection_SampleMappings.txt";
        
        String[] colheaders = Utils.getFileHeader(datafilename, "\t");
        
        UserInputParser uiParser = new UserInputParser();
        uiParser.parseSampleMappingsFile(metafilename, true, colheaders);
        SampleMappings sm1 = uiParser.getMappings();
        
        uiParser.parseSampleMappingsFile(metafilename, false, colheaders);
        SampleMappings sm2 = uiParser.getMappings();
        
        Heatmap heatmap = new Heatmap();
        double[] row_data = new double[20];
        for (int i=0; i<20; i++) {
            row_data[i] = i + 0.5;
        }
        heatmap.genLinearColorScale(row_data);
        
        row_data = new double[21];
        for (int i=0; i<21; i++) {
            row_data[i] = i;
        }
        heatmap.genLinearColorScale(row_data);
        
        String[][] data = Utils.loadDelimData("D:\\ad\\sg\\phd_project\\Visualization_toolbox\\Visualization_Toolbox_0.0.3\\FlatFilesProcessing_0.0.3\\data\\final_lethal_infection.txt", "\t", true);
        
        Cell[][] datacells = new Cell[data.length][133];
        for(int i = 1; i < data.length; i++){
            for(int j = 4; j < 136; j++){
                datacells[i-1][j-4] = new Cell(Double.parseDouble(data[i][j]));
            }            
        }
        
        //SummaryStatistics stats = new SummaryStatistics();
        //stats.addValue(1);
        //stats.addValue(2);
        //stats.addValue(3);
        //stats.addValue(3);
        
        //System.out.println(stats.getMean());
        
        //MongoDBConnect mdb = new MongoDBConnect("human", "geneMap");
        
        //String searchString = "NAT1,NAT2,NATP,SERPINA3";
        /*String searchString = "81845";
        Searcher search = new Searcher("mouse");
        ArrayList <String> eids = search.processWildCardGenes(searchString, "entrez");
        search.closeMongoDBConnection();
        
        
        //Searcher search = new Searcher_old();
        //ArrayList <PathwayObject> searchResult = search.processWildCardPathwayQuery (searchString, "pathname");
        //ArrayList <ArrayList<GeneObject>> searchResult = search.processingSingleGeneQueryStr (searchString, "genesymbol");
       
                
        double[][] linkage_tree = new double[][]{
                                        {6, 5, 0.5, 2, 1, 2},
                                        {1, 0, 0.75, 2, 6, 7},
                                        {3, 2, 0.80, 2, 4, 5},
                                        {7, 4, 0.85, 3, 1, 3},
                                        {8, 9, 0.90, 4, 4, 7},
                                        {11, 10, 0.95, 7, 1, 7}
                                    };
        
        
        //double[][] leaf_ordering = new double[][]{{4},{5},{6},{2},{3},{0},{1}};
        
        BinaryTree btree = new BinaryTree(linkage_tree, BinaryTree.LARGEST_CHILD_FIRST_LEAF_ORDER);
        
        ArrayList <Integer> sub_tree = btree.breadthFirstSearch(12, 7);
        //.breadthFirstSearch(sub_tree, linkage_tree, 12, 3, 7);
        //btree.breadthFirstSearch(sub_tree, linkage_tree, 11, 12, 7);
        //btree.breadthFirstSearch(sub_tree, linkage_tree, 12, 12, 7);
        
        HashMap <Integer, TreeNode> treeMap = btree.buildSubTree(sub_tree);
        
        btree.computeEdgeCoordinatesPostOrder (treeMap, treeMap.get(12));
        */
        
        
    }
    
    public static float[][] loadData(String filename, 
                        int start_row,
                        int end_row,
                        int[] data_height_width,    // the height in data_height_width includes header rows if any
                        String delimiter, 
                        boolean hasHeader, 
                        int genesymbolcol,
                        int entrezcol,
                        int imputeK) {
        
        ArrayList <Integer> columns = new ArrayList <Integer> ();
        for (int i=3; i<13; i++) {
            columns.add(i);
        }
        
        int row_count = end_row - start_row + 1;
        float[][] raw_data = new float[row_count][columns.size()];
        
        impute_indicator = new ArrayList <ArrayList <Integer>> ();
        
        BufferedReader br = null;
        String line;
        
        try {

            br = new BufferedReader(new FileReader(filename));

            int count = 0;
            String[] lineData = null;
            
            int i = 0;
            while ((line = br.readLine()) != null) {

                if (count >= start_row) {
                    lineData = line.split(delimiter);
                    
                    ArrayList <Integer> impute_cols = new ArrayList <Integer> (0);
                    
                    for(int j = 0; j < columns.size(); j++) {
                        try {
                            raw_data[i][j] = Float.parseFloat(lineData[columns.get(j)]);
                        } catch (NumberFormatException e) {
                            raw_data[i][j] = Float.NEGATIVE_INFINITY;
                            impute_cols.add(j);
                        }
                    }
                    
                    impute_indicator.add(impute_cols);
                    
                    i++;
                }
                
                if (count == end_row) {
                    break;
                }
                
                count++;
            }
            
            return raw_data;
            
        } catch (Exception e) {
            System.out.println("Error reading input data:");
            System.out.println(e);
            return null;
        }
    }
}
