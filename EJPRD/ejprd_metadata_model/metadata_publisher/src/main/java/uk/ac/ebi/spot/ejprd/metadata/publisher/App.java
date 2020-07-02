package uk.ac.ebi.spot.ejprd.metadata.publisher;

import com.github.jsonldjava.core.JsonLdError;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException, JsonLdError {
        ConvertJsonToJsonLD convertJsonToJsonLD = new ConvertJsonToJsonLD();
        //convertJsonToJsonLD.filename = "data/edit_json_schema/sample.json";
        convertJsonToJsonLD.convertfile();
       // System.out.println(convertJsonToJsonLD);

    }
}


