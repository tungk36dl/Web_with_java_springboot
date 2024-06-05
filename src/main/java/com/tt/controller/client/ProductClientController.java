package com.tt.controller.client;

import com.tt.entity.*;
import com.tt.repository.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
@Slf4j
public class ProductClientController {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private BrandRepository brandRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private  SpecificationRepository specificationRepo;

    public ProductClientController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }


    // Giups tự động thêm các biến categories, brands vào model trước khi mỗi phương thức xử lý request đc gọi
    @ModelAttribute
    public void addAttributes(Model model, HttpSession session) {
        List<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);

        List<Brand> brands = brandRepo.findAll();
        model.addAttribute("brands", brands);

        // Kiểm tra nếu người dùng đã đăng nhập
        Account loggedInUser = (Account) session.getAttribute("account");
        if (loggedInUser != null) {
            model.addAttribute("loggedInUser", loggedInUser);
//            model.addAttribute("loggedInUserId", loggedInUser.getId());

        }
    }
    @GetMapping("product")
    public String getAll(Model model,
                         Pageable pageable) {
        String page = "product-list-client";
        model.addAttribute("page", page);
        List<Brand> brands = brandRepo.findAll();
        model.addAttribute("brands", brands);

//        List<Product> products = productRepo.findAll();
        Page<Product> pageProducts =  productRepo.findAll(pageable);

        List<Product> products = pageProducts.toList();
        model.addAttribute("products", products);
        model.addAttribute("totalPage", pageProducts.getTotalPages());
        model.addAttribute("currentPage", pageable.getPageNumber());

        return "client-index";
    }

    @GetMapping("product/{id}")
    public String getDetail(@PathVariable Integer id,
                            Model model) {
        String page = "product-detail";
        model.addAttribute("page", page);
        Product product = productRepo.findById(id).orElse(null);
        if(product == null) {

        }

        Specification specification = specificationRepo.findByProduct_Id(product.getId());
        model.addAttribute("specification", specification);
        model.addAttribute("product", product);

        List<Product> products = productRepo.findByCategory_Brand_BrandName(product.getCategory().getBrand().getBrandName());
        model.addAttribute("products", products);
        return "client-index";
    }


    /// Search product name
    @GetMapping("product/search")
    public String search(Model model,
                         @RequestParam String data,
                         Pageable pageable){
        data = data.trim();
        String page = "product-list-client";
        model.addAttribute("page", page);

        Page<Product> pageProducts;

        if(data.equals("")) {
            pageProducts = productRepo.findAll(pageable);

        } else{
            pageProducts = productRepo.findByProductNameContaining(data, pageable);

        }
        List<Product> products = pageProducts.getContent();
        model.addAttribute("products", products);
        model.addAttribute("totalPage", pageProducts.getTotalPages());
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("searchData", data);
        return "client-index";
    }


    @GetMapping("product/list/{brandName}")
    public String listProductInBrand(@PathVariable String brandName,
                                     Model model,
                                     Pageable pageable) {
        try {
            brandName = brandName.toLowerCase(Locale.ROOT);

            String page = "product-list-client";
            model.addAttribute("page", page);

            Page<Product> pageProducts =  productRepo.findByCategory_Brand_BrandNameContaining(brandName, pageable);

            List<Product> products = pageProducts.toList();
            model.addAttribute("products", products);
            model.addAttribute("totalPage", pageProducts.getTotalPages());
            model.addAttribute("currentPage", pageable.getPageNumber());

            return "client-index";
        } catch (Exception e) {
            // Log lỗi (nếu có logger)
            // logger.error("Error in listProductInBrand", e);
            model.addAttribute("error", "An error occurred while fetching the products.");
            return "error-page"; // Hoặc trang lỗi khác nếu cần
        }
    }

        @GetMapping("product/list/{brandName}/{categoryId}")
    public String listProductInCategory(@PathVariable String brandName,
                                        @PathVariable Integer categoryId,
                                        Model model,
                                        Pageable pageable) {
        try{
            brandName = brandName.toLowerCase(Locale.ROOT);
            String page = "product-list-client";
            model.addAttribute("page", page);

            Category category = categoryRepo.findById(categoryId).orElse(null);
            if(category == null) {
                model.addAttribute("products", new ArrayList<>());
                return "client-index";
            }

            Page<Product> pageProducts =  productRepo.findByCategory_CategoryNameContaining(category.getCategoryName(), pageable);

            List<Product> products = pageProducts.toList();
            model.addAttribute("products", products);
            model.addAttribute("totalPage", pageProducts.getTotalPages());
            model.addAttribute("currentPage", pageable.getPageNumber());


        }catch (Exception e){
            model.addAttribute("error", "An error occurred while retrieving products.");
        }
        return "client-index";

    }


    @PostMapping("/product/add-to-cart")
    public String addToCart(@RequestParam Integer quantity,
                            @RequestParam Integer productId,
                            HttpSession session,
                            RedirectAttributes redirectAttributes){

        Account loggedInUser = (Account) session.getAttribute("account");
        if (loggedInUser == null) {
            // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
            return "redirect:/login";
        }

        Account accountFromDb = accountRepo.findById(loggedInUser.getId()).orElse(null);


        // Lấy sản phẩm từ database
        Product product = productRepo.findById(productId).orElse(null);
        if (product == null) {
            // Nếu sản phẩm không tồn tại, xử lý lỗi (có thể trả về một trang lỗi)
            return "redirect:/error";
        }

        Optional<CartItem> cartItemCheck = cartItemRepo.findByAccount_IdAndProduct_IdAndOrder(accountFromDb.getId(), productId, null);
        if(cartItemCheck.isEmpty()) {
            // Tạo một mục giỏ hàng mới
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setAccount(accountFromDb);
            cartItem.setOrder(null);

            // Lưu mục giỏ hàng vào database
            cartItemRepo.save(cartItem);
        }else{
            CartItem cartItem = cartItemCheck.get();
            Integer quantityNow = cartItem.getQuantity();
            cartItem.setQuantity(quantityNow + quantity);
//            cartItem.setOrder(null);
            cartItemRepo.save(cartItem);

        }

        redirectAttributes.addFlashAttribute("messageAddToCart", "Sản phẩm đã được thêm vào giỏ hàng thành công!");

        return "redirect:/product/" + productId;
    }

    @GetMapping("/product/list/asc")
    public String getProductAsc(Model model,
                                Pageable pageable) {
        String page = "product-list-client";
        model.addAttribute("page", page);

        Page<Product> pageProducts =  productRepo.findAllByOrderByPriceAsc(pageable);

        List<Product> products = pageProducts.toList();
        model.addAttribute("products", products);
        model.addAttribute("totalPage", pageProducts.getTotalPages());
        model.addAttribute("currentPage", pageable.getPageNumber());

        return "client-index";
    }

    @GetMapping("/product/list/desc")
    public String getProductDesc(Model model,
                                 Pageable pageable) {
        String page = "product-list-client";
        model.addAttribute("page", page);

        Page<Product> pageProducts =  productRepo.findAllByOrderByPriceDesc(pageable);

        List<Product> products = pageProducts.toList();
        model.addAttribute("products", products);
        model.addAttribute("totalPage", pageProducts.getTotalPages());
        model.addAttribute("currentPage", pageable.getPageNumber());
        return "client-index";
    }

    @PostMapping("/product/list/priceBetween")
    public String getProductBetween(@RequestParam Integer minValue,
                                    @RequestParam Integer maxValue,
                                    Model model, Pageable pageable) {
        Page<Product> pageProducts =  productRepo.findByPriceBetween(minValue, maxValue, pageable);

        List<Product> products = pageProducts.toList();
        model.addAttribute("products", products);
        model.addAttribute("totalPage", pageProducts.getTotalPages());
        model.addAttribute("currentPage", pageable.getPageNumber());
        String page = "product-list-client";
        model.addAttribute("page", page);
        return "client-index";
    }



}
