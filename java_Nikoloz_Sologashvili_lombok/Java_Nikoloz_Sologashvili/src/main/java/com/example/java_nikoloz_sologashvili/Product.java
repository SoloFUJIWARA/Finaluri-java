package com.example.java_nikoloz_sologashvili;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private int id;
    private String name;
    private int price;
    private int quantity;
}
