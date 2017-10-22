
/**
 *
 * @author Soumita
 */
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class ConfigureSlide {
    
    public static final int WINDOWS_OS  = 1;
    public static final int MAC_OS      = 2;
    public static final int UNIX_OS     = 3;
    
    String install_dir;
    String java_dir;
    int os_type;
    
    public ConfigureSlide(String install_dir, String java_dir, String os) {
        
        if (os.equalsIgnoreCase("WINDOWS")) {
            this.os_type = 1;
        } else if (os.equalsIgnoreCase("MAC")) {
            this.os_type = 2;
        } else if (os.equalsIgnoreCase("UNIX")) {
            this.os_type = 3;
        }
        
        this.install_dir = removeTrailingSlash(removeQuotes(install_dir));
        this.java_dir = removeTrailingSlash(removeQuotes(java_dir));
        
        if (this.os_type == ConfigureSlide.WINDOWS_OS) {
            this.install_dir = replaceSlash(this.install_dir);
            this.java_dir = replaceSlash(this.java_dir);
        }
    }
    
    public void run(){
        
        String glassfish_dir = "";
        String mongo_dir = "";
        String python_dir = "";    
        
        // 0. Search for relevant paths in system PATH variable
    
        // 1. Take input paths
        if (this.os_type == ConfigureSlide.WINDOWS_OS) {
            glassfish_dir = findFile(
                                   "Please Provide Path to Glassfish Server Installation Directory:",
                                   "asadmin.bat",
                                   "Glassfish Server Installation Directory"
                                   );

            mongo_dir = findFile(
                               "Please Provide Path to the \"bin\" folder in MongoDB Installation Directory:",
                               "mongo.exe",
                               "MongoDB Installation Directory"
                               );

            python_dir = findFile(
                                "Please Provide Path to python.exe:",
                                "python.exe",
                                "Python Installation Directory"
                                );
        } else if (this.os_type == ConfigureSlide.UNIX_OS ||
                    this.os_type == ConfigureSlide.MAC_OS) {
            glassfish_dir = findFile(
                                   "Please Provide Path to Glassfish Server Installation Directory:",
                                   "asadmin",
                                   "Glassfish Server Installation Directory"
                                   );

            mongo_dir = findFile(
                               "Please Provide Path to the \"bin\" folder in MongoDB Installation Directory:",
                               "mongo",
                               "MongoDB Installation Directory"
                               );

            python_dir = findFile(
                                "Please Provide Path to python:",
                                "python",
                                "Python Installation Directory"
                                );
            
        }
        
        // 2. Create slide-config.txt
        println("Generating SLIDE configuration files...");
        boolean configCreated = createSlideConfig(java_dir, glassfish_dir, mongo_dir, python_dir);
        if (!configCreated) {
            println("SLIDE configuration failed.");
            System.exit(-1);
        } else {
            println("Done.");
        }
        
        // 3. Add slide-web-config.txt to VTBox.jar
        try {
            println("Generating SLIDE web configuration files...");
            addConfigToJar();
            println("Done.");
        } catch (Exception e) {
            println("SLIDE configuration failed.");
            println(e);
            System.exit(-1);
        }

        // 4. Check if python packages exist
        println("Checking python dependencies...");
        boolean pycheck = checkPythonPackages(python_dir, install_dir);
        if (!pycheck) {
            println("SLIDE configuration failed.");
            System.exit(-1);
        } else {
            println("Done.");
        }
        
        // 7. Add paths to bin/start-slide.bat and similar runners
        println("Creating executables...");
        boolean runnerUpdates = false;
        if (this.os_type == ConfigureSlide.WINDOWS_OS) {
            runnerUpdates = updateRunnersWin(mongo_dir, glassfish_dir);
        } else if (this.os_type == ConfigureSlide.UNIX_OS ||
                    this.os_type == ConfigureSlide.MAC_OS) {
             runnerUpdates = updateRunnersUnix(mongo_dir, glassfish_dir);
        } 
        if (!runnerUpdates) {
            println("SLIDE configuration failed.");
            System.exit(-1);
        } else {
            println("Done.");
        }
        
        // 5. Check if mongodb database folder exists For windows C:/data/db otherwise /data/db
        println("Validating MongoDB storage...");
        boolean mongoStoreCheck = checkMongoStorage(mongo_dir);
        if (!mongoStoreCheck) {
            println("SLIDE configuration failed.");
            System.exit(-1);
        } else {
            println("Done.");
        }
        
        // 6. Import Data Into MongoDB
        println("Creating databases in MongoDB...");
        boolean dataImported = importDataIntoMongoDB(mongo_dir);
        if (!dataImported) {
            println("SLIDE configuration failed.");
            System.exit(-1);
        } else {
            println("Done.");
        }
        
        println("SLIDE configured successfully!");
        if (this.os_type == ConfigureSlide.WINDOWS_OS) {
            println("To start SLIDE run " + install_dir + File.separator + "bin" + File.separator + "start-slide.bat");
            println("To stop SLIDE run " + install_dir + File.separator + "bin" + File.separator + "stop-slide.bat");
        } else if (this.os_type == ConfigureSlide.UNIX_OS ||
                    this.os_type == ConfigureSlide.MAC_OS) {
            println("To start SLIDE run " + install_dir + File.separator + "bin" + File.separator + "start-slide.sh");
            println("To stop SLIDE run " + install_dir + File.separator + "bin" + File.separator + "stop-slide.sh");
        }
    }
    
    private String replaceSlash (String path) {
        String path_c = "";
        String[] result = path.split("/");
        int i = 0;
        for(i = 0; i < result.length-1; i++){
            path_c += result[i] + "\\";
        }
        path_c += result[i];
        return path_c;
    }
    
    private String removeTrailingSlash (String path) {
        if (path.endsWith("\\") || path.endsWith("/")) {
            return path.substring(0, path.length()-1);
        } else {
            return path;
        }
    }
    
    private String findFile(String first_message, String file_to_find, String type) {
        
        Scanner scanner = new Scanner(System.in);
        println(first_message);
        String in_dir = scanner.nextLine();
        if (this.os_type == ConfigureSlide.WINDOWS_OS) {
            in_dir = replaceSlash(removeTrailingSlash(in_dir));
        }
        
        String file_to_find_path = in_dir + File.separator + file_to_find;
        
        File temp = new File(file_to_find_path);
        if(!temp.exists()){
            String err_msg = "Could not find " + file_to_find + " in " + in_dir + ". Please ensure that the Path to " + type + " provided is correct.";
            println(err_msg);
            println("SLIDE configuration failed");
            System.exit(-1);
        } else {
            return in_dir;
        }
        return null;
    }
    
    private String removeQuotes(String path) {
        if (path.startsWith("\"") || path.endsWith("\"")) {
            path = path.replace("\"", "");
        }
        return path;
    }

    private void addConfigToJar() throws IOException {
        
        BufferedInputStream in = null;
        JarOutputStream target = null;
        JarFile source = null;
        
        try {
            
            // create slide-web-config.txt inside slide/lib
            String config_file_path = install_dir + File.separator + "lib" + File.separator + "slide-web-config.txt";
            FileWriter fstream = new FileWriter(config_file_path, false);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write(install_dir);
            out.close();
            
            File configFile = new File(config_file_path);
            
            String old_war_path = install_dir + File.separator + "lib" + File.separator + "VTBox.war";
            File oldWar = new File(old_war_path);
            source = new JarFile(oldWar);
            
            String new_war_path = install_dir + File.separator + "lib" + File.separator + "VTBox2.war";
            File newWar = new File(new_war_path);
            target = new JarOutputStream(new FileOutputStream(newWar));
            
            JarEntry entry = new JarEntry("WEB-INF/slide-web-config.txt");
            entry.setTime(configFile.lastModified());
            target.putNextEntry(entry);
            
            in = new BufferedInputStream(new FileInputStream(configFile));
            byte[] buffer = new byte[1024];
            while (true) {
                int count = in.read(buffer);
                if (count == -1)
                    break;
                target.write(buffer, 0, count);
            }
            //target.closeEntry();
            
            int bytesRead;
            Enumeration enumEntries = source.entries();
            while (enumEntries.hasMoreElements()) {
                entry = (JarEntry) enumEntries.nextElement();
                
                if (!entry.getName().endsWith("slide-web-config.txt")) {
                    target.putNextEntry(entry);
                    InputStream entryStream = source.getInputStream(entry);
                    while ((bytesRead = entryStream.read(buffer)) != -1) {
                        target.write(buffer, 0, bytesRead);
                    }
                }
            }
            
            source.close();
            target.close();
            in.close();
            
            // delete VTBox.war and rename VTBox2.war to VTBox.war
            File vtbox = new File(install_dir + File.separator + "lib" + File.separator + "VTBox.war");
            Files.deleteIfExists(vtbox.toPath());
            
            File vtbox2 = new File(install_dir + File.separator + "lib" + File.separator + "VTBox2.war");
            vtbox2.renameTo(vtbox);

        } finally {
            if (source != null) {
                source.close();
            }
            if (target != null) {
                target.close();
            }
            if (in != null) {
                in.close();
            }
        }
        
    }
    
    public boolean checkPythonPackages(String python_home, String install_dir) {
        
        File out_file = new File(install_dir + File.separator + "src" + File.separator + "import_check.txt");
        
        try {

            Files.deleteIfExists(out_file.toPath());

            ProcessBuilder pb = new ProcessBuilder(
                    python_home + File.separator + "python",
                    install_dir + File.separator + "src" + File.separator + "dependency_checks.py",
                    install_dir + File.separator + "src" + File.separator + "import_check.txt"
            );
            
            Process p = pb.start();

        } catch (Exception e) {
            println(e);
        }

        boolean isDone = true;
        int waiting = 0;
        while (!out_file.exists()) {
            waiting++;
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
                println(e);
            }
            if (waiting > 50) {
                isDone = false;
                break;
            }
        }
        //println("Waited for: " + waiting);
        
        if (isDone == false) {
            println("Python dependency check failed: either python or one of the required packages (numpy, scipy, fastcluster) were not found.");
            return false;
        } else {
            try {
                BufferedReader br = new BufferedReader(new FileReader(out_file));
                String dependency_check_result = br.readLine();
                if (dependency_check_result == null) {
                    println("Python dependency check failed: either python or one of the required packages (numpy, scipy, fastcluster) were not found.");
                    return false;
                } else {
                    if (dependency_check_result.equals("-1")) {
                        println("Python dependency check failed: one of the required packages, fastcluster, was not found.");
                        return false;
                    } else if (dependency_check_result.equals("-2")) {
                        println("Python dependency check failed: one of the required packages, numpy, was not found.");
                        return false;
                    } else if (dependency_check_result.equals("-3")) {
                        println("Python dependency check failed: one of the required packages, scipy, was not found.");
                        return false;
                    } else if (dependency_check_result.equals("-4")) {
                        println("Python dependency check failed: one of the required packages, scipy.cluster.hierarchy, was not found.");
                        return false;
                    } else {
                        println("Python dependency check successful.");
                        return true;
                    }
                }
            } catch (Exception e) {
                println("Python dependency check failed: either python or one of the required packages (numpy, scipy, fastcluster) were not found.");
                return false;
            }
        }
    }
    
    public boolean checkMongoStorage(String mongo_dir) {
        
        String mongo_storage_path = null;
        
        switch (this.os_type) {
            case ConfigureSlide.WINDOWS_OS:
                mongo_storage_path = mongo_dir.substring(0, 1) +
                        ":" + File.separator + "data" + File.separator + "db";
                break;
            case ConfigureSlide.MAC_OS:
            case ConfigureSlide.UNIX_OS:
                mongo_storage_path = File.separator + "data" + File.separator + "db";
                break;
            default:
                break;
        }
        
        if (mongo_storage_path == null) {
            
            println("Unknown OS type");
            return false;
            
        } else {
        
            if( new File(mongo_storage_path).exists() ) {
                return true;
            } else {
                println("Could not find MongoDB storage at " + mongo_storage_path);
                return false;
            }
        }
    }
    
    public boolean updateRunnersWin(String mongo_dir, String glassfish_dir) {
        
        try {
        
            String install_dir_c = replaceSlash(install_dir);
            String mongo_dir_c = replaceSlash(mongo_dir);
            String glassfish_dir_c = replaceSlash(glassfish_dir);

            String[] prefixes = new String[3];
            prefixes[0] = "SET install_dir=" + install_dir_c;
            prefixes[1] = "SET mongodb_dir=" + mongo_dir_c;
            prefixes[2] = "SET glassfish_dir=" + glassfish_dir_c;
            replaceLinesInFile(
                    install_dir + File.separator + "bin" + File.separator + "start-slide.bat", 
                    prefixes, 3, 3
            );

            prefixes = new String[1];
            prefixes[0] = "SET glassfish_dir=" + glassfish_dir_c;
            replaceLinesInFile(
                    install_dir + File.separator + "bin" + File.separator + "stop-slide.bat", 
                    prefixes, 3, 1
            );

            prefixes = new String[1];
            prefixes[0] = "SET mongodb_dir=" + mongo_dir_c;
            replaceLinesInFile(
                    install_dir + File.separator + "bin" + File.separator + "start-mongo.bat", 
                    prefixes, 3, 1
            );
            
            return true;
        
        } catch (Exception e) {
            
            println(e);
            return false;
        }
        
    }
    
    public boolean updateRunnersUnix(String mongo_dir, String glassfish_dir) {
        
        try {
        
            String[] prefixes = new String[3];
            prefixes[0] = "export install_dir=" + install_dir;
            prefixes[1] = "export mongodb_dir=" + mongo_dir;
            prefixes[2] = "export glassfish_dir=" + glassfish_dir;
            replaceLinesInFile(
                    install_dir + File.separator + "bin" + File.separator + "start-slide.sh", 
                    prefixes, 3, 3
            );

            prefixes = new String[1];
            prefixes[0] = "export glassfish_dir=" + glassfish_dir;
            replaceLinesInFile(
                    install_dir + File.separator + "bin" + File.separator + "stop-slide.sh", 
                    prefixes, 3, 1
            );

            prefixes = new String[1];
            prefixes[0] = "export mongodb_dir=" + mongo_dir;
            replaceLinesInFile(
                    install_dir + File.separator + "bin" + File.separator + "start-mongo.sh", 
                    prefixes, 3, 1
            );
            
            return true;
        
        } catch (Exception e) {
            
            println(e);
            return false;
        }
        
    }
    
    private static void replaceLinesInFile(String filename, String[] texts, int start_index, int nLines)
            throws IOException {

        ArrayList<String> file_lines = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new FileReader(filename));

        String line;
        while ((line = br.readLine()) != null) {
            file_lines.add(line);
        }
        br.close();

        for (int i = start_index; i < (start_index + nLines); i++) {
            file_lines.set(i, texts[i - start_index]);
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < file_lines.size(); i++) {
            bw.write(file_lines.get(i));
            bw.newLine();
        }
        bw.close();

    }
    
    public boolean createSlideConfig (String java_dir, String glassfish_dir, String mongo_dir, String python_dir) {
        
        String fname = install_dir + File.separator + "config" + File.separator + "slide-config.txt";
        
        try {
            FileWriter fstream = new FileWriter(fname, false);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("#install-dir");
            out.newLine();
            out.write(install_dir);
            out.newLine();
            out.write("#java-dir");
            out.newLine();
            out.write(java_dir);
            out.newLine();
            out.write("#glassfish-dir");
            out.newLine();
            out.write(glassfish_dir);
            out.newLine();
            out.write("#mongodb-dir");
            out.newLine();
            out.write(mongo_dir);
            out.newLine();
            out.write("#python-dir");
            out.newLine();
            out.write(python_dir);
            out.newLine();
            out.close();
            return true;
        } catch(Exception e){
          println(e);
          println("Unable to create slide configuration file.");
          return false;
        }
        
    }
    
    public boolean importDataIntoMongoDB(String mongo_dir) {
        
        String exec_extn = "";
        if (this.os_type == ConfigureSlide.WINDOWS_OS) {
            exec_extn = ".bat";
        } else if (this.os_type == ConfigureSlide.UNIX_OS ||
                    this.os_type == ConfigureSlide.MAC_OS) {
            exec_extn = ".sh";
        }
        
        try {
            
            ProcessBuilder pb = new ProcessBuilder(
                    install_dir + File.separator + "bin" + File.separator + "start-mongo" + exec_extn);
            Process mongo_start = pb.start();
            
            MongoClient mongoClient = null;
            DB db_mm = null;
            DB db_hs = null;
            
            boolean mongoHasStarted = false;
            int wait_seconds = 0;
            while (!mongoHasStarted) {
                TimeUnit.MILLISECONDS.sleep(1000);
                try {
                    mongoClient = new MongoClient (new ServerAddress("localhost" , 27017), Arrays.asList());
                    db_mm = mongoClient.getDB("geneVocab_MusMusculus");
                    db_hs = mongoClient.getDB("geneVocab_HomoSapiens");
                    mongoHasStarted = true;
                } catch (MongoException me) {
                    wait_seconds++;
                    if (wait_seconds > 60) {
                        mongoHasStarted = true;
                    } else {
                        mongoHasStarted = false;
                    }
                }
            }
        
            String[] collection_names = {"pathwayMap", "goMap2", "geneMap2", "entrezAliasMap", "aliasEntrezMap"};
            
            DBCollection collection;
            DBObject obj;
            for (int i=0; i<collection_names.length; i++) {
                
                print("\tImporting collection " + (i+1) + " of 5...");
                
                // Process MM
                String collection_name = "MM_" + collection_names[i];
                
                collection = db_mm.getCollection(collection_name);
                collection.drop();
                
                collection = db_mm.getCollection(collection_name);
                ArrayList <String> JSONs = readJSONFile(
                        install_dir + File.separator + "db" + File.separator + collection_name + ".json");
                for (String entry : JSONs) {
                    obj = (DBObject) JSON.parse(entry);
                    collection.insert(obj);
                }
                
                // Process HS
                collection_name = "HS_" + collection_names[i];
                
                collection = db_hs.getCollection(collection_name);
                collection.drop();
                
                collection = db_hs.getCollection(collection_name);
                JSONs = readJSONFile(
                        install_dir + File.separator + "db" + File.separator + collection_name + ".json");
                for (String entry : JSONs) {
                    obj = (DBObject) JSON.parse(entry);
                    collection.insert(obj);
                }
                
                println("done");
            }
            
            mongoClient.close();
            
            ProcessBuilder pb2 = new ProcessBuilder(
                    install_dir + File.separator + "bin" + File.separator + "stop-mongo" + exec_extn);
            Process mongo_stop = pb2.start();
            TimeUnit.MILLISECONDS.sleep(3000);
            
            mongo_start.destroyForcibly();
            mongo_stop.destroyForcibly();
            
            return true;
            
        } catch (Exception e) {
            println(e);
            println("Import data into MongoDB failed.");
            return false;
        }
    }
    
    private void println(Object o) {
        System.out.println(o.toString());
    }
    
    private void print(Object o) {
        System.out.print(o.toString());
    }
    
    private ArrayList <String> readJSONFile(String filename) throws IOException {
        
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        ArrayList <String> JSONs = new ArrayList <String> ();

        try {
            while((line = reader.readLine()) != null) {
                JSONs.add(line);
            }
            return JSONs;
        } finally {
            reader.close();
        }
        
    }
}
