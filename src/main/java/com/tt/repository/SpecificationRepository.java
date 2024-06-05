package com.tt.repository;


import com.tt.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SpecificationRepository extends JpaRepository<Specification, Integer> {

    Specification findByProduct_Id(Integer id);

    Specification findByCreatedAt(Date createdAt);
}
