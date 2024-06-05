package com.tt.service;

import com.tt.entity.Category;
import com.tt.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public List<Category> getAll() {
        List<Category> categories = categoryRepo.findAll();
        return categories;
    }
}
