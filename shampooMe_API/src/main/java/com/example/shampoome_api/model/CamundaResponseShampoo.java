package com.example.shampoome_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CamundaResponseShampoo {

    private String bottleSize;

    public String getId() {
        return bottleSize;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("processInstance")
    private void unpackNested(Map<String,Object> processInstance) {
        //
    }
}
