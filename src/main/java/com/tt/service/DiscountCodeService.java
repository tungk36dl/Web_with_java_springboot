package com.tt.service;

import com.tt.dto.DiscountCodeDto;
import com.tt.entity.DiscountCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DiscountCodeService {
    List<DiscountCode> getAll();

    Boolean deleteDiscountCode(Integer id);

    DiscountCodeDto addDiscountCode(DiscountCodeDto discountCodeDto);

    Optional<DiscountCode> getDiscountCodeById(Integer id);

    DiscountCode updateDiscountCode(Integer id, DiscountCodeDto discountCodeDTO);
}
