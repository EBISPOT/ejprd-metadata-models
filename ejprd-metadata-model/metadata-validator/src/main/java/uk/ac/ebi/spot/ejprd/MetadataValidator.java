package uk.ac.ebi.spot.ejprd;




public class MetadataValidator {

        private static Logger logger = LoggerFactory.getLogger ( MetadataValidator.class );

        private final static api.RDF rdfFactory = new rdf.simple.SimpleRDF ();

        public static void validateTTL (String schemaFileName, String dataFileName, String validationStatusFileName, String focusNode, String shexShape) {
            fr.inria.lille.shexjava.schema.ShexSchema schema = getSchema ( schemaFileName );
            org.eclipse.rdf4j.model.Model dataModel = getData ( dataFileName );

            String fdir = "/Users/dipo/Documents/GitHub/ejprd_metadatamodel/checker/";
            String pathname = System.getProperty ( "user.dir" );
            schemaFileName = pathname + fdir + "SchemaInShExc";
            dataFileName = pathname + fdir + "dataInturtle.ttl";
            validationStatusFileName = fdir + "result.ttl";
            focusNode = "<http://example.org/alice> @<http://example.org/User>";
            shexShape = pathname + fdir + "ejprd-metadata_model.shex";

//		ValidationAlgorithm validationAlgorithm = new RefineValidation(schema, (new RDF4J()).asGraph(dataModel));
            fr.inria.lille.shexjava.validation.ValidationAlgorithm validationAlgorithm = new fr.inria.lille.shexjava.validation.RecursiveValidation ( schema, (new org.apache.commons.rdf.rdf4j.RDF4J ()).asGraph ( dataModel ) );

            fr.inria.lille.shexjava.schema.Label shexShapeLabel = new fr.inria.lille.shexjava.schema.Label ( rdfFactory.createIRI ( shexShape ) );
            org.apache.commons.rdf.api.RDFTerm focusRDFTerm = rdfFactory.createIRI ( focusNode );
            validationAlgorithm.validate ( focusRDFTerm, shexShapeLabel );

            String output = validationAlgorithm.getTyping ().getStatusMap ().toString ();
            try (java.io.BufferedWriter writer = java.nio.file.Files.newBufferedWriter ( java.nio.file.Paths.get ( validationStatusFileName ) )) {
                writer.write ( output, 0, output.length () );
                logger.debug ( "Typing written in " + validationStatusFileName );
            } catch (java.io.IOException x) {
                logger.debug ( "I/O Error while writing the output" );
                return;
            }
        }

        private static fr.inria.lille.shexjava.schema.ShexSchema getSchema (String schemaFileName) {
            fr.inria.lille.shexjava.schema.ShexSchema schema;
            String fdir = "/Users/dipo/Documents/GitHub/ejprd_metadatamodel/checker/";
            String pathname = System.getProperty ( "user.dir" );
            schemaFileName = pathname + fdir + "SchemaInShExc";


            try {
                schema = fr.inria.lille.shexjava.schema.parsing.GenParser.parseSchema ( rdfFactory, java.nio.file.Paths.get ( schemaFileName ) );
            } catch (java.io.IOException e) {
                logger.error ( "Error reading the schema file." );
                logger.error ( "Caused by: " );
                logger.error ( e.getMessage (), e );
                ;
                return null;
            } catch (Exception e) {
                logger.error ( "Error while parsing the schema file. Caused by: " + e.getMessage (), e );
                return null;
            }
            return schema;
        }

        private static org.eclipse.rdf4j.model.Model getData (String dataFileName) {
            org.eclipse.rdf4j.model.Model dataModel;
            String fdir = "/Users/dipo/Documents/GitHub/ejprd_metadatamodel/checker/";
            String pathname = System.getProperty ( "user.dir" );

            dataFileName = pathname + fdir + "dataInturtle.ttl";


            try {
                java.nio.file.Path dataFilePath = java.nio.file.Paths.get ( dataFileName );
                java.io.InputStream inputStream = java.nio.file.Files.newInputStream ( dataFilePath );
                dataModel = org.eclipse.rdf4j.rio.Rio.parse ( inputStream, dataFilePath.toUri ().toURL ().toString (), org.eclipse.rdf4j.rio.RDFFormat.TURTLE );
            } catch (Exception e) {
                logger.error ( "Error while reading the data file." );
                logger.error ( "Caused by: " );
                logger.error ( e.getMessage (), e );
                return null;
            }
            return dataModel;
        }



        public static void main(String[] args) {
            try {
                validateTTL(args[0], args[1], args[2], args[3], args[4]);
            } catch (Throwable t) {
                logger.error(t.getMessage(), t);
            }
        }
}





