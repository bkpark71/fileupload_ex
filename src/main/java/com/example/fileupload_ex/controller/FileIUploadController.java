package com.example.fileupload_ex.controller;

import com.example.fileupload_ex.entity.Product;
import com.example.fileupload_ex.entity.ProductDto;
import com.example.fileupload_ex.entity.ProductImage;
import com.example.fileupload_ex.repository.ProductRepository;
import com.example.fileupload_ex.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileIUploadController {
    private final ProductService productService;

//    @Value("${file.dir}")
//    private String fileDir;

    @GetMapping("/upload")
    public String upload() {
        return "FileUploadForm";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute ProductDto productDto) {
        //log.info("product name {} ==> ", productDto.getName());
        //log.info("files {} ==> ", imageFiles);
        List<MultipartFile> imageFiles = productDto.getImageFiles();
        List<ProductImage> productImages = productService.fileSave(imageFiles);
        productService.addProduct(productDto, productImages);

        return "FileUploadForm";
    }

    @GetMapping("/info/{productId}")
    public String getProduct(@PathVariable Long productId,
                             Model model) {
        Product product = productService.getProductInfo(productId);
        for(ProductImage productImage : product.getImageFiles()) {
            log.info("product orgfilenames {} ==> ", productImage.getOrgFileName());
            log.info("product storefilenames {} ==> ", productImage.getStoreFileName());
        }
        //model.addAttribute("fileDir", fileDir);
        model.addAttribute("product", product);
        return "ProductInfo";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        log.info("filename {} ==> ", filename);
        String fullPath = "file:" + productService.getFullPath(filename);
        return new UrlResource("file:" + productService.getFullPath(filename));
    }
}
