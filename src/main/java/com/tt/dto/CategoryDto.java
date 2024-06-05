package com.tt.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CategoryDto {

    private Integer id;

    @NotEmpty(message = "The categoryName is required")
    private String categoryName;

    private String brandName;
}
