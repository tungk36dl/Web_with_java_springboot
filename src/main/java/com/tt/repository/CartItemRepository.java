package com.tt.repository;

import com.tt.entity.CartItem;
import com.tt.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByAccount_Id(Integer accountId);

    List<CartItem> findByAccount_IdAndOrder(Integer accountId, Order oder);

    Optional<CartItem> findByAccount_IdAndProduct_Id(Integer accountId, Integer productId);

    Optional<CartItem> findByAccount_IdAndProduct_IdAndOrder(Integer accountId, Integer productId, Order order);
    Optional<CartItem> findByProduct_Id(Integer productId);

    List<CartItem> findByOrder_Id(Integer id);

}
