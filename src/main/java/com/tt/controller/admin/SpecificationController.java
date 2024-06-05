package com.tt.controller.admin;

import com.tt.entity.Account;
import com.tt.entity.Product;
import com.tt.entity.Specification;
import com.tt.repository.ProductRepository;
import com.tt.repository.SpecificationRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SpecificationController {

    @Autowired
    SpecificationRepository specificationRepo;

    @Autowired
    ProductRepository productRepo;

    @ModelAttribute
    public void addAttributes(Model model, HttpSession session) {

        // Kiểm tra nếu người dùng đã đăng nhập
        Account loggedInUser = (Account) session.getAttribute("account");
        if (loggedInUser != null) {
            model.addAttribute("loggedInUser", loggedInUser);
//            model.addAttribute("loggedInUserId", loggedInUser.getId());

        }
    }

    @GetMapping("admin/product/specification/{id}")
    public String productSpecification(@PathVariable Integer id,
                                       Model model) {

        Specification specification = specificationRepo.findByProduct_Id(id);
        if (specification != null) {
            model.addAttribute("specification", specification);
        }else {
            model.addAttribute("specification", new Specification());
        }
        model.addAttribute("productId", id);
        String page = "edit-specification";
        model.addAttribute("page", page);
        return "admin-index";
//        }
    }

    @PostMapping("/admin/product/specification/{productId}")
    public String saveOrUpdateSpecification(@PathVariable Integer productId,
                                            @Valid @ModelAttribute("specification") Specification specification,
                                            Model model,
                                            RedirectAttributes redirectAttributes){
        try {
//            if(id != null) {
//                Specification specification1 = specificationRepo.findById(id).orElse(null);
//                specificationRepo.save(specification1);
//            }else{
            Product product = productRepo.findById(productId).orElse(null);
            Specification specification1 = specificationRepo.findByProduct_Id(productId);
            specificationRepo.delete(specification1);
            specification.setProduct(product);
            specificationRepo.save(specification);
//            }
            // Lưu hoặc cập nhật Specification
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông số kĩ thuật thành công!");

            // Sau khi lưu thành công, chuyển hướng người dùng đến trang danh sách Specifications
            return "redirect:/admin/product";
        } catch (Exception e) {
            // Xử lý lỗi và trả về trang hiện tại với thông báo lỗi
//            model.addAttribute("errorMessage", "Có lỗi xảy ra khi lưu thông số kỹ thuật.");
//            model.addAttribute("specification", specification);
            String page = "admin-product";
            model.addAttribute("page", page);
            return "admin-index";
        }
    }
}
