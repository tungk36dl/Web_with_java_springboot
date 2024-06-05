package com.tt.service;


import com.tt.entity.Brand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {
    List<Brand> getAll();
}
