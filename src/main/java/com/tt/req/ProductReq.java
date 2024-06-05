package com.tt.req;

import com.tt.entity.Category;
import lombok.Data;

import java.util.Date;

@Data
public class ProductReq {
    private String productName;

    private Integer price;

    private Integer quantity;

    private Category category;

    private  String image;

    private Date createdAt;

    private Date updatedAt;
}
