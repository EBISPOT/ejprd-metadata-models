package uk.ac.ebi.spot.ejprd.metadata.publisher;

import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConvertJsonToJsonLD {

    String dir = System.getProperty("user.dir");

    String fileDirectory = "/EJPRD/ejprd_metadata_model/data/edit_json_files/";


    public void convertfile() throws IOException, JsonLdError {

        UtilityService utilityService = new UtilityService();

        List<String> filenames = utilityService.listAllFilesInADirectory(dir + fileDirectory);


        // System.out.println(filenames);


        for (String fileName : filenames) {

            // Open a valid json(-ld) input file
            InputStream inputStream = new FileInputStream(dir + fileDirectory + fileName);
            // Read the file into an Object

            Object jsonObject = JsonUtils.fromInputStream(inputStream);

            // Create a context JSON map containing prefixes and definitions
            Map<String, Object> context = new HashMap<>();

            context.put("@context", "http://purl.org/ejp-rd/vocabulary/" );
            // Customise context...
            // Create an instance of JsonLdOptions with the standard JSON-LD options
            JsonLdOptions options = new JsonLdOptions();

            // Customise options...
            // Call whichever JSONLD function you want! (e.g. compact)
            Object compact = null;
            try {
                compact = JsonLdProcessor.compact(jsonObject, context, options);
            } catch (JsonLdError jsonLdError) {
                jsonLdError.printStackTrace();
            }

            // Print out the result (or don't, it's your call!)
            System.out.println("File name is : " + fileName);
            System.out.println(JsonUtils.toPrettyString(compact));

            System.out.println(" ===============================================================================");

            System.out.println(JsonLdProcessor.toRDF(jsonObject, new JsonLdOptions("tag:http://purl.org/ejp-rd/vocabulary/")));

            System.out.println(" --------------------------------------------------------------------------------- ");

        }
    }

}




















