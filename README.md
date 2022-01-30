## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)

## General info
Currency calculator is project that can change given Euros to other 
currencies.

	
## Technologies
Project is created with:
* JDK 1.8.0
* Apache Maven 3.6.3

Java libraries used:
* JUnit 5.8.1
* org.w3c.dom 2.3.0-jaxb-1.0.6
	
## Setup
There are 2 methods to run this project:

### Use Apache Maven to package project and run created jar
To run this project you need to install Apache Maven 3.6.3 or higher. Then navigate to 
main folder of project and run console. Next create package using command:
```
mvn package
```
This should create a snapshot of project with it current version. To run this jar
use command:
```
java -cp target/CurrencyCalculator-1.0-SNAPSHOT.jar Main
```
This example is for version 1.0. If project doesn't run check .jar file name in target
folder and if it's not the same - use it name instead of 
`CurrencyCalculator-1.0-SNAPSHOT.jar` in command.

### Use IntelliJ IDEA in version 2020.2 or newer
As IntelliJ has build-in plugin for Maven projects, you can simply run project by 
running Main class in it. To open Maven project in IntelliJ follow this guide:  
[Opening Maven Projects is Easy as Pie](https://blog.jetbrains.com/idea/2008/03/opening-maven-projects-is-easy-as-pie/)


### If project still won't start
* Look for .xml file in src/main/resources folder. If it's not there project won't run!
To write your own xml file use this as template: 
[Template XML file](https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml)
* In same folder config.properties is needed to run project. It specify path to XML file
which you can change if you need to. In case of changes to path, XML file should be in
specified folder, not in resources!
##Features
* Calculate how much other currency you have (it takes Euro as converted currency)
* Update your rates to up-to-date by changing xml file
* Currencies names are sorted alphabetical to search quicker for currency

###To do
* Add more tests
* Add reverse conversion (other currency to EUR)
