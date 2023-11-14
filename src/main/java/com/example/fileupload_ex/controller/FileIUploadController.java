package com.example.fileupload_ex.controller;

import com.example.fileupload_ex.entity.Product;
import com.example.fileupload_ex.entity.ProductDto;
import com.example.fileupload_ex.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileIUploadController {
    private final ProductRepository productRepository;

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String upload() {
        return "FileUploadForm";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute ProductDto productDto){
        log.info("product name {} ==> ", productDto.getName());
        List<MultipartFile> imageFiles = productDto.getImageFiles();
        log.info("files {} ==> ", imageFiles);

        for(MultipartFile imageFile : imageFiles) {
            log.info("file {} ==> ", imageFile.getOriginalFilename());
        }
//        if(!file.isEmpty()) {
//            file.transferTo(new File(fileDir + file.getOriginalFilename()));
//        }
        return "FileUploadForm";
    }
}
