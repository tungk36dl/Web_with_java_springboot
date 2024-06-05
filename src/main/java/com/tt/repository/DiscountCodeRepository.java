package com.tt.repository;


import com.tt.entity.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Integer > {
    Optional<DiscountCode> findByCodeName(String codeName);
}
