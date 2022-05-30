package com.example.computer;

public class Item {
    private String name;
    private Integer price;
    private String size;

    public Item(String name, Integer price, String size) {
        this.name = name;
        this.price = price;
        this.size = size;
    }

    public Item() {

    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }
}
