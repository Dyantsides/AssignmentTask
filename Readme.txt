## Table of contents
* General info
* Technologies
* Setup

## General info
This project is simple Polish PostFix notation implementation.
	
## Technologies
Project is created with:
* Java version: 1.8
* Junit version: 5.4.0
* Project is created using maven build and dependency tool
	
## Setup
To run this project using the Jar file, do the following:
* Make sure you have java 8 or above version installed in the system/server
* Copy the jar to a location
* Open command prompt
* Navigate to the location of the jar file
* run the command: java -jar <jar file name>
##Import the project in IDE
* Please use import project option from your IDE.
* Provide the root folder path where you unzipped the zip file and import it as a Maven project.
* click finish.  
##To build and run this project using mvn, do the following
* Install maven plugin.
* Go to the project location.
* Type mvn clean install. This should generate a .jar.
* Run the jar file using "java -jar <jar file name>"

##Special Instructions:
* Please type the command in upper case.

##Test cases
* StackMachineTest file contains unit test cases for all operations. 
* All tests are not covered due to time constraints.

##Things could  have been done better (due to lack of time)
* All node and head objects in StackMachineImpl file should be relaced with their respective setters and getters functions.
* Use of entriprise application logging i.e. log4j, slf4j etc instead of console loogging.
* There are still APIs that need to be tested using Junit. i.e. execute() where a file can be passed with operation instructions etc.
* Creating a buils script for the same instead of writing manual instructions.
