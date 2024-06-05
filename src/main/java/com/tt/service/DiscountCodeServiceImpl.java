package com.tt.service;


import com.tt.dto.DiscountCodeDto;
import com.tt.entity.DiscountCode;
import com.tt.repository.DiscountCodeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountCodeServiceImpl implements DiscountCodeService{

    @Autowired
    DiscountCodeRepository discountCodeRepo;

        public boolean applyDiscount(String codeName, HttpSession session) {
        // Validate and apply the discount code to the session
        DiscountCode discountCode = discountCodeRepo.findByCodeName(codeName).orElse(null);
        if(discountCode == null && discountCode.getQuantity() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<DiscountCode> getAll() {
        List<DiscountCode> discountCodes = discountCodeRepo.findAll();
        return discountCodes;
    }

    @Override
    public Boolean deleteDiscountCode(Integer id) {
        DiscountCode discountCode = discountCodeRepo.findById(id).orElse(null);
        if (discountCode == null) {
            return false;
        }
        discountCodeRepo.delete(discountCode);
        return true;
    }

    @Override
    public DiscountCodeDto addDiscountCode(DiscountCodeDto discountCodeDTO) {
        DiscountCode discountCode = new DiscountCode();
        discountCode.setCodeName(discountCodeDTO.getCodeName());
        discountCode.setPercent(discountCodeDTO.getPercent());
        discountCode.setQuantity(discountCodeDTO.getQuantity());

        discountCode = discountCodeRepo.save(discountCode);

        // Chuyển đổi từ entity sang DTO để trả về
        DiscountCodeDto responseDto = new DiscountCodeDto();
        responseDto.setCodeName(discountCode.getCodeName());
        responseDto.setPercent(discountCode.getPercent());
        responseDto.setQuantity(discountCode.getQuantity());
        return responseDto;
    }

    @Override
    public Optional<DiscountCode> getDiscountCodeById(Integer id) {
        return discountCodeRepo.findById(id);
    }

    @Override
    public DiscountCode updateDiscountCode(Integer id, DiscountCodeDto discountCodeDTO) {
        Optional<DiscountCode> discountCodeOpt = discountCodeRepo.findById(id);
        if (discountCodeOpt.isPresent()) {
            DiscountCode discountCode = discountCodeOpt.get();
            discountCode.setCodeName(discountCodeDTO.getCodeName());
            discountCode.setPercent(discountCodeDTO.getPercent());
            discountCode.setQuantity(discountCodeDTO.getQuantity());
            return discountCodeRepo.save(discountCode);
        }
        throw new RuntimeException("Discount code not found");
    }
}
