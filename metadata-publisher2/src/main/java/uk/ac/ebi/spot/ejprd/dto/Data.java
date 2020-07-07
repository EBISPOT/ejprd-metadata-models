package uk.ac.ebi.spot.ejprd.dto;

import java.util.Map;



public class Data {

    private Map<String, Object> compact;
    private Object json2Rdf;

    public Data(Map<String, Object> compact, Object json2Rdf) {
        this.compact = compact;
        this.json2Rdf = json2Rdf;
    }

    public Map<String, Object> getCompact() {
        return compact;
    }

    public void setCompact(Map<String, Object> compact) {
        this.compact = compact;
    }

    public Object getJson2Rdf() {
        return json2Rdf;
    }

    public void setJson2Rdf(Object json2Rdf) {
        this.json2Rdf = json2Rdf;
    }

    @Override
    public String toString() {
        return "Data{" +
                "compact=" + compact +
                '}';
    }
}
