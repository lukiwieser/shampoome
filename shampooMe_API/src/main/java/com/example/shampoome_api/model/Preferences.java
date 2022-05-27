package com.example.shampoome_api.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class Preferences {

    @Min(16) @Max(120)
    public int age;

    public String scalp;
    public boolean splitEnds;
    public boolean dandruff;
    public boolean hairLossMedium;
    public boolean hairLossStrong;
    public boolean thinHair;
    public String diet;

    @Pattern(regexp = "^(S|M|L)$")
    public String bottleSize;

    public String fragrance;

    @Pattern(regexp = "^[a-zA-Z]{2,}$")
    public String nickName;

    public String hairType;

}
