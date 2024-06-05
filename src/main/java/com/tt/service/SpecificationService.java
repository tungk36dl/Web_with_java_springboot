package com.tt.service;

import com.tt.entity.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SpecificationService {

    List<Specification> getAllSpecifications();

    Specification getSpecificationById(Integer id);

    Specification saveSpecification(Specification specification);

    void deleteSpecification(Integer id);
}
