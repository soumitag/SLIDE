/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

/**
 *
 * @author soumita
 */
public class ReadConfig {
    
    public static HashMap <String, String> getSlideConfig(String installPath) {
        return loadConfig(installPath + File.separator + "config" + File.separator + "slide-config.txt");
    }
    
    private static HashMap <String, String> loadConfig (String filepath) {
        
        HashMap <String, String> nameValuePairs = new HashMap <String, String> ();
                
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {
                    String name;
                    switch(count) {
                        case 0:     name = "install-dir";
                                    break;
                        case 1:     name = "java-dir";
                                    break;
                        case 2:     name = "glassfish-dir";
                                    break;
                        case 3:     name = "mongodb-dir";
                                    break;
                        case 4:     name = "python-dir";
                                    break;
                        default:    name = "";
                                    break;
                    }
                    nameValuePairs.put(name, line.trim());
                    count++;
                }
            }
            
            br.close();
            nameValuePairs.put("py-module-path", nameValuePairs.get("install-dir") + File.separator + "src");
            return nameValuePairs;
            
        } catch (Exception e) {
            System.out.println("Error reading config file:");
            System.out.println(e);
            return null;
        }
    }
}
