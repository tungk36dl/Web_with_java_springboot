package com.tt.controller.client;

import com.tt.dto.CartItemDto;
import com.tt.dto.NewDto;
import com.tt.entity.*;
import com.tt.repository.*;
import com.tt.req.CartItemReq;
import com.tt.service.CartItemService;
import com.tt.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class ClientIndexController {

    @Autowired
    private NewRepository newRepo;

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
    private DiscountCodeRepository discountCodeRepo;

    @Autowired
    private CartItemService cartItemService;

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

    @GetMapping
    public String clientIndex(Model model, HttpSession session) {
        String page = "client-index";
        model.addAttribute("page", page);

        List<Product> products = productRepo.findAll();
        model.addAttribute("products", products);

//        List<Category> categories = categoryRepo.findAll();
//        model.addAttribute("categories", categories);

        List<New> news = newRepo.findAll();
        model.addAttribute("news", news);
//
//        List<Brand> brands = brandRepo.findAll();
//        model.addAttribute("brands", brands);

//        Account loggedInUser = (Account) session.getAttribute("account");
//        if (loggedInUser != null) {
//            List<CartItem> cartItems = cartItemRepo.findByAccount_Id(loggedInUser.getId());
//            model.addAttribute("cartItems", cartItems);
//        }

        return "client-index";
    }

    @GetMapping("contact")
    public String clientContact(Model model) {
        String page = "client-contact";
        model.addAttribute("page", page);
        return "client-index";
    }
    @GetMapping("new")
    public String clientNew(Model model) {
        String page = "new-list-client";
        model.addAttribute("page", page);

        List<New> news = newRepo.findAllByOrderByCreatedAtDesc();

//        List<NewDto> newDtos = new ArrayList<>();
        model.addAttribute("news", news);
        return "client-index";
    }


    @GetMapping("new/{id}")
    public String getDetail(@PathVariable Integer id,
                            Model model) {
        String page = "new-detail";
        model.addAttribute("page", page);
        New  tinTuc= newRepo.findById(id).orElse(null);
        model.addAttribute("tinTuc", tinTuc);

        if(tinTuc == null) {

        }

        List<New> news = newRepo.findAllByOrderByCreatedAtDesc();
//        List<NewDto> newDtos = new ArrayList<>();
        model.addAttribute("news", news);
        return "client-index";
    }



//    @PostMapping("cart")
//    public String checkCode(@RequestParam String codeName,
//                            Model model,
//                            RedirectAttributes redirectAttributes,
//                            HttpSession session) {
//        Optional<DiscountCode> discountCode = discountCodeRepo.findByCodeName(codeName);
//
//        if(discountCode.isEmpty()){
//            redirectAttributes.addFlashAttribute("messageCheckCode", "Mã giảm giá không chính xác!!!");
//            redirectAttributes.addFlashAttribute("status", "danger");
//            return "redirect:/cart";
//        }
//
//        DiscountCode discountCode1 = discountCode.get();
//        if(discountCode1.getQuantity() == 0) {
//            redirectAttributes.addFlashAttribute("messageCheckCode", "Mã giảm giá không chính xác!!!");
//            redirectAttributes.addFlashAttribute("status", "danger");
//            return "redirect:/cart";
//        }
//        model.addAttribute("discountCode1", discountCode1);
//        redirectAttributes.addFlashAttribute("messageCheckCode", "Mã giảm giá chính xác");
//        redirectAttributes.addFlashAttribute("status", "success");
//        // Giusp giảm số lượng mã giảm giá
////        discountCode1.setQuantity(discountCode1.getQuantity() - 1);
////        discountCodeRepo.save(discountCode1);
//
//        String page = "client-cart";
//        model.addAttribute("page", page);
//
//
//        Account loggedInUser = (Account) session.getAttribute("account");
//        if (loggedInUser != null) {
////            System.out.println("++++++++++++++++++ Khong rong!!");
//            List<CartItem> cartItems = cartItemRepo.findByAccount_Id(loggedInUser.getId());
//            model.addAttribute("cartItems", cartItems);
//
//
//        }
//        return "client-index";
//
//    }


}
