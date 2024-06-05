package com.tt.controller.admin;


import com.tt.dto.BrandDto;
import com.tt.dto.DiscountCodeDto;
import com.tt.dto.NewDto;
import com.tt.dto.OrderDto;
import com.tt.entity.*;
import com.tt.helper.OrderStatus;
import com.tt.repository.CartItemRepository;
import com.tt.repository.OrderRepository;
import com.tt.repository.ProductRepository;
import com.tt.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    CartItemRepository cartItemRepo;

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

    @GetMapping("/admin/order")
    public String getAllOrder(Model model) {
        String page = "admin-order";
        model.addAttribute("page", page);

        List<Order> orders = orderRepo.findByCancelOrderOrderByCreatedAtDesc(false);
        model.addAttribute("orders", orders);
        return "admin-index";
    }

    @GetMapping("/admin/cancelOrder")
    public String getAllCancelOrder(Model model) {
        String page = "admin-cancel-order";
        model.addAttribute("page", page);

        List<Order> orders = orderRepo.findByCancelOrderOrderByCreatedAtDesc(true);
        model.addAttribute("orders", orders);
        return "admin-index";
    }

    @GetMapping("/admin/order/edit/{id}")
    public String editOrder(@PathVariable Integer id,
                            Model model) {
        Order order = orderRepo.findById(id).orElse(null);
        OrderDto orderDto = new OrderDto();

        orderDto.setId(order.getId());
        orderDto.setNote(order.getNote());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setAccount(order.getAccount());


        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("order", orderDto);
        model.addAttribute("page", "edit-order");
        return "admin-index";
    }

    @PostMapping("/admin/order/edit")
    public String updateOrder(@RequestParam Integer id,
                            Model model,
                            @Valid @ModelAttribute("orderDto") OrderDto orderDto,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {

        try {

            Order order = orderRepo.findById(id).orElse(null);

            Date updateAt = new Date();


            if(result.hasErrors()) {
                System.out.println(result.getAllErrors());
//                return  "redirect:/admin/new/edit/{id}";
                return  "redirect:/admin/order";
            }

            order.setNote(orderDto.getNote());
            order.setOrderStatus(orderDto.getOrderStatus());
            order.setUpdatedAt(updateAt);

            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật ddown hang thành công!");

            orderRepo.save(order);

        }catch (Exception ex) {
            System.out.println("Exception" + ex.getMessage());
        }
        return "redirect:/admin/order";

    }




    @GetMapping("/admin/order/delete/{id}")
    public String deleteOrder(@PathVariable Integer id,
                              Model model,
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
            productRepo.save(product);
        }

        redirectAttributes.addFlashAttribute("messageCancelOrder","Ban da xoa don hang thanh cong");
        return "redirect:/admin/order";
    }



    @GetMapping("/admin/order/add")
    public String addOrder(Model model) {
        String page = "add-order";
        model.addAttribute("page", page);

        OrderDto orderDto = new OrderDto();
        model.addAttribute("orderDto", orderDto);
        return "admin-index";
    }



//    @PostMapping("/admin/order/add")
//    public String saveOrder(@Valid @ModelAttribute("orderDto") OrderDto orderDto,
//                            BindingResult result,
//                            Model model,
//                            HttpSession session) {
//        String page = "add-order";
//        model.addAttribute("page", page);
//        if (result.hasErrors()) {
//            return "admin-index";        }
//
//        try {
//            Account loggedInUser = (Account) session.getAttribute("account");
//            if (loggedInUser == null) {
////                List<CartItem> cartItems = cartItemRepo.findByAccount_Id(loggedInUser.getId());
////                model.addAttribute("cartItems", cartItems);
//
//            }
//            discountCodeService.addDiscountCode(discountCodeDto);
//            return "redirect:/admin/discountCode";
//        } catch (Exception e) {
//
//
//            String messageError = "DiscountCode da ton tai";
//            model.addAttribute("messageError", messageError);
//            return "admin-index";
//
//        }
//
//
//    }



}
