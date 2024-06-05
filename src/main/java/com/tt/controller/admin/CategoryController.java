package com.tt.controller.admin;


import com.tt.dto.CategoryDto;
import com.tt.entity.Account;
import com.tt.entity.Brand;
import com.tt.entity.Category;
import com.tt.entity.Product;
import com.tt.repository.BrandRepository;
import com.tt.repository.CategoryRepository;
import com.tt.repository.ProductRepository;
import com.tt.service.CategoryService;
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
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private BrandRepository brandRepo;

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

    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepo) {
        this.categoryService = categoryService;
        this.categoryRepo = categoryRepo;
    }

    @GetMapping("/admin/category")
    public String adminCategory(Model model) {
        String page = "admin-category";
        model.addAttribute("page", page);

        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories); // Tên attribute dùng để gọi ở file HTML
        return "admin-index";
    }

    @GetMapping("/admin/category/delete/{id}")
    public String deleteCategory(@PathVariable Integer id,
                                 Model model) {
        String page = "admin-category";
        Category category = categoryRepo.findById(id).orElse(null);

        model.addAttribute("page", page);

        if (category == null) {
            String result = "false";
            model.addAttribute("result", result);
            return "admin-index";
        }

        List<Product> products = productRepo.findByCategory_CategoryName(category.getCategoryName());
        for (Product product : products) {
            product.setCategory(null);
            productRepo.save(product);
        }

        categoryRepo.delete(category);
        String result = "true";
        model.addAttribute("result", result);

        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
//        return "redirect:/admin/category";
        return "admin-index";
    }

    @GetMapping("/admin/category/add")
    public String addCategory(Model model) {
        String page = "add-category";
        model.addAttribute("page", page);

        List<Brand> brands = brandRepo.findAll();
        model.addAttribute("brands", brands);

        CategoryDto categoryDto = new CategoryDto();
        model.addAttribute("categoryDto", categoryDto);
        return "admin-index";
    }

    @PostMapping("/admin/category/add")
    public String saveCategory(@Valid @ModelAttribute("categoryDto") CategoryDto categoryDto,
                               BindingResult result,
                               Model model) {
        Category category = categoryRepo.findByCategoryName(categoryDto.getCategoryName());
        if(category != null) {

            String page = "add-category";
            model.addAttribute("page", page);

            String messageError = "The loai da ton tai";
            model.addAttribute("messageError", messageError);
            return "admin-index";
        }else {

            Brand brand = brandRepo.findByBrandName(categoryDto.getBrandName());
            Category categoryNew = new Category();
            categoryNew.setCategoryName(categoryDto.getCategoryName());
            categoryNew.setBrand(brand);

            categoryRepo.save(categoryNew);
            return "redirect:/admin/category";
        }
    }


    @GetMapping("/admin/category/edit/{id}")
    public String editCategory(@PathVariable Integer id,
                               Model model) {
        String page = "edit-category";
        model.addAttribute("page", page);

        List<Brand> brands = brandRepo.findAll();
        model.addAttribute("brands", brands);

        Category category = categoryRepo.findById(id).orElse(null);
        model.addAttribute("category", category);
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId(id);
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setBrandName(category.getBrand().getBrandName());
//        System.out.println("++++++++++++++++++++++++++++++++" + categoryDto);
        model.addAttribute("categoryDto", categoryDto);
        return "admin-index";
    }

    @PostMapping("/admin/category/edit")
    public String updateCategory(@Valid @ModelAttribute("categoryDto") CategoryDto categoryDto,
                                 BindingResult result,
                                 @RequestParam Integer id,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
//        String page = "edit-category";
//        model.addAttribute("page", page);


        Category category = categoryRepo.findById(id).orElse(null);
//        if(category != null) {
//            String messageError = "The loai da ton tai";
//            model.addAttribute("messageError", messageError);
//            return "admin-index";
//        }else {

            Brand brand = brandRepo.findByBrandName(categoryDto.getBrandName());
            category.setCategoryName(categoryDto.getCategoryName());
            category.setBrand(brand);

        categoryRepo.save(category);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật Category thành công!");

        return "redirect:/admin/category";
//        }

    }
}
