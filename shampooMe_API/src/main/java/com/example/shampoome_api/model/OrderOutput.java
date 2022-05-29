package com.example.shampoome_api.model;

import javax.validation.constraints.Pattern;

public class OrderOutput extends OrderInput {

    public String orderId;
    public String NickName;
    public String MatriculationNumber;
    public String ShippingAddress;
    public String ingredients;
    public String description;
    public int price;
    public String bottleSize;
    public boolean isDelayed;

    @Pattern(regexp = "^(order_placed|on_the_way|delivered)$")
    public String status;

}
