package com.tt.service;

import com.tt.entity.Product;
import com.tt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepo;

    @Override
    public List<Product> getAll() {
        List<Product> products = productRepo.findAll();
        return products;
    }
}
