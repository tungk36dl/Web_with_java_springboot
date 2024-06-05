package com.tt.dto;


import com.tt.entity.Account;
import com.tt.helper.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class OrderDto {

    private Integer id;

    private String status;

    private String note;

    private Integer totalPrice;

    private Account account;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
