package com.tt.controller.admin;

import com.tt.entity.Account;
import com.tt.entity.Order;
import com.tt.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("admin")
@Slf4j
public class AdminController {

    @Autowired
    OrderRepository orderRepo;

    @ModelAttribute
    public void addAttributes(Model model, HttpSession session) {

        // Kiểm tra nếu người dùng đã đăng nhập
        Account loggedInUser = (Account) session.getAttribute("account");
        if (loggedInUser != null) {
            model.addAttribute("loggedInUser", loggedInUser);
//            model.addAttribute("loggedInUserId", loggedInUser.getId());

        }
    }

    @GetMapping
    public String adminIndex(Model model) {
        String page = "admin-home";
        model.addAttribute("page", page);

        Date dateNow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
        int year = calendar.get(Calendar.YEAR);

        model.addAttribute("day", day);
        model.addAttribute("month", month);
        model.addAttribute("year", year);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar2.getTime();

//        calendar2.set(Calendar.HOUR_OF_DAY, 23);
//        calendar2.set(Calendar.MINUTE, 59);
//        calendar2.set(Calendar.SECOND, 59);
//        calendar2.set(Calendar.MILLISECOND, 999);
//        Date endOfDay = calendar2.getTime();


        List<Order> orders = orderRepo.findByCreatedAtBetween(startOfDay, dateNow);


        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(Calendar.DAY_OF_MONTH, 1);
        calendar3.set(Calendar.HOUR_OF_DAY, 0);
        calendar3.set(Calendar.MINUTE, 0);
        calendar3.set(Calendar.SECOND, 0);
        calendar3.set(Calendar.MILLISECOND, 0);
        Date startOfMonth = calendar3.getTime();

        List<Order> orderList = orderRepo.findByCreatedAtBetween(startOfMonth, dateNow);

        model.addAttribute("totalOrder", orders.size());
        Integer totalPriceOrder = 0;
        Integer totalCancelOrder = 0;
        for (Order order : orders) {
            if(!order.isCancelOrder()) {
                totalPriceOrder += order.getTotalPrice();
            }else{
                totalCancelOrder += 1;
            }
        }

        Integer totalPriceofMonth = 0;
        for (Order order : orderList) {
            if(!order.isCancelOrder()) {
                totalPriceofMonth += order.getTotalPrice();
            }
        }

        model.addAttribute("totalPriceOrder", totalPriceOrder);
        model.addAttribute("totalCancelOrder", totalCancelOrder);
        model.addAttribute("totalPriceofMonth", totalPriceofMonth);
        return "admin-index";
    }

}
