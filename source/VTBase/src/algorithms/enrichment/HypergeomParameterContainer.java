/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.enrichment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.math3.distribution.HypergeometricDistribution;
import utils.Sorter;

/**
 *
 * @author soumita
 */
public class HypergeomParameterContainer implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public int population_size;
   
    public ArrayList <String> featlist_names;
    public ArrayList <String> funcgrp_names;
    
    public HashMap <String, Integer> featlist_size_map;                           // maps feature list name - > small n; list1 -> 20
    public HashMap <String, Integer> funcgrp_size_map;                       // maps func grp name - > big K; 0022967_bp -> 10
    
    public HashMap <List <String>, Integer> funcgrp_featlist_smallk_map;     // maps func grp name, feature list name - > small ks; 0022967_bp,list1 -> 4
    public HashMap <List <String>, Double> funcgrp_featlist_pvalue_map;      // maps func grp name, feature list name - > small ks; 0022967_bp,list1 -> 4
    
    public boolean[] funcgrp_mask;
    public int nonmasked_funcgrp_count;
    
    public HypergeomParameterContainer(int population_size) {
        
        this.population_size = population_size;
        
        featlist_names = new ArrayList <String> ();
        featlist_size_map = new HashMap <String, Integer> ();
        
        funcgrp_names = new ArrayList <String> ();
        funcgrp_size_map = new HashMap <String, Integer> ();
        
        funcgrp_featlist_smallk_map = new HashMap <List <String>, Integer> ();
        
        nonmasked_funcgrp_count = 0;
    }
    
    public void addFeatureList (String feature_list_name, int size) {
        featlist_names.add(feature_list_name);
        featlist_size_map.put(feature_list_name, size);
    }
    
    public void addFunctionalGroup (String functional_group_name, int size) {
        funcgrp_names.add(functional_group_name);
        funcgrp_size_map.put(functional_group_name, size);
    }
    
    public void incrementFunctionalGroupSize (String functional_group_name) {
        if (containsFunctionalGroup(functional_group_name)) {
            int val = funcgrp_size_map.get(functional_group_name);
            funcgrp_size_map.put(functional_group_name, ++val);
        } else {
            addFunctionalGroup (functional_group_name, 1);
        }
    }
    
    public boolean containsFunctionalGroup (String functional_group_name) {
        return funcgrp_size_map.containsKey(functional_group_name);
    }
    
    public void addFuncGrpFeatListAssoc (String functional_group_name, String feature_list_name, int size) {
        List <String> key = makeKey(functional_group_name, feature_list_name);
        funcgrp_featlist_smallk_map.put(key, size);
        //System.out.println(functional_group_name + ", " + feature_list_name);
    }
    
    public boolean containsFuncGrpFeatListAssoc (String functional_group_name, String feature_list_name) {
        List <String> key = makeKey(functional_group_name, feature_list_name);
        return funcgrp_featlist_smallk_map.containsKey(key);
    }
    
    public void incrFuncGrpFeatListAssoc (String functional_group_name, String feature_list_name) {
        if (containsFuncGrpFeatListAssoc(functional_group_name, feature_list_name)) {
            List <String> key = makeKey(functional_group_name, feature_list_name);
            int val = funcgrp_featlist_smallk_map.get(key);
            funcgrp_featlist_smallk_map.put(key, ++val);
        } else {
            addFuncGrpFeatListAssoc (functional_group_name, feature_list_name, 1);
        }
    }
    
    public int[] getParameters_n_k_N_K (String feature_list_name, String functional_group_name) {
        
        int[] n_k_N_K = new int[4];
        
        n_k_N_K[0] = featlist_size_map.get(feature_list_name);
        
        List <String> key = makeKey(functional_group_name, feature_list_name);
        if (funcgrp_featlist_smallk_map.containsKey(key)) {
            n_k_N_K[1] = funcgrp_featlist_smallk_map.get(key);
        } else {
            n_k_N_K[1] = 0;
        }
        n_k_N_K[2] = population_size;
        n_k_N_K[3] = funcgrp_size_map.get(functional_group_name);
        
        return n_k_N_K;
    }
    
    public HashMap <List <String>, Double> computeHyperGeom(HashMap <Integer, String> list_order_map) {

        funcgrp_featlist_pvalue_map = new HashMap <List <String>, Double> ();
        
        for (int i = 0; i < funcgrp_names.size(); i++) {
            
            for (int j = 0; j < featlist_names.size(); j++) {

                int[] n_k_N_K = getParameters_n_k_N_K (featlist_names.get(j), funcgrp_names.get(i));
                
                HypergeometricDistribution hypergeom = new HypergeometricDistribution(n_k_N_K[2], n_k_N_K[3], n_k_N_K[0]);
                
                double p_value = 1 - hypergeom.cumulativeProbability(n_k_N_K[1]);
                
                if (p_value <= Float.MIN_NORMAL) {
                    p_value = Float.MIN_NORMAL;
                }
                
                if (Double.isNaN(p_value) || Double.isInfinite(p_value)) {
                    p_value = 0.99;
                }
                
                double p_value_before_log = p_value;
                p_value = (float) -Math.log10(p_value);
                funcgrp_featlist_pvalue_map.put(makeKey(funcgrp_names.get(i), featlist_names.get(j)), p_value);

                /*
                System.out.println( featlist_names.get(j) + "\t" + 
                                    funcgrp_names.get(i) + "\t" + 
                                    n_k_N_K[0] + "\t" + n_k_N_K[1] + "\t" + n_k_N_K[2] + "\t" + n_k_N_K[3] + "\t" + 
                                    p_value_before_log + "\t" + p_value
                                );
                */
            }
        }

        float[] p_values = new float[funcgrp_names.size()];
        for (int i = 0; i < funcgrp_names.size(); i++) {
            List<String> key = HypergeomParameterContainer.makeKey(funcgrp_names.get(i), list_order_map.get(0));
            p_values[i] = funcgrp_featlist_pvalue_map.get(key).floatValue();
        }

        //sort
        Sorter sorter = new Sorter(p_values);
        int row_order[] = sorter.getSortOrder();
        
        ArrayList <String> sorted_funcgrp_names = new ArrayList <String> ();
        for (int i = 0; i < funcgrp_names.size(); i++) {
            sorted_funcgrp_names.add(funcgrp_names.get(row_order[i]));
        }
        
        ArrayList <String> sorted_featlist_names = new ArrayList <String> ();
        for (int j = 0; j < featlist_names.size(); j++) {
            sorted_featlist_names.add(list_order_map.get(j));
        }
        
        this.funcgrp_names = sorted_funcgrp_names;
        this.featlist_names = sorted_featlist_names;
        
        return funcgrp_featlist_pvalue_map;
    }
    
    public void filterFunctionalGroupList (
            int MIN_BIG_K, int MIN_SMALL_k, double SIGNIFICANCE_LEVEL) {
        
        // generate row mask
        funcgrp_mask = new boolean[funcgrp_names.size()];
        nonmasked_funcgrp_count = 0;
        
        for (int i=0; i<funcgrp_names.size(); i++) {
            
            int K = funcgrp_size_map.get(funcgrp_names.get(i));
            if (K > MIN_BIG_K) {
                
                for (int j=0; j<featlist_names.size(); j++) {
                    int k = 0;
                    List <String> key = makeKey(funcgrp_names.get(i), featlist_names.get(j));
                    if (funcgrp_featlist_smallk_map.containsKey(key)) {
                        k = funcgrp_featlist_smallk_map.get(key);
                    }
                    if (k > MIN_SMALL_k) {
                        
                        double p_value = funcgrp_featlist_pvalue_map.get(makeKey(funcgrp_names.get(i), featlist_names.get(j)));
                        if (p_value >= -Math.log10(SIGNIFICANCE_LEVEL)) {
                            funcgrp_mask[i] = true;
                            nonmasked_funcgrp_count++;
                            break;
                        }
                    }
                }
            }
        }
        
    }
    
    public static List <String> makeKey (String functional_group_name, String feature_list_name) {
         return Collections.unmodifiableList(Arrays.asList(functional_group_name, feature_list_name));
    }
}
