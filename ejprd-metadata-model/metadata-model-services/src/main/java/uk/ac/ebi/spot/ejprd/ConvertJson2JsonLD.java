package uk.ac.ebi.spot.ejprd;

import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;
import org.apache.jena.atlas.json.JsonParseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;





public class ConvertJson2JsonLD {

                String s;
        private String ConvertJson2JsonLD () {

                try {
                        // Object jsonObject=com.github.jsonldjava.utils.JsonUtils.fromString ( sw.toString () );
                        InputStream inputStream = new FileInputStream( "sample.json" );
                        Object jsonObject = inputStream;
                        JsonLdOptions options=new JsonLdOptions ();
                        options.format="application/jsonld";

                        final Map<String, Object> nses = new HashMap<String, Object>();
                        nses.put ( "dcterms", "http://purl.org/dc/terms/" );
                        nses.put ( "@base", "http://purl.org/ejp-rd/vocabulary/" );
                        nses.put ( "ejp", "http://purl.org/ejp-rd/vocabulary/" );
                        nses.put ( "rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#" );
                        nses.put ( "rdfs", "http://www.w3.org/2000/01/rdf-schema#" );
                        nses.put ( "owl", "http://www.w3.org/2002/07/owl#" );
                        nses.put ( "xsd", "http://www.w3.org/2001/XMLSchema#" );
                        nses.put ( "dct", "http://purl.org/dc/terms/" );
                        nses.put ( "dc", "http://purl.org/dc/elements/1.1/" );
                        nses.put ( "dcat", "http://www.w3.org/ns/dcat#" );
                        nses.put ( "vcard", "http://www.w3.org/ns/dcat#" );
                        nses.put ( "foaf", "http://xmlns.com/foaf/0.1/" );
                        nses.put ( "umbel", "http://umbel.org/umbel#" );

                        final Map <String, Object> ctx=new HashMap <String, Object> ();
                        ctx.put ( "@context", nses );

                        Object compact = JsonLdProcessor.compact ( jsonObject, ctx, options );

                        s = JsonUtils.toPrettyString ( compact );
                } catch (JsonParseException | IOException e) {
                        e.printStackTrace ();
                } catch (JsonLdError e) {
                        e.printStackTrace ();
                }
                return s;
        }

}







//
//public class ConvertJson2JsonLD {
//
//        String s;
//        private String convertJson2JsonLd () throws IOException, JsonLdError {
//
//
//        InputStream inputStream = new FileInputStream("sample.json");
//
//        Object jsonObject = JsonUtils.fromInputStream(inputStream);
//
//        JsonLdOptions options = new JsonLdOptions();
//
//        options.format = "application/jsonld";
//
//        final Map<String, Object> nses = new HashMap<String, Object>();
//        nses.put("dcterms", "http://purl.org/dc/terms/");
//        nses.put("@base", "http://purl.org/ejp-rd/vocabulary/");
//        nses.put("ejp", "http://purl.org/ejp-rd/vocabulary/");
//        nses.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
//        nses.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
//        nses.put("owl", "http://www.w3.org/2002/07/owl#");
//        nses.put("xsd", "http://www.w3.org/2001/XMLSchema#");
//        nses.put("dct", "http://purl.org/dc/terms/");
//        nses.put("dc", "http://purl.org/dc/elements/1.1/");
//        nses.put("dcat", "http://www.w3.org/ns/dcat#");
//        nses.put("vcard", "http://www.w3.org/ns/dcat#");
//        nses.put("foaf", "http://xmlns.com/foaf/0.1/");
//        nses.put("umbel", "http://umbel.org/umbel#");
//
//        final Map<String, Object> ctx = new HashMap<String, Object>();
//        ctx.put("@context", nses);
//
//        Object compact = JsonLdProcessor.compact(jsonObject, ctx, options);
//
//        s = toPrettyString(compact);
//
//            return null;
//        }
//}
