package com.tt.controller.admin;


import com.tt.dto.BrandDto;
import com.tt.dto.DiscountCodeDto;
import com.tt.entity.Account;
import com.tt.entity.Brand;
import com.tt.entity.DiscountCode;
import com.tt.service.DiscountCodeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class DiscountCodeController {

    private DiscountCodeService discountCodeService;

    public DiscountCodeController(DiscountCodeService discountCodeService) {
        this.discountCodeService = discountCodeService;
    }

    @ModelAttribute
    public void addAttributes(Model model, HttpSession session) {

        // Kiểm tra nếu người dùng đã đăng nhập
        Account loggedInUser = (Account) session.getAttribute("account");
        if (loggedInUser != null) {
            model.addAttribute("loggedInUser", loggedInUser);
//            model.addAttribute("loggedInUserId", loggedInUser.getId());

        }
    }

    @GetMapping("/admin/discountCode")
    public String adminDiscountCode(Model model) {
        String page = "admin-discountCode";
        model.addAttribute("page", page);

        List<DiscountCode> discountCodes = discountCodeService.getAll();
        model.addAttribute("discountCodes", discountCodes); // Tên attribute dùng để gọi ở file HTML
        return "admin-index";
    }

    @GetMapping("/admin/discountCode/delete/{id}")
    public String deleteDiscountCode(@PathVariable Integer id,
                              Model model) {
        String page = "admin-discountCode";
        model.addAttribute("page", page);

        Boolean isDelete = discountCodeService.deleteDiscountCode(id);
        if (!isDelete) {
            String result = "false";
            model.addAttribute("result", result);
            return "admin-index";
        }
        String result = "true";
        model.addAttribute("result", result);

        List<DiscountCode> discountCodes = discountCodeService.getAll();
        model.addAttribute("discountCodes", discountCodes);
        return "admin-index";
    }


    @GetMapping("/admin/discountCode/add")
    public String addDiscountCode(Model model) {
        String page = "add-discountCode";
        model.addAttribute("page", page);

        DiscountCodeDto discountCodeDto = new DiscountCodeDto();
        model.addAttribute("discountCodeDto", discountCodeDto);
        return "admin-index";
    }

    @PostMapping("/admin/discountCode/add")
    public String saveDiscountCode(@Valid @ModelAttribute("discountCodeDto") DiscountCodeDto discountCodeDto,
                            BindingResult result,
                            Model model) {
        String page = "add-discountCode";
        model.addAttribute("page", page);
        if (result.hasErrors()) {
            return "admin-index";        }

        try {
            discountCodeService.addDiscountCode(discountCodeDto);
            return "redirect:/admin/discountCode";
        } catch (Exception e) {


            String messageError = "DiscountCode da ton tai";
            model.addAttribute("messageError", messageError);
            return "admin-index";

        }
    }

    @GetMapping("/admin/discountCode/edit/{id}")
    public String showEditDiscountCodeForm(@PathVariable Integer id,
                                           Model model) {
        String page = "edit-discountCode";
        model.addAttribute("page", page);

        Optional<DiscountCode> discountCodeOpt = discountCodeService.getDiscountCodeById(id);
        if (discountCodeOpt.isPresent()) {
            DiscountCode discountCode = discountCodeOpt.get();
            DiscountCodeDto discountCodeDTO = new DiscountCodeDto();
            discountCodeDTO.setCodeName(discountCode.getCodeName());
            discountCodeDTO.setPercent(discountCode.getPercent());
            discountCodeDTO.setQuantity(discountCode.getQuantity());
            model.addAttribute("discountCodeDto", discountCodeDTO);
            model.addAttribute("id", id);
            return "admin-index";
        } else {
            return "redirect:/admin/discountCode";
        }
    }

    @PostMapping("/admin/discountCode/edit/{id}")
    public String updateDiscountCode(@PathVariable Integer id,
                                     @Valid DiscountCodeDto discountCodeDto,
                                     BindingResult result,
                                     Model model) {
        String page = "edit-discountCode";
        model.addAttribute("page", page);
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "admin-index";
        }

        try {
            discountCodeService.updateDiscountCode(id, discountCodeDto);
            return "redirect:/admin/discountCode";
        } catch (Exception e) {
            model.addAttribute("messageError", "Có lỗi xảy ra, vui lòng thử lại.");
            model.addAttribute("id", id);
            return "admin-index";
        }
    }














}
