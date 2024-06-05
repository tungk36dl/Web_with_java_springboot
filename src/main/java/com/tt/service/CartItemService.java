package com.tt.service;

import org.springframework.stereotype.Service;

@Service
public interface CartItemService {
    boolean updateQuantity(Integer productId,Integer accountId, Integer quantity);
}
