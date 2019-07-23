# qmatic-hub-clear-cache
 
#### Installing as windows Service.
To install as windows Service we need 3 compontnts.

Step 1: Clone the repository and open ***install as widndows service*** folder.  
Step 2: Keep the name of 3 files (xml,exe and jar).  
Step 3: type ***your_filename.exe install***


````xml
<?xml version="1.0" encoding="UTF-8"?>
<service>
    <id>api-schedule</id>
    <name>HUB-Clear-schedule</name>
    <description>api-schedule Windows Service</description>
    <executable>java</executable>
    <arguments>-jar "api-schedule.jar"</arguments>
    <logmode>rotate</logmode>
</service>
`````
If you want to change service name. you can change it on the name field of xml file.
