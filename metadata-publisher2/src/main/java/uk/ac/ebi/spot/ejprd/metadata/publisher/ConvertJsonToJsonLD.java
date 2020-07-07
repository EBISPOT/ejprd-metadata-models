package uk.ac.ebi.spot.ejprd.metadata.publisher;

import com.github.jsonldjava.core.*;
import com.github.jsonldjava.utils.JarCacheResource;
import com.github.jsonldjava.utils.JsonUtils;
import uk.ac.ebi.spot.ejprd.dto.Data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.*;


public class ConvertJsonToJsonLD {

    private static String userDirectory = System.getProperty("user.dir");
    private static String fileDirectory = "/data/edit_json_files/";
    private static final DocumentLoader documentLoader = new DocumentLoader();

    private static final String contextFileUrl = "ejp_vocabulary_context.json";


    private JarCacheResource loadContext() {
        JarCacheResource jarCacheResource = null;
        try {
            Enumeration<URL> resourcesEnum = getClass().getClassLoader().getResources(contextFileUrl);

            for (Iterator<String> i = resourcesEnum; i.hasNext();) {
                String item = i.next();
                System.out.println(item);
            }
            jarCacheResource =
                    new JarCacheResource(getClass().getClassLoader().getResource(contextFileUrl));
        }
        catch (IOException ioe) {
            System.out.println(ioe.getStackTrace());
        }
        return jarCacheResource;
    }

    public List<Data> convertfile() throws IOException, JsonLdError {

        List<String> filenames = UtilityService.listAllFilesInADirectory(userDirectory + fileDirectory);

        //List<Map<String, Object>> compactList = new ArrayList<>();
        List<Data> dataList = new ArrayList<>();
        for (String fileName : filenames){

            // Open a valid json(-ld) input file
            InputStream inputStream = new FileInputStream(userDirectory + fileDirectory + fileName);
            // Read the file into an Object

            Object jsonObject = JsonUtils.fromInputStream(inputStream);

            JarCacheResource jarCacheResource = loadContext();
            ObjectInputStream ois = new ObjectInputStream(jarCacheResource.getInputStream());
            Object object = null;

            try {
                object = ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Map<String, Object> context = (Map<String, Object>) object;
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
}












