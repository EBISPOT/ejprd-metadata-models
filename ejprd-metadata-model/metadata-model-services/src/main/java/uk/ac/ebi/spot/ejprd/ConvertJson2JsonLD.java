package uk.ac.ebi.spot.ejprd;

import static org.apache.jena.riot.Lang.JSONLD;
import org.apache.jena.riot.Lang.*;

public class ConvertJson2JsonLD {


    private String asJson () {

        try {
           // Object jsonObject=com.github.jsonldjava.utils.JsonUtils.fromString ( sw.toString () );
            java.io.InputStream inputStream = new java.io.FileInputStream ( pathname + fdir + "edit_json_schema/sample.json" );
            Object jsonObject = com.github.jsonldjava.utils.JsonUtils.fromInputStream( inputStream );
            com.github.jsonldjava.core.JsonLdOptions options=new com.github.jsonldjava.core.JsonLdOptions ();
            options.format="application/jsonld";

            final java.util.Map <String, Object> nses = new java.util.HashMap <String, Object> ();
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

            final java.util.Map <String, Object> ctx=new java.util.HashMap <String, Object> ();
            ctx.put ( "@context", nses );

            Object compact=com.github.jsonldjava.core.JsonLdProcessor.compact ( jsonObject, ctx, options );

            s=com.github.jsonldjava.utils.JsonUtils.toPrettyString ( compact );
        } catch (com.fasterxml.jackson.core.JsonParseException e) {
            e.printStackTrace ();
        } catch (java.io.IOException e) {
            e.printStackTrace ();
        } catch (com.github.jsonldjava.core.JsonLdError e) {
            e.printStackTrace ();
        }
        return s;
    }

}

