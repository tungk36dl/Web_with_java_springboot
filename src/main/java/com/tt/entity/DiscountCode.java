package com.tt.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "discount_code")
@Data
public class DiscountCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codeName;

    private Integer percent;

    private Integer quantity;
}
