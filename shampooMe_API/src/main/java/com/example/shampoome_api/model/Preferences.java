package com.example.shampoome_api.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class Preferences {

    public String ageOver25;

    public String scalp;
    public String splitEnds;
    public String dandruff;
    public String hairLossMedium;
    public String hairLossStrong;
    public String thinHair;
    public String diet;

    @Pattern(regexp = "^(S|M|L)$")
    public String bottleSize;

    public String fragrance;

    @Pattern(regexp = "^[a-zA-Z]{2,}$")
    public String nickName;

    public String hairType;

}
