package uk.ac.ebi.spot.ejprd.metadata.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jsonldjava.core.*;
import com.github.jsonldjava.utils.JsonUtils;
import uk.ac.ebi.spot.ejprd.dto.Data;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


public class ConvertJsonToJsonLD {

    private static String userDirectory = System.getProperty("user.dir");
    private static String fileDirectory = "/data/edit_json_files/";
    private static final DocumentLoader documentLoader = new DocumentLoader();

    private static final String contextFileUrl = "/contexts/ejp_vocabulary_context.json";

    private static Stream<URL> getResourceAsStream(String resource, Class clazz) {
        ClassLoader classLoader = getContextClassLoader();
        Stream<URL> urlStream = classLoader.resources(resource);
        if (urlStream == null || urlStream.count() == 0) {
            System.out.println(resource + " not found using ContextClassLoader.");
            urlStream = Stream.of(clazz.getResource(resource));
        }

        return urlStream;
    }


    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    private static String loadResourceByUrl (URL u, String resource) throws IOException, ClassNotFoundException {
        System.out.println("-> attempting input resource: "+resource);
        String jsonString = null;
        if (u != null) {
            String path = u.getPath();
            path = path.replaceFirst("^/(.:/)", "$1");
            System.out.println("    absolute resource path found :\n    " + path);
            jsonString = new String(Files.readAllBytes(Paths.get(path)));
            System.out.println("    file content: "+ jsonString);
        } else {
            System.out.println("    no resource found: " + resource);
        }
        return jsonString;
    }

    private static Map<String, Object> loadContext(String contextFileUrl, Class clazz) {
        Map<String, Object> context = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Stream<URL> urlStream = ConvertJsonToJsonLD.getResourceAsStream(contextFileUrl, clazz);
            Optional<URL> absoluteResourceURLOptional = urlStream.findFirst();
            if (absoluteResourceURLOptional.isPresent()) {
                String jsonString = loadResourceByUrl(absoluteResourceURLOptional.get(), contextFileUrl);
                context = objectMapper.readValue(jsonString, Map.class);
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        return context;
    }

    public static List<Data> convertfile() throws IOException, JsonLdError {
        List<String> filenames = UtilityService.listAllFilesInADirectory(userDirectory + fileDirectory);

        //List<Map<String, Object>> compactList = new ArrayList<>();
        List<Data> dataList = new ArrayList<>();
        for (String fileName : filenames){

            // Open a valid json(-ld) input file
            InputStream inputStream = new FileInputStream(userDirectory + fileDirectory + fileName);
            // Read the file into an Object

            Object jsonObject = JsonUtils.fromInputStream(inputStream);


            Map<String, Object> context = null;
            context = loadContext(contextFileUrl, ConvertJsonToJsonLD.class);

            System.setProperty("com.github.jsonldjava.disallowRemoteContextLoading", "true");
            System.setProperty(DocumentLoader.DISALLOW_REMOTE_CONTEXT_LOADING, "true");
//                    .fromURL(new URL(CONTEXT));
//            // FIXME: jsonld-java 0.3 and later uses DocumentLoader instead of
//            // JSONUtils
//            // Map<String, Object> context = (Map<String, Object>)
//            // JSONUtils.fromURL(new URL(CONTEXT));
//            Object retrievedFrom = context.get("http://purl.org/pav/retrievedFrom");
//            assertNotNull("Did not load context from cache: " + CONTEXT,
//                    retrievedFrom);
//
//        }
            context.put("@context", context );
            // Customise context...
            // Create an instance of JsonLdOptions with the standard JSON-LD options
            JsonLdOptions options = new JsonLdOptions();

            // Customise options...
            // Call whichever JSONLD function you want! (e.g. compact)

            Map<String, Object> compact = null;
            try {
                compact = JsonLdProcessor.compact(jsonObject, context, options);
            } catch (JsonLdError jsonLdError) {
                jsonLdError.printStackTrace();
            }

            Data data = new Data(compact, JsonLdProcessor.toRDF(jsonObject, new JsonLdOptions("")));


            dataList.add(data);

           System.out.println(JsonUtils.toPrettyString(compact));



           System.out.println(JsonLdProcessor.toRDF(jsonObject, new JsonLdOptions("")));


        }
        return dataList;
    }

    public static void main(String args[]) {
        try {

           ConvertJsonToJsonLD.loadContext(contextFileUrl, ConvertJsonToJsonLD.class);
            ConvertJsonToJsonLD.convertfile();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}












