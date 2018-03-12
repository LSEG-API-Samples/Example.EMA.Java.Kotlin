# Implement EMA Java Application with Kotlin Language

## Overview

This example project shows how to implement the [Elektron Message API Java (EMA Java)](https://developers.thomsonreuters.com/elektron/elektron-sdk-java) applications with Kotlin lanuage. The console applications source code are implemented in Kotlin programming language. All source code will be compiled to Java classes which compatible with Java virtual machine. The applications will run with these Java classes files. 

The example project contains one interactive-Provider application and two consumer applications. 
- The Kotlin_IProvider_200 shows how to implement a basic RSSL Provider application. 
- The Kotlin_Consumer_100 shows how to implement a basic RSSL consumer application.
- The Kotlin_Consumer_220 shows how to implement an advance RSSL consumer application. 

The consumer applications can consume data from Kotlin_IProvider_200 application or other Elektron data sources (ADS server, etc)

![connection diagram](./images/diagram.png "connection diagram")

This example project is compatible with EMA Java 3.1.1 ([Elektron SDK 1.1.1](https://developers.thomsonreuters.com/elektron)) version.

## Kotlin Overview

[Kotlin](https://kotlinlang.org/) is a statically-typed programming language developed by [Jetbrains](https://www.jetbrains.com/) that runs on the Java virtual machine. Kotlin is interoperate with Java code and is reliant on Java code from the existing Java Class Library/Framework. Kotlin syntax aims for reducing Java language verbosity and complexity. Kotlin is a first-class programming language on Android OS. 

Although Kotlin source code can also be compiled to JavaScript and Native code, this example project focus only the JVM target environment.

## Prerequisite
This example requires the following dependencies software.
1. Java 8 SDK
2. [IntelliJ IDEA](https://www.jetbrains.com/idea/) Java IDE version 2017 and above. You can download Intelli IDEA Community Edition from this [page](https://www.jetbrains.com/idea/download/index.html). 
3. If you prefer to use Kotlin [command line compiler](https://github.com/JetBrains/kotlin/releases/latest), you can manual download and install it by following the guide in [Kotlin - Working with the Command Line Compiler page](https://kotlinlang.org/docs/tutorials/command-line.html).
4. [Elektron SDK Java Edition](https://developers.thomsonreuters.com/elektron/elektron-sdk-java). You can download the SDK package via this [link](https://developers.thomsonreuters.com/elektron/elektron-sdk-java/downloads) or via [GitHub](https://github.com/thomsonreuters/Elektron-SDK) page.

## Project Files Structure
- *src/* folder: Applications source code folder
- *Kotlin_Consumer_100.kt*: A basic consumer application source code 
- *Kotlin_Consumer_220.kt*: Consumer application source code that show how to handle incoming data
- *Kotlin_IProvider_200.kt*: Interactive-Provider application source code
- *libs/* folder: Elektron SDK libraries files folder
- *etc/* folder: Eltrkon Data Dictionary files folder (RDMFieldDictionary and enumtype.def files)
- *EmaConfig.xml*: Elektron SDK Java Configuration file
- *.idea/* folder, *Kotlin_EMA.iml*: IntelliJ IDEA project file and folder
- *LICENSE.md*: License declaration file
- *README.md*: readme file

## Running the Project with IntelliJ IDEA
1. Unzip or download the example project folder into a directory of your choice (example, D:\code\Kotlin_proj).
2. Copy all required EMA Java API libraries to the "libs" folder. The required libraries are following
    - ema.jar (&lt;Elektron SDK Java package&gt;/Ema/Libs)
    - upa.jar (&lt;Elektron SDK Java package&gt;/Eta/Libs)
    - upaValueAdd.jar (&lt;Elektron SDK Java package&gt;/Eta/Libs)
    - commons-configuration-1.10.jar (&lt;Elektron SDK Java package&gt;/Ema/Libs/apache)
    - commons-lang-2.6.jar (&lt;Elektron SDK Java package&gt;/Ema/Libs/apache)
    - commons-logging-1.2.jar (&lt;Elektron SDK Java package&gt;/Ema/Libs/apache)
    - org.apache.commons.collections.jar (&lt;Elektron SDK Java package&gt;/Ema/Libs/apache)
    - slf4j-api-1.7.12.jar (&lt;Elektron SDK Java package&gt;/Ema/Libs/SLF4J/slf4j-1.7.12)
    - slf4j-api-1.7.12.jar (&lt;Elektron SDK Java package&gt;/Ema/Libs/SLF4J/slf4j-1.7.12)
3. Open IntelliJ IDEA Java IDE, select **Create New Project**.

    ![intellij](./images/intelliJ_1.png "create new project")

4. Select **Kotlin/JVM** in the Addition Libraries and Frameworks window, then click Next button.

    ![intellij](./images/intelliJ_2.png "select Kotlin/JVM")

5. Set **Project location** folder to the directory from step *1* .

    ![intellij](./images/intelliJ_3.png "set project location")

6. The example project and sub folders will be available in IntelliJ IDEA Java IDE.

    ![intellij](./images/intelliJ_4.png "IntelliJ IDEA Java IDE")

7. Right click on *libs* folder, then choose **Addd as Library...**, then click Ok button to add EMA Java libraries to the project.

    ![intellij](./images/intelliJ_5.png "add EMA Java libraries")

8. Open Kotlin_IProvider_200.kt file, right click and choose **Run** to start the Kotlin_IProvider_200 provider application.

    ![intellij](./images/intelliJ_6.png "Running Kotlin_IProvider_200")

9. The Kotlin_IProvider_200 application will be started and waiting for a consumer application.

    ![intellij](./images/intelliJ_7.png "Running Kotlin_IProvider_200 console")

10. Open Kotlin_IProvider_100.kt file, right click and choose **Run** to start the Kotlin_Consumer_100 consumer application.

    ![intellij](./images/intelliJ_8.png "Running Kotlin_Consumer_100")

11. The Kotlin_Consumer_100 application will be started, then connects and consumes data from Kotlin_IProvider_200 application.

    ![intellij](./images/intelliJ_9.png "Running Kotlin_Consumer_100 console")

## References
For further details, please check out the following resources:
* [Elektron SDK Family site](https://developers.thomsonreuters.com/elektron)
* [Elektron Java API page](https://developers.thomsonreuters.com/elektron/elektron-sdk-java/) on the [Thomson Reuters Developer Community](https://developers.thomsonreuters.com/) web site.
* Developer Webinar: [Introduction to Enterprise App Creation With Open-Source Elektron Message API](https://www.youtube.com/watch?v=2pyhYmgHxlU)
* Elektron Message API Java: [Quick Start](https://developers.thomsonreuters.com/elektron/elektron-sdk-java/quick-start)
* Developer Article: [10 important things you need to know before you write an Elektron Real Time application](https://developers.thomsonreuters.com/article/10-important-things-you-need-know-you-write-elektron-real-time-application)
* [Kotlin programming language](https://kotlinlang.org/)

For any question related to this article or Elektron WebSocket API page, please use the Developer Community [Q&A Forum](https://community.developers.thomsonreuters.com/).

