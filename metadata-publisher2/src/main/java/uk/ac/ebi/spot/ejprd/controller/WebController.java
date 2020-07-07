package uk.ac.ebi.spot.ejprd.controller;

import com.github.jsonldjava.core.DocumentLoader;
import com.github.jsonldjava.core.JsonLdError;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uk.ac.ebi.spot.ejprd.dto.Data;
import uk.ac.ebi.spot.ejprd.metadata.publisher.ConvertJsonToJsonLD;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
public class WebController {
    @GetMapping("/metadata-publisher")
    public String publisher(Model model) throws IOException, JsonLdError {

        ConvertJsonToJsonLD convertJsonToJsonLD = new ConvertJsonToJsonLD();
       // DocumentLoader documentLoader = new DocumentLoader();
       // documentLoader.loadDocument("https://raw.githubusercontent.com/EBISPOT/ejprd-metadata-models/master/EJPRD/ejprd_metadata_model/metadata_publisher/resources/contexts/ejp_vocabulary_context.json");
        List<Data> dataList =  convertJsonToJsonLD.convertfile();
        model.addAttribute("compact", dataList);
        return "index";
    }

    @GetMapping("/metadata-visualiser")
    public String visualiser(){

        return "my data visauliser";

    }
}


