//package com.tt.controller;
//
//import com.tt.entity.Account;
//import com.tt.entity.Category;
//import com.tt.entity.Product;
//import com.tt.repository.AccountRepository;
//import com.tt.repository.CategoryRepository;
//import com.tt.repository.ProductRepository;
//import com.tt.service.AccountService;
//import com.tt.service.CategoryService;
//import com.tt.service.ProductService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("admin")
//@Slf4j
//public class AdminIndexController {
//
//    private AccountService accountService;
//    private ProductService productService;
//    private CategoryService categoryService;
//
//    @Autowired
//    private AccountRepository accountRepo;
//    private ProductRepository productRepo;
//    private CategoryRepository categoryRepo;
//
//    public AdminIndexController(AccountService accountService, ProductService productService, CategoryService categoryService, AccountRepository accountRepo, ProductRepository productRepo, CategoryRepository categoryRepo) {
//        this.accountService = accountService;
//        this.productService = productService;
//        this.categoryService = categoryService;
//        this.accountRepo = accountRepo;
//        this.productRepo = productRepo;
//        this.categoryRepo = categoryRepo;
//    }
//
//    @GetMapping
//    public String adminIndex(Model model) {
//        String page = "admin-index";
//        model.addAttribute("page", page);
//        return "admin-index";
//    }
//
//    // Account -----------------------
//    @GetMapping("/account")
//    public String adminAccount(Model model) {
//        String page = "admin-account";
//        model.addAttribute("page", page);
//
//        List<Account> accounts = accountService.getAll();
//        System.out.println(accounts);
//
//        model.addAttribute("accounts", accounts);
//
//        return "admin-index";
//    }
//    @GetMapping("/account/delete/{id}")
//    public String deleteAccount(@PathVariable Integer id,
//                         Model model){
//
//        Account account = accountService.findById(id);
//        if(account == null) {
//            System.out.println("Not found Account with id = " + id);
//        }
//        accountRepo.delete(account);
//
//
//
////        return "account-list";
//        return "redirect:/admin/account";
//    }
//
//    // Product ----------------------------------
//    @GetMapping("/product")
//    public String adminProduct(Model model) {
//        String page = "admin-product";
//        model.addAttribute("page", page);
//
//        List<Product> products = productService.getAll();
//
//        model.addAttribute("products", products);
//        return "admin-index";
//    }
//
//    @GetMapping("/product/delete/{id}")
//    public String deleteProduct(@PathVariable Integer id,
//                                 Model model) {
//        String page = "admin-product";
//        Product product = productRepo.findById(id).orElse(null);
//
//        model.addAttribute("page", page);
//        if (product == null) {
//            String result = "false";
//            model.addAttribute("result", result);
//            return "admin-index";
//        }
//        productRepo.delete(product);
//        String result = "true";
//        model.addAttribute("result", result);
//        return "redirect:/admin/product";
//    }
//
//
//    // Category------------------------------
//    @GetMapping("/category")
//    public String adminCategory(Model model) {
//        String page = "admin-category";
//        model.addAttribute("page", page);
//
//        List<Category> categories = categoryService.getAll();
//        model.addAttribute("categories", categories); // Tên attribute dùng để gọi ở file HTML
//        return "admin-index";
//    }
//
//    @GetMapping("/category/delete/{id}")
//    public String deleteCategory(@PathVariable Integer id,
//                                 Model model) {
//        String page = "admin-category";
//        Category category = categoryRepo.findById(id).orElse(null);
//
//        model.addAttribute("page", page);
//
//        if (category == null) {
//            String result = "false";
//            model.addAttribute("result", result);
//            return "admin-index";
//        }
//        categoryRepo.delete(category);
//        String result = "true";
//        model.addAttribute("result", result);
//
//        List<Category> categories = categoryService.getAll();
//        model.addAttribute("categories", categories);
////        return "redirect:/admin/category";
//        return "admin-index";
//    }
//
//}
