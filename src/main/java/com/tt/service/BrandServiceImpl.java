package com.tt.service;


import com.tt.entity.Brand;
import com.tt.entity.Category;
import com.tt.repository.BrandRepository;
import com.tt.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService{

    @Autowired
    private BrandRepository brandRepo;

    @Override
    public List<Brand> getAll() {
        List<Brand> brands = brandRepo.findAll();
        return brands;
    }
}
