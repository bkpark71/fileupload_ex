package com.example.fileupload_ex.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductDto {
    private String name;
    private Integer price;
    private Integer quantity;
    private List<MultipartFile> imageFiles ;
}
