package com.example.cardealershipapp_lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private int id;
    private String brand;
    private String model;
    private String type;
    private String engine;
    private String color;
    private int price;
    private int quantity;
}
