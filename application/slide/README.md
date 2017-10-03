### SLIDE Windows Installation Guide

This manual is a step-by-step installation and configuration guide for running SLIDE on Microsoft Windows. 

1. **System requirements**

SLIDE has been tested on various systems. The table below shows a typical system configuration that would work well with SLIDE:
  *Operating System    - Windows 10 or Windows 7
  *CPU                 - Intel Xeon CPU E5-1620 v4 @ 3.50 GHz 
                         Intel Core i7-6700HQ CPU @2.60 GHz   
                         Intel Core i7-4600U CPU @2.10 GHz    
  *Memory              - 16 GB RAM  
  *Web browser         - Microsoft Internet Explorer (recommended)

2. **Prerequisites**

SLIDE requires the following software to be available on the system before it can be configured to run:

Java Development Kit (JDK)	(http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
GlassFish Server		(http://download.oracle.com/glassfish/4.1.1)
MongoDB				(https://www.mongodb.com/download-center#community)
Python				(https://anaconda.org/anaconda/python)
Numpy				(https://anaconda.org/anaconda/numpy)
Scipy				(https://anaconda.org/anaconda/scipy)
fastcluster			(https://anaconda.org/conda-forge/fastcluster)

SLIDE has been tested on the following versions of these dependencies:
Java Development Kit (JDK)	1.8
GlassFish Server		4.1.1
MongoDB				3.4
Python				3.5.2

3. **SLIDE Download and Configuration**

SLIDE can be downloaded from (https://github.com/soumitag/SLIDE). To install SLIDE locally on your PC (without the source code), download only the *application\slide* folder. Let the *slide* folder be located on your PC at C:\my favourite software\slide. 



To set up SLIDE run configure_slide.bat

SLIDE has the following dependencies:
1. JDK
2. Glassfish Server
3. MongoDB
4. Python
5. Python Modules: numpy, scipy, and fastcluster

Make sure the above dependencies are installed before running configure_slide.bat

SLIDE has been tested using the following versions of the dependencies:
JDK			1.8
Glassfish Server	4.1.1
MongoDB			3.4
Python			3.5.2
Numpy			
Scipy			
Fastcluster		
