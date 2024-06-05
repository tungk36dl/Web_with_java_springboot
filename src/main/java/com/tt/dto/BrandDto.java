package com.tt.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BrandDto {

    private Integer id;

    @NotEmpty(message = "The brandName is required")
    private String brandName;
}
