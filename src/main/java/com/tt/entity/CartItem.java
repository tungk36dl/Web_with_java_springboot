package com.tt.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cartItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
//    public CartItem(Integer id, Account account, Product product, Integer quantity) {
//        this.id = id;
//        this.account = account;
//        this.product = product;
//        this.quantity = quantity;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantity;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

    public CartItem(Account account, Product product, Integer quantity) {
        this.account = account;
        this.product = product;
        this.quantity = quantity;
    }
}
