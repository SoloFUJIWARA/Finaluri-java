package com.example.cardealeshipapp;

public class Car {
    private int id;
    private String brand;
    private String model;
    private String type;
    private String engine;
    private String color;
    private int price;
    private int quantity;

    public Car(int id, String brand, String model, String type, String engine, String color, int price, int quantity) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.engine = engine;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public String getEngine() {
        return engine;
    }

    public String getColor() {
        return color;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
