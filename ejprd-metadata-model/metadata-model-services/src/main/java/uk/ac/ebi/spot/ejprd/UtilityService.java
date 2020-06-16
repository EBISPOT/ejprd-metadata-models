package uk.ac.ebi.spot.ejprd;




public class UtilityService {

    private final static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UtilityService.class);
    private String homeDir = System.getProperty("user.home");
    private com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper ();

    /*************************************************************************************************************
     *                                           DATA SERIALIZER METHODS SECTION                                 *
     ************************************************************************************************************/

    public java.util.List <java.util.Map <String, String>> serializeDataToMaps(String fileName) {

        String fileExtension = getFileExtension(fileName);

        java.util.List <java.util.Map <String, String>> csvMaps = new java.util.ArrayList <> ();

        if (fileExtension.equals("json")) {

            csvMaps = (java.util.List) serializeJSONToMaps(fileName);
        } else {

            System.out.println ("This folder does not contains any json extension file");
        }

        return csvMaps;
    }



    public java.util.Map <String, java.util.List <java.util.Map <String, String>>> serializeAndGroupFileContent(String fileName, String groupColumn) {

        String fileExtension = getFileExtension(fileName);

        java.util.List <java.util.Map <String, String>> csvMaps = new java.util.ArrayList <> ();

        switch (fileExtension){


            case "json":
                csvMaps = (java.util.List) serializeJSONToMaps(fileName);
                break;
        }
        if(csvMaps == null) log.error("Map is null for "+fileName +" in groupCol " +groupColumn);

        java.util.Map <String, java.util.List <java.util.Map <String, String>>> groupedMap = groupDataByColumn(csvMaps, groupColumn);

        return groupedMap;
    }



    public java.util.Map <String, java.util.List <java.util.Map <String, String>>> groupDataByColumn(java.util.List <java.util.Map <String, String>> csvMaps, String groupColumn) {


        java.util.Map <String, java.util.List <java.util.Map <String, String>>> groupedMap = new java.util.LinkedHashMap <> ();

        for (java.util.Map <String, String> rowData : csvMaps){

            java.util.List <java.util.Map <String, String>> tempList = new java.util.ArrayList <> ();

            String rowKey = rowData.get(groupColumn);

            if (groupedMap.get(rowKey) == null) {

                tempList.add(rowData);
                groupedMap.put(rowKey, tempList);
            } else {

                tempList = groupedMap.get(rowKey);
                tempList.add(rowData);

                groupedMap.put(rowKey, tempList);
            }

        }
        return groupedMap;
    }

    /*************************************************************************************************************
     *                                           JSON FILE HANDLER SECTION                                      *
     ************************************************************************************************************/

    // SERIALIZER : CONVERT JSON URL TO JAVA MAPS
    public java.util.List <java.util.Map <String, Object>> serializeJSONToMaps(String jsonFile) {

        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper ();

        java.util.List <java.util.Map <String, Object>> data;

        com.fasterxml.jackson.databind.JsonNode node = readJsonLocal(jsonFile);
        try {
            data = mapper.convertValue(node, java.util.List.class);

        }catch (Exception e){

            java.util.Map <String, Object> json = mapper.convertValue(node, java.util.Map.class);

            String jsonKey = "";
            for (java.util.Map.Entry<String, Object> entry : json.entrySet() ) {      // GET THE JSON KEY
                jsonKey = entry.getKey();
            }

            data = (java.util.List) json.get(jsonKey);
        }

        return data;
    }


    // SERIALIZER : CONVERT JSON URL TO JAVA MAPS
    public java.util.List <java.util.Map <String, Object>> serializeJSONToMaps(String jsonFile, String jsonKey) {

        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper ();

        com.fasterxml.jackson.databind.JsonNode node = readJsonLocal(jsonFile);

        java.util.Map <String, Object> json = mapper.convertValue(node, java.util.Map.class);

        java.util.List <java.util.Map <String, Object>> data = (java.util.List) json.get(jsonKey);

        return data;
    }



    // PARSER : LOAD JSON NODES FROM  REMOTE JSON HTTP URL
    public com.fasterxml.jackson.databind.JsonNode readJsonURL(String apiLink) {

        com.fasterxml.jackson.databind.JsonNode jsonNode = null;
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper ();

        try {

            java.net.URL url = new java.net.URL (apiLink);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            java.io.BufferedReader br = new java.io.BufferedReader (new java.io.InputStreamReader ((conn.getInputStream())));

            jsonNode = mapper.readTree(br);
            conn.disconnect();

        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return jsonNode;

    }


    // PARSER : LOAD JSON NODES FROM  LOCAL JSON
    public com.fasterxml.jackson.databind.JsonNode readJsonLocal(String jsonFileLink) {

        com.fasterxml.jackson.databind.JsonNode jsonNode = null;
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper ();

        try {

            java.io.BufferedReader br = new java.io.BufferedReader (new java.io.FileReader (jsonFileLink));
            jsonNode = mapper.readTree(br);

        }catch (Exception e) {
            log.error("Hey, could not read local directory: {}", jsonFileLink);
        }

        return jsonNode;
    }

    /*************************************************************************************************************
     *                              LOCAL OR REMOTE FILE HANDLERS PARSERS SECTION                               *
     ************************************************************************************************************/

    // PARSER : CONVERT ANY FILE TO INPUTSTREAM
    public java.io.FileInputStream convertFileToStream(String filePath){

        java.io.FileInputStream inputStream = null;
        try {
            inputStream = new java.io.FileInputStream (new java.io.File (filePath));
            log.info("Loading template from : {}", filePath);
        }catch (Exception e) {
            log.error("UtilityService convertFileToStream says Data File "+filePath+" Not Found");
        }

        return inputStream;
    }

    // GET THE EXTENSION OF A FILE
    public String getFileExtension(String fileName){

        String[] check = fileName.split("\\.");
        String fileExtension = check[check.length-1];

        return fileExtension;
    }


    // FILE WRITE: WRITE STRING DATA TO FILE
    public void writeToFile(String data, String destination, Boolean shouldAppend){

        // Write to the file using BufferedReader and FileWriter
        try {
            java.io.BufferedWriter writer = new java.io.BufferedWriter (new java.io.FileWriter (destination, shouldAppend));
            writer.append(data);
            writer.close();

        } catch (Exception e) {}

    }


    // DELETE FILE FROM A LOCAL DIRECTORY
    public Boolean deleteFile(String localDirectory) {

        Boolean report = false;
        try {

            java.nio.file.Path path = java.nio.file.Paths.get(localDirectory);
            java.nio.file.Files.deleteIfExists(path);

            report = true;
        } catch (Exception e) {
        }

        return report;
    }


    public String listAllFilesInADirectory(String directory) {

        String fileNames = "";

        java.io.File folder = new java.io.File (directory);

        java.io.File[] filDir = folder.listFiles();

        if (filDir.length == 0) {

            log.warn("No subdirs found for the universal loader, skipping");
        }
        else {

            for (int i = 0; i < filDir.length; i++) {

                if (filDir[i].isFile()) {

                    fileNames += filDir[i].getName()+"\n";

                }
            }
        }

        log.info(fileNames);

        return "";
    }
    public void mkDirectoryFromFilePathName(String filePath){

        // Get Directory from file path string
        String directoryName = filePath.substring(0, filePath.lastIndexOf("/"));

        // Create Directory if it does not exist
        java.io.File directory = new java.io.File (directoryName);

        if (!directory.exists()){
            directory.mkdirs();
        }
    }


    public  byte[] convertLocalFileToByte(String filePath) {

        byte fileData[] = null;
        java.io.File file = new java.io.File (filePath);

        try (java.io.FileInputStream inputStream = new java.io.FileInputStream (file)) {

            fileData = new byte[(int) file.length()];
            inputStream.read(fileData);
        } catch (Exception e) {
            System.out.println("Exception while reading the file " + e);
        }

        return fileData;
    }



    public java.util.List <java.util.Map> objectArrayListToMapList(java.util.List <Object[]> dataList, java.util.List <String> keys){

        java.util.List <java.util.Map> result = new java.util.ArrayList <> ();

        for (Object[] data : dataList) {

            java.util.Map <String, Object> dataMap = new java.util.LinkedHashMap <> ();
            int count = 0;
            for (Object content : data){

                dataMap.put(keys.get(count), content);
                count++;
            }
            result.add(dataMap);
        }
        return result;
    }



    public com.fasterxml.jackson.databind.JsonNode jsonStringToNode(String jsonString){

        com.fasterxml.jackson.databind.JsonNode jsonNode = null;

        try {
            jsonNode = mapper.readTree(jsonString);
        } catch (Exception e) { }

        return jsonNode;
    }

}


