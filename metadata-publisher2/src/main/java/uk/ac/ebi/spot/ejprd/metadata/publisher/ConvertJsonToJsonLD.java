package uk.ac.ebi.spot.ejprd.metadata.publisher;

import com.github.jsonldjava.core.DocumentLoader;
import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;
import uk.ac.ebi.spot.ejprd.dto.Data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConvertJsonToJsonLD {

        String dir = System.getProperty("user.dir");

        String fileDirectory = "/data/edit_json_files/";

        private final DocumentLoader documentLoader = new DocumentLoader();

    public List<Data> convertfile() throws IOException, JsonLdError {

        UtilityService utilityService = new UtilityService();


        List<String> filenames = utilityService.listAllFilesInADirectory(dir + fileDirectory);

        Map<String, Object> compact = null;
       // System.out.println(filenames);

        //List<Map<String, Object>> compactList = new ArrayList<>();
        List<Data> dataList = new ArrayList<>();

        for (String fileName : filenames){

            // Open a valid json(-ld) input file
            InputStream inputStream = new FileInputStream(dir + fileDirectory + fileName);
            // Read the file into an Object

            Object jsonObject = JsonUtils.fromInputStream(inputStream);

            // Create a context JSON map containing prefixes and definitions
            //Map<String, Object> context = new HashMap<>();
            //Map<String, object> context = (Map<string, object="">) new DocumentLoader().fromURL(new URL(CONTEXT));


            Map<String, Object> context = (Map<String, Object>) new DocumentLoader();

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












