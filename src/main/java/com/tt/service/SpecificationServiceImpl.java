package com.tt.service;

import com.tt.entity.Specification;
import com.tt.repository.SpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService{
    @Autowired
    private SpecificationRepository specificationRepo;

    @Override
    public List<Specification> getAllSpecifications() {
        return specificationRepo.findAll();
    }
    @Override
    public Specification getSpecificationById(Integer id) {
        return specificationRepo.findById(id).orElse(null);
    }
    @Override
    public Specification saveSpecification(Specification specification) {
        return specificationRepo.save(specification);
    }
    @Override
    public void deleteSpecification(Integer id) {
        specificationRepo.deleteById(id);
    }
}
