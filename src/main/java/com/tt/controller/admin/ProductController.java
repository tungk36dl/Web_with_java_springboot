package com.tt.controller.admin;

import com.tt.dto.ProductShowDto;
import com.tt.entity.*;
import com.tt.repository.BrandRepository;
import com.tt.repository.CategoryRepository;
import com.tt.repository.ProductRepository;
import com.tt.repository.SpecificationRepository;
import com.tt.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import java.awt.print.Pageable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class ProductController {

    @Value("5")
    private Integer littelQuantity;

    private ProductService productService;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private BrandRepository brandRepo;

    @Autowired
    SpecificationRepository specificationRepo;

    @ModelAttribute
    public void addAttributes(Model model, HttpSession session) {

        // Kiểm tra nếu người dùng đã đăng nhập
        Account loggedInUser = (Account) session.getAttribute("account");
        if (loggedInUser != null) {
            model.addAttribute("loggedInUser", loggedInUser);
//            model.addAttribute("loggedInUserId", loggedInUser.getId());

        }
    }

    public ProductController(ProductService productService, ProductRepository productRepo, CategoryRepository categoryRepo) {
        this.productService = productService;
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

//    // Giups tự động thêm các biến categories, brands vào model trước khi mỗi phương thức xử lý request đc gọi
//    @ModelAttribute
//    public void addAttributes(Model model) {
//        List<Category> categories = categoryRepo.findAll();
//        model.addAttribute("categories", categories);
//
//        List<Brand> brands = brandRepo.findAll();
//        model.addAttribute("brands", brands);
//    }

    @GetMapping("/admin/product")
    public String adminProduct(Model model,
                               Pageable pageable) {
        // Pageable: Nhận trang số bao nhiêu và sắp xếp số trang ( mạc đinh: 0 )
        // sắp xếp alpha, beta ( tăng dần )
        // Mỗi trang bao nhiêu bản ghi (20)

        String page = "admin-product";
        model.addAttribute("page", page);

        Page<Product> pageProducts =  productRepo.findAll(pageable);
        List<Product> products = pageProducts.toList();
//        System.out.println(products.getSize());
//        System.out.println(products.getTotalPages());


        model.addAttribute("products", products);
        model.addAttribute("totalPage", pageProducts.getTotalPages());
        model.addAttribute("currentPage", pageable.getPageNumber());
        return "admin-index";
    }

    @GetMapping("/admin/product/little")
    public String adminProductLittle(Model model,
                               Pageable pageable) {
        // Pageable: Nhận trang số bao nhiêu và sắp xếp số trang ( mạc đinh: 0 )
        // sắp xếp alpha, beta ( tăng dần )
        // Mỗi trang bao nhiêu bản ghi (20)

        String page = "admin-product";
        model.addAttribute("page", page);

        Page<Product> pageProducts =  productRepo.findByQuantityBetween(0, this.littelQuantity,pageable);
        List<Product> products = pageProducts.toList();
//        System.out.println(products.getSize());
//        System.out.println(products.getTotalPages());


        model.addAttribute("products", products);
        model.addAttribute("totalPage", pageProducts.getTotalPages());
        model.addAttribute("currentPage", pageable.getPageNumber());
        return "admin-index";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable Integer id,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        String page = "admin-product";
        Product product = productRepo.findById(id).orElse(null);

        model.addAttribute("page", page);
        if (product == null) {
            String result = "false";
            model.addAttribute("result", result);
            return "admin-index";
        }
        if(product.getCartItems() != null) {
            redirectAttributes.addFlashAttribute("messageDelete", "Sản phẩm đang nằm trong 1 đơn hàng, ko thể xóa sản phẩm");
            return "redirect:/admin/product";
        }
        productRepo.delete(product);
        String result = "true";
        model.addAttribute("result", result);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/add")
    public String addProduct(Model model) {
        String page = "add-product";
        model.addAttribute("page", page);

        List<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);

        ProductShowDto productDto = new ProductShowDto();
        model.addAttribute("productDto", productDto);

        return "admin-index";
    }

    @PostMapping("/admin/product/add")
    public String saveProduct(@Valid @ModelAttribute("productDto") ProductShowDto productDto,
                              BindingResult result) throws IOException {
        if (productDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("productDto", "imageFile", "This image file is required!"));
        }
        if (result.hasErrors()) {
            return "redirect:/admin/product";
        }

        // Save main image
        MultipartFile image = productDto.getImageFile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

        String uploadDir = "public/images/products/";

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        // Save additional images
        List<MultipartFile> imageFiles = productDto.getImageFiles();
        List<String> additionalImages = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                String additionalStorageFileName = createdAt.getTime() + "_" + file.getOriginalFilename();
                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + additionalStorageFileName), StandardCopyOption.REPLACE_EXISTING);
                    additionalImages.add(additionalStorageFileName);
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }
            }
        }

        // Add thể  loại vào sản phẩm
        Category category = categoryRepo.findByCategoryName(productDto.getCategory());

        // Add 1 thông số kĩ thuật rỗng vào sản phẩm
        Specification specification = new Specification();
        specification.setCreatedAt(createdAt);
        specificationRepo.save(specification);
        Specification specification1 = specificationRepo.findByCreatedAt(createdAt);


        Product product = new Product();
        product.setSpecification(specification1);
        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setQuantitySold(0);
        product.setCategory(category);
        product.setImage(storageFileName);
        product.setImages(additionalImages);  // Assume you have a setImages method in your Product entity
        product.setCreatedAt(createdAt);

        productRepo.save(product);

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/edit/{id}")
    public String editProduct(@PathVariable Integer id ,Model model) {
        String page = "edit-product";
        model.addAttribute("page", page);


        Product product = productRepo.findById(id).orElse(null);
        model.addAttribute("product", product);

        List<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);

        ProductShowDto productDto = new ProductShowDto();
        productDto.setProductName(product.getProductName());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());
        if(product.getCategory() != null) {
            productDto.setCategory(product.getCategory().getCategoryName());
        }
//        ProductShowDto productDto = new ProductShowDto();
        model.addAttribute("productDto", productDto);

        return "admin-index";
    }

    @PostMapping("/admin/product/edit")
    public String updateProduct(@RequestParam Integer id,
                                Model model,
                                @Valid @ModelAttribute("productDto") ProductShowDto productDto,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
//        if(productDto.getImageFile().isEmpty()) {
//            result.addError(new FieldError("productDto", "imageFile", "This image file is required!"));
//        }
//        if(result.hasErrors()) {
//            return "admin/product/add-product";
//        }
//        String page = "edit-product";
//        model.addAttribute("page", page);

        System.out.println("++++++++++++++++++++++++++++++++++++++++++ Da vao day");


        try {
            Product product = productRepo.findById(id).orElse(null);
            model.addAttribute("product", product);

            Date updateAt = new Date();


            if(result.hasErrors()) {
                return  "redirect:/admin/product/edit/{id}";
            }

            if (!productDto.getImageFile().isEmpty()) {

                // Delete old image
                String uploadDir = "public/images/products/";
                Path oldImagePath = Paths.get(uploadDir + product.getImage());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }

                // Save new image file
                MultipartFile image = productDto.getImageFile();
                String storageFileName = updateAt.getTime() + "_" + image.getOriginalFilename();
                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);

                }catch (Exception ex) {
                    System.out.println("Exception" + ex.getMessage());
                }

                product.setImage(storageFileName);

            }

            // Lưu ảnh phụ
            List<MultipartFile> additionalImages = productDto.getImageFiles();
            List<String> imagePaths = new ArrayList<>();

            for (MultipartFile additionalImage : additionalImages) {
                if (!additionalImage.isEmpty()) {
                    String additionalStorageFileName = updateAt.getTime() + "_" + additionalImage.getOriginalFilename();
                    try (InputStream inputStream = additionalImage.getInputStream()) {
                        Files.copy(inputStream, Paths.get("public/images/products/" + additionalStorageFileName), StandardCopyOption.REPLACE_EXISTING);
                        imagePaths.add(additionalStorageFileName);
                    } catch (Exception ex) {
                        System.out.println("Exception" + ex.getMessage());
                    }
                }
            }
            product.setImages(imagePaths);

            Category category = categoryRepo.findByCategoryName(productDto.getCategory());

            product.setProductName(productDto.getProductName());
            product.setPrice(productDto.getPrice());
            product.setQuantity(productDto.getQuantity());
            product.setCategory(category);
            product.setUpdatedAt(updateAt);


            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật product thành công!");

            productRepo.save(product);

        }catch (Exception ex) {
            System.out.println("Exception" + ex.getMessage());
        }
        return "redirect:/admin/product";
//        return "redirect:/admin/product";

    }

//    @PostMapping("/admin/product/edit")
//    public String edit(Model model) {
//                System.out.println("++++++++++++++++++++++++++++++++++++++++++ Da vao day");
//
//        String page = "admin-index";
//        return "admin-index";
//    }


    @GetMapping("/admin/product/search")
    public String search(Model model,
                         @RequestParam String data,
                         Pageable pageable){
        data = data.trim();
        String page = "admin-product";
        model.addAttribute("page", page);

//        Page<Product> pageProducts =  productRepo.findAll(pageable);
//        List<Product> products = pageProducts.toList();
//        List<Product> products = new ArrayList<>();
        Page<Product> pageProducts;

        if(data.equals("")) {
//            products = productRepo.findAll();
            pageProducts = productRepo.findAll(pageable);

        } else{
//            System.out.println("++++++++++++++++++++++++++++++ data: " + data);
//            System.out.println("++++++++++++++++++++++++++++++ pageable: " + pageable);
//            products = productRepo.getData(data);
            pageProducts = productRepo.findByProductNameContaining(data, pageable);

        }
        List<Product> products = pageProducts.getContent();
        model.addAttribute("products", products);
        model.addAttribute("totalPage", pageProducts.getTotalPages());
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("searchData", data);
//        model.addAttribute("products", products);
        return "admin-index";
    }


//    @GetMapping("admin/product/sort/{colSort}")
//    public String sortData(@PathVariable String colSort,
//                           Model model){
//        String page = "admin-product";
//        model.addAttribute("page", page);
//
//        String typeSort = "ASC";
//        List<Product> products = productRepo.sortData();
//        model.addAttribute("products", products);
//
//        return "admin-index";
//    }

    @GetMapping("/admin/product/list/top3")
    public String getTop3(Model model,RedirectAttributes redirectAttributes) {
        List<Integer> productsTop3QuantitySold = productRepo.findTopByQuantitySold(PageRequest.of(0, 3));

        List<Product> productsTop3 = productRepo.findByQuantitySoldBetween(productsTop3QuantitySold.get(2), productsTop3QuantitySold.get(0));
        model.addAttribute("productsTop3", productsTop3);
        redirectAttributes.addFlashAttribute("productsTop3", productsTop3);
        return "redirect:/admin/product";
    }
    @GetMapping("/admin/product/list/button3")
    public String getButton3(Model model,RedirectAttributes redirectAttributes) {
        List<Integer> productsButton3QuantitySold = productRepo.findButtonByQuantitySold(PageRequest.of(0, 3));
        List<Product> productsButton3 = productRepo.findByQuantitySoldBetween(productsButton3QuantitySold.get(0), productsButton3QuantitySold.get(2));

        model.addAttribute("productsButton3", productsButton3);
        redirectAttributes.addFlashAttribute("productsButton3", productsButton3);
        return "redirect:/admin/product";
    }





}
