package com.example.fileupload_ex.service;

import com.example.fileupload_ex.entity.Product;
import com.example.fileupload_ex.entity.ProductDto;
import com.example.fileupload_ex.entity.ProductImage;
import com.example.fileupload_ex.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String storeFileName) {
        return fileDir + storeFileName;
    }
    public List<ProductImage> fileSave(List<MultipartFile> imageFiles) {
        List<ProductImage> storeImageFiles = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            if (!imageFile.isEmpty()) {
                // ProductImage 객체 생성
                String fileName = imageFile.getOriginalFilename();
                // 서버에 저장할(유일한) 파일 이름 생성
                String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
                String newFileName = UUID.randomUUID().toString() + "." + ext;
                ProductImage productImage = new ProductImage();
                productImage.setOrgFileName(fileName);
                productImage.setStoreFileName(newFileName);
                //파일 저장
                File file = new File(fileDir, newFileName);
                try {
                    imageFile.transferTo(file);
                    storeImageFiles.add(productImage);
                } catch (IllegalStateException | IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return storeImageFiles;
    }

    public void addProduct(ProductDto productDto, List<ProductImage> imageFiles) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setImageFiles(imageFiles);
        productRepository.save(product);
    }

    public Product getProductInfo(Long productId) {
        return productRepository.findById(productId);
    }
}
