package com.tt.controller.admin;


import com.tt.dto.AccountDto;
import com.tt.entity.Account;
import com.tt.repository.AccountRepository;
import com.tt.service.AccountService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;

@Controller
@Slf4j
public class AccountController {

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

    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepo;

    public AccountController(AccountService accountService,
                                AccountRepository accountRepo) {
        this.accountService = accountService;
        this.accountRepo = accountRepo;
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



    @GetMapping("/admin/account")
    public String adminAccount(Model model,
                               Pageable pageable) {

//        sendEmail("Binh ban banh bo ben bo bien"," binh bao ba bao bo");
        String page = "admin-account";
        model.addAttribute("page", page);

        Page<Account> pageAccounts = accountRepo.findAll(pageable);
        List<Account> accounts = pageAccounts.toList();


        model.addAttribute("accounts", accounts);
        model.addAttribute("totalPage", pageAccounts.getTotalPages());
        model.addAttribute("currentPage", pageable.getPageNumber());

        return "admin-index";
    }

    @GetMapping("/admin/account/delete/{id}")
    public String deleteAccount(@PathVariable Integer id,
                                Model model,
                                RedirectAttributes redirectAttributes){

        Account account = accountService.findById(id);
        if(account == null) {
            System.out.println("Not found Account with id = " + id);
        }
        if(account.getOrders() != null) {
            redirectAttributes.addFlashAttribute("messageDelete", "Người dùng đang mua hàng, không thể xóa");
            return "redirect:/admin/account";
        }
        accountRepo.delete(account);
//        return "account-list";
        return "redirect:/admin/account";
    }

    @GetMapping("/admin/account/add")
    public String addAccount(Model model) {
        String page = "add-account";
        model.addAttribute("page", page);

        AccountDto accountDto = new AccountDto();
        model.addAttribute("accountDto", accountDto);
        return "admin-index";
    }

    @PostMapping("/admin/account/add")
    public String saveAccount( @Valid @ModelAttribute("accountDto") AccountDto accountDto,
                               BindingResult result,
                               Model model,
                               HttpSession session) throws IOException {

        if(result.hasErrors()) {
            return "redirect:/admin/account";
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
            String page = "add-account";
            model.addAttribute("page", page);
            return "admin-index";
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
        String page = "auth-email";
        model.addAttribute("page", page);
        return "admin-index";
//        accountRepo.save(account);
//        return "redirect:/admin/account";
    }
//    @Valid @ModelAttribute("accountDto") AccountDto accountDto,
//    BindingResult result

    @PostMapping("/admin/account/addAccount")
    public String addAccount(@RequestParam Integer enteredCode,
                             Model model,
                             HttpSession session){
        Integer verificationCode = (Integer) session.getAttribute("verificationCode");
        AccountDto accountDto = (AccountDto) session.getAttribute("accountDto");

        if (verificationCode == null || accountDto == null) {
            model.addAttribute("errorSession", "Session hết hạn. Vui lòng thử lại.");
            return "redirect:/admin/account/add";
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
            return "redirect:/admin/account";
        } else {
            model.addAttribute("errorVerificationCode", "Mã xác nhận không đúng. Vui lòng thử lại.");
//            model.addAttribute("verificationCode", verificationCode);
//            model.addAttribute("accountDto", accountDto);
            String page = "auth-email";
            model.addAttribute("page", page);
            return "admin-index";
        }
    }

    @GetMapping("/admin/account/edit/{id}")
    public String editAccount(@PathVariable Integer id ,Model model) {
        String page = "edit-account";
        model.addAttribute("page", page);

        Account account = accountService.findById(id);
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setUsername(account.getUsername());
        accountDto.setEmail(account.getEmail());
        model.addAttribute("accountDto", accountDto);
        return "admin-index";
    }

    @PostMapping("/admin/account/edit")
    public String updateAccount(@RequestParam Integer id,
                                Model model,
                                @Valid @ModelAttribute("accountDto") AccountDto accountDto,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        try {
            Account account =  accountService.findById(id);

            Date updateAt = new Date();


            if(result.hasErrors()) {
                return  "redirect:/admin/product/edit/{id}";
            }
            account.setUsername(accountDto.getUsername());
            account.setEmail(accountDto.getEmail());
            account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
            account.setUpdatedAt(updateAt);

            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật Account thành công!");

            accountRepo.save(account);

        }catch (Exception ex) {
            System.out.println("Exception" + ex.getMessage());
        }
        return "redirect:/admin/account";
    }

    @GetMapping("/admin/account/search")
    public String search(Model model,
                         @RequestParam String data){
        data = data.trim();
        String page = "admin-account";
        model.addAttribute("page", page);
//        System.out.println("++++++++++++++++++++DAta: " + data);

//        List<Product> products = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        if(data.equals("")) {
//            products = productRepo.findAll();
            accounts = accountRepo.findAll();
        } else{
//            products = productRepo.getData(data);
            accounts = accountRepo.getData(data);
        }
//        model.addAttribute("products", products);
        model.addAttribute("accounts", accounts);
        return "admin-index";
    }



}
