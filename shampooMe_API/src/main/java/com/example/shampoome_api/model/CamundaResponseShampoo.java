package com.example.shampoome_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CamundaResponseShampoo {

    private String nickName;
    private String bottleSizeActual;
    private String ingredients;
    private String description;
    private Integer cost;

    public String getNickName() { return nickName; }
    public String getBottleSize() {
        return bottleSizeActual;
    }
    public String getIngredients() {
        return ingredients;
    }
    public String getDescription() {
        return description;
    }
    public Integer getCost() {
        return cost;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("nickName")
    private void unpackNestedNickName(Map<String,Object> nickNameObj) {
        this.nickName = (String)nickNameObj.get("value");
    }
    @SuppressWarnings("unchecked")
    @JsonProperty("ingredients")
    private void unpackNestedIngredients(Map<String,Object> ingredientsObj) {
        this.ingredients = (String)ingredientsObj.get("value");
    }
    @SuppressWarnings("unchecked")
    @JsonProperty("description")
    private void unpackNestedDescription(Map<String,Object> descriptionObj) {
        this.description = (String)descriptionObj.get("value");
    }
    @SuppressWarnings("unchecked")
    @JsonProperty("bottleSizeActual")
    private void unpackNestedBottleSize(Map<String,Object> bottleSizeObj) {
        this.bottleSizeActual = (String)bottleSizeObj.get("value");
    }
    @SuppressWarnings("unchecked")
    @JsonProperty("cost")
    private void unpackNestedCost(Map<String,Object> costObj) {
        this.cost = (Integer)costObj.get("value");
    }
}
