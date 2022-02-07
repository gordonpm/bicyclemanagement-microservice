package io.gordonpm.bicyclemanagement.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public class Bicycle {

    @NotEmpty(message = "Id of bicycle cannot be empty")
    private String id;

    @NotEmpty(message = "Vendor of bicycle cannot be empty")
    private String vendor;

    @NotEmpty(message = "Name of bicycle cannot be empty")
    private String name;

    @Positive(message = "Price of bicycle has to be greater than 0")
    private double price;

    public Bicycle(String id, String vendor, String name, double price) {
        this.id = id;
        this.vendor = vendor;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
