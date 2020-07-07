package uk.ac.ebi.spot.ejprd.controller;

import com.github.jsonldjava.core.JsonLdError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.spot.ejprd.dto.Data;
import uk.ac.ebi.spot.ejprd.metadata.publisher.ConvertJsonToJsonLD;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value="/api")
public class ApiController {

    @GetMapping("/metadata-publisher")
    public  List<Data> publisher() throws IOException, JsonLdError {

        ConvertJsonToJsonLD convertJsonToJsonLD = new ConvertJsonToJsonLD();
        List<Data> dataList =  convertJsonToJsonLD.convertfile();

        return dataList;
    }

    @GetMapping("/metadata-visualiser")
    public String visualiser(){

        return "my data visauliser";

    }
}
