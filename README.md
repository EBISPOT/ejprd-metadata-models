# Metadata for EJP rare disease patient registries, biobanks and catalogs

This is for publishing [European Joint Programme (EJP) for Rare Disease](http://www.ejprarediseases.org) virtual platform metadata for Biobanks and Catalogs metadata, through the  extraction, conversion, RDF schema and validating the data RDF data using the ShExC.

 We are also working to align with similar schema standardisation efforts such as [RD connect semantic model](https://github.com/LUMC-BioSemantics/Rare-Disease-Semantic-Model), [schema.org](https://schema.org), [bioschemas](https://bioschemas.org), [MIABIS](https://github.com/MIABIS/miabis/wiki) and [GA4GH](https://www.ga4gh.org) (see also [schema blocks](https://schemablocks.org) and [phenopackets](http://phenopackets.org). A proposed semantic model for the Common Data Elements can be found [here](https://github.com/LUMC-BioSemantics/ERN-common-data-elements).

This diagram illustrates the basic idea of the metadata publication workflow. Components are independent and can be used and new blocks can be defined that inherit from parent blocks.

# WORKFLOW FOR EJP-RD METADATA PUBLISHING
![schema](https://github.com/S2Ola/ejprd-metadata-model/blob/master/images/EJP-RD-Metadata.png)
This is the entire codebase for the EJP-RD Metadata publishers. The model is based on and builds on existing standards, such as the [European Rare Disease Registry Infrastructure](https://eu-rd-platform.jrc.ec.europa.eu) and the [Common Data Elements](http://www.erare.eu/sites/default/files/SetCommonData-EU%20RD%20Platform_CDS%20_final.pdf) from the rare disease community and other more generalised standards for data sharing such as the W3C [DCAT vocabulary](https://www.w3.org/TR/vocab-dcat/).

EJP-RD Metadata standard is build around two major standards - [JSON](https://json-schema.org/) and [RDF](https://www.w3.org/RDF/) standard models for data exchange.

The metadata core schema as shown for the EJP_RD virtual platform(VP) displayed below:
##  Metadata core schema for VP
![Metadata vp_model](https://github.com/S2Ola/ejprd-metadata-model/blob/master/images/vp_model.gif)

##  EJP-RD Catalog schema blocks
![EJP schema blocks](https://github.com/S2Ola/ejprd-metadata-model/blob/master/images/ejprdSchemaBlocks.png)


* The Schema in JSON format
  * The Catalog Entity [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/json_schema/ejp-rdJSONSchemaModels.json)
  * The Catalog-Patient-Registry [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/json_schema/catalog-patientregistry.json)
  * The Catalog-BioBank [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/json_schema/catalog-biobank.json)
  * The StudyDesign [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/json_schema/studydesign.json)
  * The Biological-Sample [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/json_schema/biological-sample.json)
  * The Biological-Sample-Patient [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/json_schema/biological-sample-patient.json)
  * The Organisation [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/json_schema/organisation.json)
  * The Date-Time [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/json_schema/date-time.json)
  * The Property-Value [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/json_schema/property-value.json)
  * The Location [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/json_schema/location.json)
  * The EUPID [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/json_schema/eupid.json)
  * The Code [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/json_schema/code.json)
  * The Coding-System [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/json_schema/coding-system.json)

* The Validators - ShEx Constrains Schemas
  * The ShExC for Catalog-BioBank [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/shex_schema/ShExJ/Catalog-BioBank-shape)
  * The ShExC for Catalog [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/shex_schema/ShExJ/catalog_shape)
  * The ShExC for Catalog-PatientRegistry [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/shex_schema/ShExJ/catalog-patient-registryshape)
  * The ShExC for EJP-RD Metadata Core [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/shex_schema/ShExJ/ejprd-metadata-shape)

* The JSON to RDF converter [here](https://github.com/S2Ola/ejprd-metadata-model/tree/master/JSON2RDF)

  The jar file can be invoke from the command line using the following command :
  " cat file.json | java -jar json2rdf.jar https://www.ejprarediseases.org/metadata# | riot --formatted=TURTLE "




    * Metadata Schema for VP in JSON format [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/docs/metadata_core_schema.txt)
    * Metadata Schema for VP in JSON format [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/docs/metadata_core_schema.txt)


* The application/schema+json for the EJP-RD Schema block models demonstrating the JSON schema data model is recorded [here](https://github.com/S2Ola/ejprd-metadata-model/blob/master/docs/ejp-rdJSONSchemaModels.txt).
