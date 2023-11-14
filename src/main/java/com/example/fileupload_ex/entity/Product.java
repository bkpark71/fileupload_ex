package com.example.fileupload_ex.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Product {
    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;
    private List<ProductImage> imageFiles = new ArrayList<>();
}
