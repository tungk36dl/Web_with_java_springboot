package com.tt.controller.client;

import com.tt.dto.AccountDto;
import com.tt.entity.Account;
import com.tt.entity.Brand;
import com.tt.entity.Category;
import com.tt.repository.AccountRepository;
import com.tt.repository.BrandRepository;
import com.tt.repository.CategoryRepository;
import com.tt.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class AccountCLientController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender; // Là mail của mình

    @Value("${abc.a.b.c}")
    private String demo;

    private void sendEmail(String subject, String content, String emailTo) {
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailTo);
            mailMessage.setSubject(subject);
            mailMessage.setText(content);

            // Sending the mail
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println("Send email fail");
        }
    }

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

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



    @GetMapping("/register")
    public String addAccount(Model model) {
        String page = "register-account";
        model.addAttribute("page", page);

        AccountDto accountDto = new AccountDto();
        model.addAttribute("accountDto", accountDto);
        return "/clients/account/register-account";
    }

//    @PostMapping("/register")
//    public String registerAccount(
//                             @Valid @ModelAttribute("accountDto") AccountDto accountDto,
//                             BindingResult result){
//        Account account1 = new Account();
//        account1.setUsername(accountDto.getUsername());
//        account1.setEmail(accountDto.getEmail());
//        account1.setPassword(passwordEncoder.encode(accountDto.getPassword()));
//        account1.setRole("ROLE_USER");
//        account1.setCreatedAt(new Date());
//
//            accountRepo.save(account1);
//            return "redirect:/";
//
//    }

    @PostMapping("/register")
    public String saveAccount( @Valid @ModelAttribute("accountDto") AccountDto accountDto,
                               BindingResult result,
                               Model model,
                               HttpSession session) throws IOException {

        if(result.hasErrors()) {
            return "redirect:/register";
        }




        Integer errorCount = 0;

        if (accountRepo.existsByEmail(accountDto.getEmail())) {
            model.addAttribute("errorEmail", "Email đã tồn tại.");
            errorCount += 1;
        }

        if (accountRepo.existsByUsername(accountDto.getUsername())) {
            model.addAttribute("errorUsername", "Username đã tồn tại.");
            errorCount += 1;
        }

        if(errorCount != 0) {
            return "/clients/account/register-account";
        }

//        Random random = new Random();
//
//        // Tạo số ngẫu nhiên trong phạm vi [1000, 9999]
//        int randomNumber;
//        do {
//            randomNumber = random.nextInt(10000); // Tạo số ngẫu nhiên trong phạm vi [0, 10000)
//        } while (randomNumber < 1000 || randomNumber > 9999);


        Random random = new Random();
        int verificationCode = 1000 + random.nextInt(9000);

        session.setAttribute("verificationCode", verificationCode);
        session.setAttribute("accountDto", accountDto);
        sendEmail("Create Acocunt", "Banj da taoj tai khoan thanh cong. Ma xac nhan la: " + verificationCode, accountDto.getEmail());

        model.addAttribute("verificationCode", verificationCode);
        model.addAttribute("accountDto", accountDto);

        return "/clients/account/auth-email";
//        accountRepo.save(account);
//        return "redirect:/admin/account";
    }
//    @Valid @ModelAttribute("accountDto") AccountDto accountDto,
//    BindingResult result

    @PostMapping("saveAccount")
    public String addAccount(@RequestParam Integer enteredCode,
                             Model model,
                             HttpSession session){
        Integer verificationCode = (Integer) session.getAttribute("verificationCode");
        AccountDto accountDto = (AccountDto) session.getAttribute("accountDto");

        if (verificationCode == null || accountDto == null) {
            model.addAttribute("errorSession", "Session hết hạn. Vui lòng thử lại.");
            return "redirect:/register";
        }
        if (verificationCode.equals(enteredCode)) {
            Account account = new Account();
            account.setUsername(accountDto.getUsername());
            account.setEmail(accountDto.getEmail());
            account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
            account.setCreatedAt(new Date());
            account.setRole("ROLE_USER");

            accountRepo.save(account);
            // Xóa mã xác nhận và thông tin tài khoản khỏi session sau khi hoàn thành
            session.removeAttribute("verificationCode");
            session.removeAttribute("accountDto");
            return "redirect:/";
        } else {
            model.addAttribute("errorVerificationCode", "Mã xác nhận không đúng. Vui lòng thử lại.");
//            model.addAttribute("verificationCode", verificationCode);
//            model.addAttribute("accountDto", accountDto);
            return "/clients/account/auth-email";
        }
    }


    @GetMapping("/account/edit/{id}")
    public String editAccount(@PathVariable Integer id , Model model,
                              HttpSession session) {

        // Kiểm tra nếu người dùng đã đăng nhập
        Account loggedInUser = (Account) session.getAttribute("account");
        if (loggedInUser != null) {
            model.addAttribute("loggedInUser", loggedInUser);
//            model.addAttribute("loggedInUserId", loggedInUser.getId());

        }
        String page = "edit-account";
        model.addAttribute("page", page);

        Account account1 = (Account) session.getAttribute("account");
        if(account1 == null) {
            return "redirect:/";
        }

        Account account = accountRepo.findById(id).orElse(null);
        model.addAttribute("account", account);
        return "client-index";

    }

    @PostMapping("/account/edit")
    public String saveAccount(@Valid @ModelAttribute("account") Account account,
                              @RequestParam Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes,
                              HttpSession session){

        Account account1 = accountRepo.findById(id).orElse(null);
        account1.setUsername(account.getUsername());
        account1.setEmail(account.getEmail());
        account1.setPassword(passwordEncoder.encode(account.getPassword()));
        account1.setFullName(account.getFullName());
        account1.setPhone(account.getPhone());
        account1.setAddress(account.getAddress());

        accountRepo.save(account1);

        redirectAttributes.addFlashAttribute("messageUpdateAccount", "Cập nhật acccount thành công");
        session.removeAttribute("account");
        session.setAttribute("account", account);
        return "redirect:/";
    }

}
