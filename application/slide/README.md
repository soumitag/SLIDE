### SLIDE Windows Installation Guide

This manual is a step-by-step installation and configuration guide for running SLIDE on Microsoft Windows. 

1. **System requirements**

   SLIDE has been tested on various systems. The table below shows a typical system configuration that would work well with SLIDE:  
    * Operating System    - Windows 10 or Windows 7  
    * CPU                 - Intel Xeon CPU E5-1620 v4 @ 3.50 GHz, Intel Core i7-6700HQ CPU @2.60 GHz, Intel Core i7-4600U CPU @2.10 GHz     
    * Memory              - 16 GB RAM  
    * Web browser         - Microsoft Internet Explorer (recommended)

2. **Prerequisites**

   SLIDE requires the following software to be available on the system before it can be configured to run:  

    * Java Development Kit (JDK) -	(http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)  
    * GlassFish Server	-	(http://download.oracle.com/glassfish/4.1.1)  
    * MongoDB	-			(https://www.mongodb.com/download-center#community)  
    * Python	-			(https://anaconda.org/anaconda/python)  
    * Numpy	-			(https://anaconda.org/anaconda/numpy)  
    * Scipy	-			(https://anaconda.org/anaconda/scipy)  
    * fastcluster	-			(https://anaconda.org/conda-forge/fastcluster)  

   SLIDE has been tested on the following versions of these dependencies:
    * Java Development Kit (JDK) -	1.8 
    * GlassFish Server	-	4.1.1  
    * MongoDB		- 3.4  
    * Python		- 3.5.2  

3. **SLIDE Download and Configuration**

   SLIDE can be downloaded from (https://github.com/soumitag/SLIDE). To install SLIDE locally on your PC (without the source code), download only the *application\slide* folder. Let the *slide* folder be located on your PC at *C:\my favourite software\slide*.  
   To setup SLIDE:  
     1. Open a Command Prompt (preferably as administrator)
     2. Run the *configure_slide.bat* file located in the *slide* folder
     3.	To run the .bat file from command prompt issue the following command:
     	*"C:\my favourite software\slide\configure_slide.bat"* assuming that the slide folder was downloaded on your PC to *C:\my favourite software\slide*
     4. On running the .bat file you will be prompted to enter the following information:  
        a. Path to Java Installation Directory  
           If the path to the target java.exe on your system is *C:\Program Files\Java\jdk1.8.0_102\bin\java.exe*, provide the Java Installation Path as *C:\Program Files\Java\jdk1.8.0_102*  
        b. Path to GlassFish Server Installation Directory   
           Do the same as above
        c. Path to the *bin* folder in MongoDB Installation Directory  
           If the path to the *bin* folder in the target MongoDB installation is *C:\Program Files\MongoDB\Server\3.4\bin*, provide this as the path.
           Note: Unlike the paths for Java, GlassFish Server and Python installation directories, the path for MongoDB must include the bin folder
        d. Path to Python Installation Directory  
           Provide the directory which contains python.exe
      5. Add the JDK path in asenv.bat file of GlassFish Server   
         By adding the following line to the *asenv.bat* file   
         SET AS_JAVA=*C:\Program Files\Java\jdk1.8.0_102*
         (where SET AS_JAVA=*C:\Program Files\Java\jdk1.8.0_102* is the path to your JDK installation) 
         The asenv.bat file can be found in the *glassfish\config* folder inside GlassFish installation directory. For instance, if GlassFish is installed on your PC at: *C:\Program Files\glassfish-4.1.1* the asenv.bat file can be found at *C:\Program Files\glassfish-4.1.1\glassfish\config\asenv.bat*
	 Note: The location of asenv.bat can vary with different versions of GlassFish. 
	 
	 
3. **Quick Start Guide**

     To start SLIDE run *slide\bin\start-slide.bat*. To start using SLIDE open Microsoft Internet Explorer and go to (http://localhost:8080/VTBox/).  
     To stop SLIDE run *slide\bin\stop-slide.bat*.

         



	
