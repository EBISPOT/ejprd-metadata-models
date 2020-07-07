package uk.ac.ebi.spot.ejprd.metadata.publisher;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


public class UtilityService {


    private static String homeDir = System.getProperty("user.home");
    private final static Logger log = LoggerFactory.getLogger(UtilityService.class);


    /*************************************************************************************************************
     *                                           DATA SERIALIZER METHODS SECTION                                 *
     ************************************************************************************************************/

    public static List<Map<String, String>> serializeDataToMaps(String fileName) {

        String fileExtension = getFileExtension(fileName);

        List<Map<String, String>> csvMaps = new ArrayList<>();

        if (fileExtension.equals("json")) {

            csvMaps = (List) serializeJSONToMaps(fileName);
        } else {

            System.out.println("There are no json file format in this folder");
        }

        return csvMaps;
    }



    public static Map<String, List<Map<String, String>> > serializeAndGroupFileContent(String fileName, String groupColumn) {

        String fileExtension = getFileExtension(fileName);

        List<Map<String, String>> csvMaps = new ArrayList<>();

        switch (fileExtension){

            case "json":
                csvMaps = (List) serializeJSONToMaps(fileName);
                break;

        }
        if(csvMaps == null) log.error("Map is null for "+fileName +" in groupCol " +groupColumn);

        Map<String, List<Map<String, String>> > groupedMap = groupDataByColumn(csvMaps, groupColumn);

        return groupedMap;
    }



    public static Map<String, List<Map<String, String>> > groupDataByColumn(List<Map<String, String>> csvMaps, String groupColumn) {

        Map<String, List<Map<String, String>> > groupedMap = new LinkedHashMap<>();

        for (Map<String, String> rowData : csvMaps){

            List<Map<String, String>> tempList = new ArrayList<>();

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
    public static List<Map<String, Object>> serializeJSONToMaps(String jsonFile) {

        ObjectMapper mapper = new ObjectMapper();

        List data;

        JsonNode node = readJsonLocal(jsonFile);
        try {
            data = mapper.convertValue(node, List.class);

        }catch (Exception e){

            Map<String, Object> json = mapper.convertValue(node, Map.class);

            String jsonKey = "";
            for (Map.Entry<String, Object> entry : json.entrySet() ) {      // GET THE JSON KEY
                jsonKey = entry.getKey();
            }

            data = (List) json.get(jsonKey);
        }

        return data;
    }


    // SERIALIZER : CONVERT JSON URL TO JAVA MAPS
    public static List<Map<String, Object>> serializeJSONToMaps(String jsonFile,String jsonKey) {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = readJsonLocal(jsonFile);

        Map<String, Object> json = mapper.convertValue(node, Map.class);

        List<Map<String, Object>> data = (List) json.get(jsonKey);

        return data;
    }



    // PARSER : LOAD JSON NODES FROM  REMOTE JSON HTTP URL
    public static JsonNode readJsonURL(String apiLink) {

        JsonNode jsonNode = null;
        ObjectMapper mapper = new ObjectMapper();

        try {

            URL url = new URL(apiLink);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            jsonNode = mapper.readTree(br);
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonNode;

    }


    // PARSER : LOAD JSON NODES FROM  LOCAL JSON
    public static JsonNode readJsonLocal(String jsonFileLink) {

        JsonNode jsonNode = null;
        ObjectMapper mapper = new ObjectMapper();

        try {

            BufferedReader br = new BufferedReader(new FileReader(jsonFileLink));
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
    public static FileInputStream convertFileToStream(String filePath){

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(filePath));
            log.info("Loading template from : {}", filePath);
        }catch (Exception e) {
            log.error("UtilityService convertFileToStream says Data File "+filePath+" Not Found");
        }

        return inputStream;
    }



    // PARSER : LOAD FILE CONTENT FROM HTTP URL
    public static String parseURL(String urlStr) {

        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(urlStr);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            log.error("Unable to read from URL " + urlStr, e);
        }
        return sb.toString();
    }



    // LOAD FILE CONTENT FROM LOCAL DIRECTORY
    public static String parseFile(String path) {

        StringBuilder sb = new StringBuilder();

        try {
            Stream<String> stream = Files.lines(Paths.get(path));

            Iterator itr = stream.iterator();
            while (itr.hasNext()) {
                sb.append(itr.next());
            }
        } catch (Exception e) {
            log.error("Failed to load file " + path, e);
        }
        return sb.toString();
    }


    // GET THE EXTENSION OF A FILE
    public static String getFileExtension(String fileName){

        String[] check = fileName.split("\\.");
        String fileExtension = check[check.length-1];

        return fileExtension;
    }


    // FILE WRITE: WRITE STRING DATA TO FILE
    public static void writeToFile(String data, String destination, Boolean shouldAppend){

        // Write to the file using BufferedReader and FileWriter
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(destination, shouldAppend));
            writer.append(data);
            writer.close();

        } catch (Exception e) {}

    }


    // DELETE FILE FROM A LOCAL DIRECTORY
    public static Boolean deleteFile(String localDirectory) {

        Boolean report = false;
        try {

            Path path = Paths.get(localDirectory);
            Files.deleteIfExists(path);

            report = true;
        } catch (Exception e) {
        }

        return report;
    }


    public static  List<String> listAllFilesInADirectory(String directory) {

        List<String> fileNames = new ArrayList<>();
        File folder = new File(directory);
        File[] filDir = folder.listFiles();

        if (filDir.length == 0) {
            System.out.println("No subdirs found for the universal loader, skipping");
        }
        else {
            for (int i = 0; i < filDir.length; i++) {
                if (filDir[i].isFile()) {
                    String fileName = filDir[i].getName();
                    if (getFileExtension(fileName).equals("json")){
                        fileNames.add(fileName);
                    }
                }
            }
        }

        System.out.println(fileNames);
        return fileNames;
    }



















    /*************************************************************************************************************
     *                                          OTHER UTILITIES                                                  *
     ************************************************************************************************************/
    // File to Byte
    public void moveFile(String source,String destination){

        // Create Directory if it does not exist
        this.mkDirectoryFromFilePathName(destination);

        byte[] bytes = convertLocalFileToByte(source);

        Path path = Paths.get(destination);
        try{
            Files.write(path, bytes);
        }catch (Exception e){}

    }

    public void mkDirectoryFromFilePathName(String filePath){

        // Get Directory from file path string
        String directoryName = filePath.substring(0, filePath.lastIndexOf("/"));

        // Create Directory if it does not exist
        File directory = new File(directoryName);

        if (!directory.exists()){
            directory.mkdirs();
        }
    }

    public  byte[] convertLocalFileToByte(String filePath) {

        byte fileData[] = null;
        File file = new File(filePath);

        try (FileInputStream inputStream = new FileInputStream(file)) {

            fileData = new byte[(int) file.length()];
            inputStream.read(fileData);
        } catch (Exception e) {
            System.out.println("Exception while reading the file " + e);
        }

        return fileData;
    }

    public List<Map> objectArrayListToMapList(List<Object[]> dataList, List<String> keys){

        List<Map> result = new ArrayList<>();

        for (Object[] data : dataList) {

            Map<String, Object> dataMap = new LinkedHashMap<>();
            int count = 0;
            for (Object content : data){

                dataMap.put(keys.get(count), content);
                count++;
            }
            result.add(dataMap);
        }
        return result;
    }


    public JsonNode jsonStringToNode(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;

        try {
            jsonNode = mapper.readTree(jsonString);
        } catch (Exception e) { }

        return jsonNode;
    }

}
