/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import searcher.Searcher;

/**
 *
 * @author soumita
 */
public class Serializer {
    
    public Serializer() {
        
    }
    
    public void serializeAnalysis(AnalysisContainer analysis, String filepath) {
        
        try (ObjectOutputStream oos
                = new ObjectOutputStream(new FileOutputStream(filepath + File.separator + analysis.analysis_name + ".slide"))) {
            
            analysis.searcher = 1;
            oos.writeObject(analysis);
            System.out.println("Analysis saved.");
            
            // create a searcher object
            analysis.setSearcher(new Searcher(analysis.database.species));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    public AnalysisContainer loadAnalysis(String filepath) {
        
        AnalysisContainer analysis = null;

        try (ObjectInputStream ois
                = new ObjectInputStream(new FileInputStream(filepath))) {

            analysis = (AnalysisContainer) ois.readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return analysis;
    }
    
}
