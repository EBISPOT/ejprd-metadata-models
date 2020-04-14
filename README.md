# Metadata for EJP rare disease patient registries, biobanks and catalogs

This is for publishing [European Joint Programme (EJP) for Rare Disease](http://www.ejprarediseases.org) virtual platform metadata for Biobanks and Catalogs metadata, through the  extraction, conversion, RDF schema and validating the data RDF data using the ShExC.

 We are also working to align with similar schema standardisation efforts such as [RD connect semantic model](https://github.com/LUMC-BioSemantics/Rare-Disease-Semantic-Model), [schema.org](https://schema.org), [bioschemas](https://bioschemas.org), [MIABIS](https://github.com/MIABIS/miabis/wiki) and [GA4GH](https://www.ga4gh.org) (see also [schema blocks](https://schemablocks.org) and [phenopackets](http://phenopackets.org). A proposed semantic model for the Common Data Elements can be found [here](https://github.com/LUMC-BioSemantics/ERN-common-data-elements).

This diagram illustrates the basic idea of the metadata publication workflow. Components are independent and can be used and new blocks can be defined that inherit from parent blocks.

# WORKFLOW FOR EJP-RD METADATA PUBLISHING
![schema](https://github.com/ejprd-metadata-model/resource-metadata/shex_schema/EJP_RD_Metadata.png)


This is the entire codebase for the EJP-RD Metadata publishers. The model is based on and builds on existing standards, such as the [European Rare Disease Registry Infrastructure](https://eu-rd-platform.jrc.ec.europa.eu) and the [Common Data Elements](http://www.erare.eu/sites/default/files/SetCommonData-EU%20RD%20Platform_CDS%20_final.pdf) from the rare disease community and other more generalised standards for data sharing such as the W3C [DCAT vocabulary](https://www.w3.org/TR/vocab-dcat/).

EJP-RD Metadata standard is build around two major standards - [JSON](https://json-schema.org/) and [RDF](https://www.w3.org/RDF/) standard models for data exchange.



* [ejp-rd/api]() -
* [JSON2RDF]() -
* [RDF-Schema]() -
* [ShEx]()-
* [ShACl]()-
* [Metadata-publication]() -
* [SPARQL-QUERY]()-
* [QUERY-OUTPUT]() -
