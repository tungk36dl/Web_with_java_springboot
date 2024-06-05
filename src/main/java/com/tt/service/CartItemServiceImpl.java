package com.tt.service;

import com.tt.dto.CartItemDto;
import com.tt.entity.Account;
import com.tt.entity.CartItem;
import com.tt.entity.Product;
import com.tt.repository.AccountRepository;
import com.tt.repository.CartItemRepository;
import com.tt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService{
    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private ProductRepository productRepo;

//    public void addProductToCart(Integer accountId, Integer productId, Integer quantity) {
//        Account account = accountRepo.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
//        Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
//
//        CartItem cartItem = new CartItem(account, product, quantity);
//        cartItemRepo.save(cartItem);
//    }
//
//    public List<CartItem> getCartItems(Integer accountId) {
//        Account account = accountRepo.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
//        return account.getCartItems();
//    }


    public boolean updateQuantity(Integer productId,Integer accountId,  Integer quantity) {

        // Muốn thông qua cartItemDto
//        Product product = productRepo.findById(productId).orElse(null);
//        CartItemDto cartItemDto = new CartItemDto();
//        cartItemDto.setProduct(product);

        Optional<CartItem> cartItemOp = cartItemRepo.findByAccount_IdAndProduct_IdAndOrder(accountId, productId, null);
        if (cartItemOp.isEmpty()) {
            return false;
        }
        CartItem cartItem = cartItemOp.get();
        if(cartItem.getProduct().getQuantity() < quantity) {
            return false;
        }
        cartItem.setQuantity(quantity);
        cartItemRepo.save(cartItem);
        return true;
    }


}
