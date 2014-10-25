meditrax
========
What is meditrax
----------------
Meditrax is a retail pharmaceutical application that eases the day to day life of pharmaceutical retailers

Importing to eclipse
---------------------
1. Download eclipse for java EE from eclipse .org
2. Add e(fx)clipse plugin to eclipse. If you are using java8 use the latest version. Alternatively you can use the following update site
http://download.eclipse.org/efxclipse/updates-released/0.9.0/site/
3. If you are not using MAC, update the pom.xml file in extfx and jfx-autocomplete-textfield projects with your jfxrt location
4. Build the extfx project using the maven

Running the project
------------------
1. Navigate to build.fxbuild file. Right click on it and click build extfx application. This will create the build.xml ant build.
2. Right click on the ant build file and click run
