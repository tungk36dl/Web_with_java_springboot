package com.tt.dto;


import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
//import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.List;

@Data
public class ProductShowDto {

    private Integer id;

    @NotEmpty(message = "The name is required")
    private String productName;

//    @NotEmpty(message = "The price is required")
//    @Min(0)
    private Integer price;

    private Integer quantity;

    private MultipartFile imageFile;

    private List<MultipartFile> imageFiles;

    @NotEmpty(message = "The category is required")
    private String category;

}
