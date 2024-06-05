package com.tt.service;


import com.tt.dto.DiscountCodeDto;
import com.tt.dto.OrderDto;
import com.tt.entity.DiscountCode;
import com.tt.entity.Order;
import com.tt.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepo;

    @Override
    public List<Order> getAll() {
        List<Order> orders = orderRepo.findAllByOrderByCreatedAtDesc();
        return orders;
    }

    @Override
    public Boolean deleteOrder(Integer id) {
        Order order = orderRepo.findById(id).orElse(null);
        if (order == null) {
            return false;
        }
        orderRepo.delete(order);
        return true;
    }

    @Override
    public OrderDto addOrder(OrderDto orderDto) {
//        Order order = new Order();
//        order.setNote(orderDto.getNote());
//        order.setStatus(orderDto.getStatus());
//        order.setQuantity(orderDto.getQuantity());
//
//        orderRepo.save(order);
//
//        // Chuyển đổi từ entity sang DTO để trả về
//        OrderDto responseDto = new OrderDto();
//        responseDto.setCodeName(order.getCodeName());
//        responseDto.setPercent(order.getPercent());
//        responseDto.setQuantity(order.getQuantity());
//        return responseDto;
        return null;
    }

    @Override
    public Optional<Order> getOrderById(Integer id) {
        return null;
    }

    @Override
    public Order updateOrder(Integer id, OrderDto orderDto) {
        return null;
    }

    @Override
    public List<Order> findByCancelOrder(Boolean cancelOrder) {
        List<Order> orders = orderRepo.findByCancelOrderOrderByCreatedAtDesc(cancelOrder);
        return orders;
    }

}
