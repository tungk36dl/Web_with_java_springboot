package com.tt.controller.admin;

import com.tt.dto.BrandDto;
import com.tt.entity.Account;
import com.tt.entity.Brand;
import com.tt.repository.BrandRepository;
import com.tt.service.BrandService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@Slf4j
public class BrandController {


    private BrandService brandService;

    @Autowired
    private BrandRepository brandRepo;

    public BrandController(BrandService brandService, BrandRepository brandRepo) {
        this.brandService = brandService;
        this.brandRepo = brandRepo;
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

    @GetMapping("/admin/brand")
    public String adminBrand(Model model) {
        String page = "admin-brand";
        model.addAttribute("page", page);

        List<Brand> brands = brandService.getAll();
        model.addAttribute("brands", brands); // Tên attribute dùng để gọi ở file HTML
        return "admin-index";
    }

    @GetMapping("/admin/brand/delete/{id}")
    public String deleteBrand(@PathVariable Integer id,
                                 Model model) {
        String page = "admin-brand";
        Brand brand = brandRepo.findById(id).orElse(null);

        model.addAttribute("page", page);

        if (brand == null) {
            String result = "false";
            model.addAttribute("result", result);
            return "admin-index";
        }
        brandRepo.delete(brand);
        String result = "true";
        model.addAttribute("result", result);

        List<Brand> brands = brandService.getAll();
        model.addAttribute("brands", brands);
        return "admin-index";
    }

    @GetMapping("/admin/brand/add")
    public String addBrand(Model model) {
        String page = "add-brand";
        model.addAttribute("page", page);

        BrandDto brandDto = new BrandDto();
        model.addAttribute("brandDto", brandDto);
        return "admin-index";
    }

    @PostMapping("/admin/brand/add")
    public String saveBrand(@Valid @ModelAttribute("brandDto") BrandDto brandDto,
                               BindingResult result,
                               Model model) {
        Brand brand = brandRepo.findByBrandName(brandDto.getBrandName());
        if(brand != null) {

            String page = "add-brand";
            model.addAttribute("page", page);

            String messageError = "Brand da ton tai";
            model.addAttribute("messageError", messageError);
            return "admin-index";
        }else {

            Brand brandNew = new Brand();
            brandNew.setBrandName(brandDto.getBrandName());

            brandRepo.save(brandNew);
            return "redirect:/admin/brand";
        }
    }


    @GetMapping("/admin/brand/edit/{id}")
    public String editBrand(@PathVariable Integer id,
                               Model model) {
        String page = "edit-brand";
        model.addAttribute("page", page);

        Brand brand = brandRepo.findById(id).orElse(null);
        BrandDto brandDto = new BrandDto();

        brandDto.setId(id);
        brandDto.setBrandName(brand.getBrandName());

        model.addAttribute("brandDto", brandDto);
        return "admin-index";
    }

    @PostMapping("/admin/brand/edit")
    public String updateBrand(@Valid @ModelAttribute("brandDto") BrandDto brandDto,
                                 BindingResult result,
                                 @RequestParam Integer id,
                                 Model model,
                              RedirectAttributes redirectAttributes) {
        String status = "true";
        model.addAttribute("status", status);
        Brand brand = brandRepo.findById(id).orElse(null);

        brand.setBrandName(brandDto.getBrandName());
        brandRepo.save(brand);

        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật brand thành công!");

        return "redirect:/admin/brand";
//        }

    }
}

