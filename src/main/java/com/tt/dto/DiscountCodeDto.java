package com.tt.dto;


import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DiscountCodeDto {

    private Integer id;

    @NotBlank(message = "Code name cannot be empty")
    private String codeName;

    @NotNull(message = "Percent cannot be null")
    @Min(value = 1, message = "Percent must be greater than 0")
    @Max(value = 100, message = "Percent must be less than or equal to 100")
    private Integer percent;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;
}
