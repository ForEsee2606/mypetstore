package org.example.mypetstore.model;

import lombok.Data;

@Data
public class ProductType {
    private Long id;
    private String name;
    private Long categoryId;
    private String description;
}
