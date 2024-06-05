package com.tt.dto;


import com.tt.entity.Account;
import com.tt.entity.Order;
import com.tt.entity.Product;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CartItemDto {

    private Integer id;

    private Account account;

    private Order isOrder;

    private Product product;

    @Min(value = 1, message = "So luong khong the nho hon 1")
    private Integer quantity;
}
