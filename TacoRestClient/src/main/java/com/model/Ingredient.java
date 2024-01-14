package com.model;


import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private  String id;
    private  String name;
    private  Type type;


    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
