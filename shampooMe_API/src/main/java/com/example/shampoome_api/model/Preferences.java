package com.example.shampoome_api.model;

public class Preferences {

    private HairColor hairColor;
    private HairLength hairLength;
    private HairStructure hairStructure;
    private HairType hairType;

    public HairColor getHairColor() {
        return hairColor;
    }

    public void setHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
    }

    public HairLength getHairLength() {
        return hairLength;
    }

    public void setHairLength(HairLength hairLength) {
        this.hairLength = hairLength;
    }

    public HairStructure getHairStructure() {
        return hairStructure;
    }

    public void setHairStructure(HairStructure hairStructure) {
        this.hairStructure = hairStructure;
    }

    public HairType getHairType() {
        return hairType;
    }

    public void setHairType(HairType hairType) {
        this.hairType = hairType;
    }

    enum HairColor {black, brown, blonde}
    enum HairLength {Short, medium, neckLine, shoulder, veryLong}
    enum HairStructure {straight, wavy, curly, colly}
    enum HairType {dry, neutral, oily}
}
