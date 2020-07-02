package uk.ac.ebi.spot.ejprd.metadata.validator;


import fr.inria.lille.shexjava.schema.Label;
import fr.inria.lille.shexjava.schema.ShexSchema;
import fr.inria.lille.shexjava.schema.parsing.GenParser;
import fr.inria.lille.shexjava.validation.RecursiveValidation;
import fr.inria.lille.shexjava.validation.ValidationAlgorithm;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.apache.commons.rdf.simple.SimpleRDF;
import org.apache.jena.rdf.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MetadataValidator {


    private static Logger logger = LoggerFactory.getLogger(MetadataValidator.class);

    private final static RDF rdfFactory = new SimpleRDF();

    public static void validateTTL(String schemaFileName, String dataFileName, String validationStatusFileName,
                                   String focusNode, String shexShape) {
        ShexSchema schema = getSchema(schemaFileName);
        Model dataModel = getData(dataFileName);

//		ValidationAlgorithm validationAlgorithm = new RefineValidation(schema, (new RDF4J()).asGraph(dataModel));
        ValidationAlgorithm validationAlgorithm = new RecursiveValidation(schema, (new RDF4J()).asGraph((org.eclipse.rdf4j.model.Model) dataModel));

        Label shexShapeLabel = new Label(rdfFactory.createIRI(shexShape));
        RDFTerm focusRDFTerm = rdfFactory.createIRI(focusNode);
        validationAlgorithm.validate(focusRDFTerm, shexShapeLabel);

        String output = validationAlgorithm.getTyping().getStatusMap().toString();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(validationStatusFileName))) {
            writer.write(output, 0, output.length());
            logger.debug("Typing written in " + validationStatusFileName);
        } catch (IOException x) {
            logger.debug("I/O Error while writing the output");
            return;
        }
    }

    private static ShexSchema getSchema (String schemaFileName) {
        ShexSchema schema;
        try {
            schema = GenParser.parseSchema(rdfFactory, Paths.get(schemaFileName));
        } catch (IOException e) {
            logger.error("Error reading the schema file.");
            logger.error("Caused by: ");
            logger.error(e.getMessage(), e);;
            return null;
        } catch (Exception e) {
            logger.error("Error while parsing the schema file. Caused by: " + e.getMessage(), e);
            return null;
        }
        return schema;
    }

    private static Model getData (String dataFileName) {
        Model dataModel;
        try {
            Path dataFilePath = Paths.get(dataFileName);
            InputStream inputStream = Files.newInputStream(dataFilePath);
            dataModel  = (Model) Rio.parse(inputStream, dataFilePath.toUri().toURL().toString(), RDFFormat.TURTLE);
        } catch (Exception e) {
            logger.error("Error while reading the data file.");
            logger.error("Caused by: ");
            logger.error(e.getMessage(), e);
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
