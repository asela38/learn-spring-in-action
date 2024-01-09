package com;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Person {
    private String name;
    private Date createdAt;
}
