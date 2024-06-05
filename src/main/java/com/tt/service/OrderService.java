package com.tt.service;

import com.tt.dto.DiscountCodeDto;
import com.tt.dto.OrderDto;
import com.tt.entity.DiscountCode;
import com.tt.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    List<Order> getAll();

    Boolean deleteOrder(Integer id);

    OrderDto addOrder(OrderDto orderDto);

    Optional<Order> getOrderById(Integer id);

    Order updateOrder(Integer id, OrderDto orderDto);

    List<Order> findByCancelOrder(Boolean cancelOrder);

}
