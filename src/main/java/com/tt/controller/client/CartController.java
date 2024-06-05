package com.tt.controller.client;


import com.tt.entity.*;
import com.tt.helper.OrderStatus;
import com.tt.repository.*;
import com.tt.req.CartItemReq;
import com.tt.service.AccountService;
import com.tt.service.CartItemService;
import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class CartController {

    @Autowired
    DiscountCodeRepository discountCodeRepo;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    CartItemRepository cartItemRepo;

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    CategoryRepository categoryRepo;

    @Autowired
    BrandRepository brandRepo;

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


    @GetMapping("cart")
    public String clientCart(Model model, HttpSession session) {
        String page = "client-cart";
        model.addAttribute("page", page);

        Account loggedInUser = (Account) session.getAttribute("account");
        if (loggedInUser != null) {
//            System.out.println("++++++++++++++++++ Khong rong!!");
            List<CartItem> cartItems = cartItemRepo.findByAccount_IdAndOrder(loggedInUser.getId(), null);
            model.addAttribute("cartItems", cartItems);

            List<Order> orders = orderRepo.findByAccount_IdAndCancelOrderOrderByCreatedAtDesc(loggedInUser.getId(), false);
            model.addAttribute("orders", orders);

            Account account = accountRepo.findByUsername(loggedInUser.getUsername());
            model.addAttribute("account", account);

            String note = (String) session.getAttribute("noteOrder");

            if(note != null) {
                model.addAttribute("note", note);
            }

        }
        return "client-index";
    }

    @PostMapping("/cart/updateQuantity")
    public ResponseEntity<?> updateQuantity(@RequestBody CartItemReq cartItemReq,
                                            HttpSession session) {
        try {

            Account loggedInUser = (Account) session.getAttribute("account");
            if (loggedInUser == null) {
                // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
//                return "redirect:/login";
            }

            Account accountFromDb = accountRepo.findById(loggedInUser.getId()).orElse(null);


            cartItemService.updateQuantity(cartItemReq.getProductId(),accountFromDb.getId(), cartItemReq.getQuantity());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("cart/updateAccount")
    public String updateAcocuntForOrder(@RequestParam String fullName,
                                        @RequestParam String phone,
                                        @RequestParam String address,
                                        @RequestParam String note,
                                        HttpSession session) {

        Account loggedInUser = (Account) session.getAttribute("account");

        if (loggedInUser == null) {
            return "redirect:/login"; // redirect to login if user is not logged in
        }


        Account account = accountRepo.findByUsername(loggedInUser.getUsername());

        account.setFullName(fullName);
        account.setPhone(phone);
        account.setAddress(address);

        accountRepo.save(account);
        session.setAttribute("noteOrder", note);
        return "redirect:/cart";

    }

    @PostMapping("cart/applyDiscount")
    public ResponseEntity<?> applyDiscount(@RequestParam String codeName) {
        Optional<DiscountCode> discountCodeOptional = discountCodeRepo.findByCodeName(codeName);

        if (discountCodeOptional.isPresent()) {
            DiscountCode discountCode = discountCodeOptional.get();
            return ResponseEntity.ok(discountCode);
        } else {
            return ResponseEntity.badRequest().body("Invalid discount code");
        }
    }

    //  Xóa cartItem
    @GetMapping("cartItem/delete/{id}")
    public String deleteCartItem(@PathVariable Integer id,
                                 Model model,
                                 HttpSession session) {
        String page = "client-cart";
        model.addAttribute("page", page);

        CartItem cartItem = cartItemRepo.findById(id).orElse(null);

        if (cartItem == null) {
            String result = "false";
            model.addAttribute("result", result);
            return "client-index";
        }
        cartItemRepo.delete(cartItem);
        String result = "true";
        model.addAttribute("result", result);

//        Account loggedInUser = (Account) session.getAttribute("account");
//        if (loggedInUser != null) {
////            System.out.println("++++++++++++++++++ Khong rong!!");
//            List<CartItem> cartItems = cartItemRepo.findByAccount_IdAndIsOrder(loggedInUser.getId(), false);
//            model.addAttribute("cartItems", cartItems);
//
//            Account account = accountRepo.findByUsername(loggedInUser.getUsername());
//            model.addAttribute("account", account);
//
//        }
//        return "client-index";


        return "redirect:/cart";
    }

    @PostMapping("cart/order")
    public String saveOrder(HttpSession session,
                            @RequestParam Integer totalPriceOrder,
                            @RequestParam String discountCodeName,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        Account loggedInUser = (Account) session.getAttribute("account");

        if (loggedInUser == null) {
            return "redirect:/login"; // redirect to login if user is not logged in
        }


//        System.out.println("+++++++++++++++++ totalPriceOrder" + totalPriceOrder);

        String note = (String) session.getAttribute("noteOrder");

        OrderStatus orderStatus = OrderStatus.XAC_NHAN;
        Account account = accountRepo.findByUsername(loggedInUser.getUsername());
        Order order = new Order();
        order.setAccount(account);
        order.setNote(note);
        session.removeAttribute("noteOrder");
        order.setTotalPrice(totalPriceOrder);
        order.setOrderStatus(orderStatus);
        order.setCancelOrder(false);
        Date timeBuy = new Date();
        order.setCreatedAt(timeBuy);

        orderRepo.save(order);

        Order order1 = orderRepo.findByAccount_IdAndCreatedAt(account.getId(), timeBuy);

        // ---------------Lỗi tại không save lại cartItem
//        List<CartItem> cartItems =  account.getCartItems();
//        for (CartItem cartItem : cartItems) {
//            cartItem.setOrder(order1);
//        }



        List<CartItem> cartItems = cartItemRepo.findByAccount_IdAndOrder(account.getId(), null);
        cartItems.forEach(cartItem -> {
            cartItem.setOrder(order1);
            cartItemRepo.save(cartItem);

            Product product = productRepo.findByProductName(cartItem.getProduct().getProductName());
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            product.setQuantitySold(product.getQuantitySold() + cartItem.getQuantity());
            productRepo.save(product);
        });

        if(discountCodeName != null) {
            DiscountCode discountCode = discountCodeRepo.findByCodeName(discountCodeName).orElse(null);
            if(discountCode != null){
                discountCode.setQuantity(discountCode.getQuantity() - 1);
                discountCodeRepo.save(discountCode);
            }
        }

//        redirectAttributes.addFlashAttribute("messageOrder", "Ban da dat thanh cong don hang");

        model.addAttribute("messageOrder",  "Ban da dat thanh cong don hang");
        return "redirect:/cart";
    }

    @GetMapping("cart/cancelOrder/{id}")
    public String cancelOrder(@PathVariable Integer id,
                              RedirectAttributes redirectAttributes) {
        Order order = orderRepo.findById(id).orElse(null);
        order.setCancelOrder(true);
        order.setUpdatedAt(new Date());
        orderRepo.save(order);
// Update laij soso luong san pham
        List<CartItem> cartItems = cartItemRepo.findByOrder_Id(order.getId());
        for (CartItem cartItem : cartItems) {
            Product product = productRepo.findById(cartItem.getProduct().getId()).orElse(null);
            product.setQuantity(product.getQuantity() + cartItem.getQuantity());
            product.setQuantitySold(product.getQuantitySold() - cartItem.getQuantity());
            productRepo.save(product);
        }

        redirectAttributes.addFlashAttribute("messageCancelOrder","Ban da xoa don hang thanh cong");
        return "redirect:/cart";
    }

}
