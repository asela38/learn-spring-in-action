package com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Taco {
    private Long id;
    private String name;
    private Date createdAt = new Date();
    private List<Ingredient> ingredients;

}
