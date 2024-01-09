package com;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Person {
    private long id;
    private String name;
    private Date createdAt;
}
