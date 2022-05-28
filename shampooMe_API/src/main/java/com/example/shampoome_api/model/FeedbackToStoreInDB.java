package com.example.shampoome_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedbackToStoreInDB {

    private String nickName;
    private String matriculationNumber;
    private int overallSatisfaction;
    private int priceSatisfaction;
    private String comment;

    public String getNickName() {
        return nickName;
    }

    public String getMatriculationNumber() {
        return matriculationNumber;
    }

    public int getOverallSatisfaction() {
        return overallSatisfaction;
    }

    public int getPriceSatisfaction() {
        return priceSatisfaction;
    }

    public String getComment() {
        return comment;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("nickName")
    private void unpackNestedNickName(Map<String,Object> nickNameObj) {
        this.nickName = (String)nickNameObj.get("value");
    }
    @SuppressWarnings("unchecked")
    @JsonProperty("matriculationNumber")
    private void unpackNestedMatriculationNumber(Map<String,Object> matriculationNumberObj) {
        this.matriculationNumber = (String)matriculationNumberObj.get("value");
    }
    @SuppressWarnings("unchecked")
    @JsonProperty("overallSatisfaction")
    private void unpackNestedOverallSatisfaction(Map<String,Object> overallSatisfactionObj) {
        this.overallSatisfaction = (int)overallSatisfactionObj.get("value");
    }
    @SuppressWarnings("unchecked")
    @JsonProperty("priceSatisfaction")
    private void unpackNestedPriceSatisfaction(Map<String,Object> priceSatisfactionObj) {
        this.priceSatisfaction = (int)priceSatisfactionObj.get("value");
    }
    @SuppressWarnings("unchecked")
    @JsonProperty("comment")
    private void unpackNestedComment(Map<String,Object> commentObj) {
        this.comment = (String)commentObj.get("value");
    }
}
