package com.tt.controller.admin;

import com.tt.dto.NewDto;
import com.tt.entity.Account;
import com.tt.entity.New;
import com.tt.helper.HtmlSanitizerUtil;
import com.tt.repository.NewRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.jsoup.Jsoup;
import org.jsoup.safety.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class NewController {

    @Autowired
    private NewRepository newRepo;

    public NewController(NewRepository newRepo) {
        this.newRepo = newRepo;
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

    @GetMapping("/admin/new")
    public String adminNew(Model model) {
        String page = "admin-new";
        model.addAttribute("page", page);

        // Lấy tin tức từ mới nhất
        List<New> news = newRepo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("news", news); // Tên attribute dùng để gọi ở file HTML
        return "admin-index";
    }

    @GetMapping("/admin/new/delete/{id}")
    public String deleteNew(@PathVariable Integer id,
                                 Model model) {
        String page = "admin-new";
        New tinTuc = newRepo.findById(id).orElse(null);
        model.addAttribute("page", page);

        if (tinTuc == null) {
            String result = "false";
            model.addAttribute("result", result);
            return "admin-index";
        }
        newRepo.delete(tinTuc);
        String result = "true";
        model.addAttribute("result", result);

        List<New> news = newRepo.findAll();
        model.addAttribute("news", news);

        return "admin-index";
    }

    @GetMapping("/admin/new/add")
    public String addNew(Model model) {
        String page = "add-new";
        model.addAttribute("page", page);

        NewDto newDto = new NewDto();
        model.addAttribute("newDto", newDto);

        return "admin-index";
    }

    @PostMapping("/admin/new/add")
    public String saveNew( @Valid @ModelAttribute("newDto") NewDto newDto,
                               BindingResult result,
                           @RequestParam MultipartFile imageFile) throws IOException {
//        System.out.println("+++++++++++++++++++" + newDto);
//        System.out.println("+++++++++++++++++++" + imageFile);
        if(newDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("newDto", "imageFile", "This image file is required!"));
        }
        if(result.hasErrors()) {
            return "redirect:/admin/new";
        }

        // Save image
        MultipartFile image = newDto.getImageFile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

        try{
            String uploadDir = "public/images/news/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            try(InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        New tinTuc = new New();

//        String safeContent = Jsoup.clean(newDto.getContent(), Whitelist.basic());
//        tinTuc.setContent(safeContent);

        // Làm sạch dữ liệu đầu vào
        String sanitizedContent = HtmlSanitizerUtil.sanitize(newDto.getContent());
        tinTuc.setContent(sanitizedContent);
        tinTuc.setNewName(newDto.getNewName());
        tinTuc.setDescription(newDto.getDescription());
//        tinTuc.setContent(newDto.getContent());
        tinTuc.setImage(storageFileName);
        tinTuc.setCreatedAt(createdAt);

        newRepo.save(tinTuc);

        return "redirect:/admin/new";
    }


    @GetMapping("/admin/new/edit/{id}")
    public String editNew(@PathVariable Integer id ,Model model) {
        String page = "edit-new";
        model.addAttribute("page", page);


        New tinTuc = newRepo.findById(id).orElse(null);
        model.addAttribute("tinTuc", tinTuc);

//        List<New> news = newRepo.findAll();
//        model.addAttribute("news", news);

        NewDto newDto = new NewDto();
        newDto.setId(tinTuc.getId());
        newDto.setNewName(tinTuc.getNewName());
        newDto.setDescription(tinTuc.getDescription());
        newDto.setContent(tinTuc.getContent());
        model.addAttribute("newDto", newDto);

        return "admin-index";
    }

    @PostMapping("/admin/new/edit")
    public String updateNew(@RequestParam Integer id,
                                Model model,
                                @Valid @ModelAttribute("newDto") NewDto newDto,
                                BindingResult result,
                            RedirectAttributes redirectAttributes) {

        try {

            System.out.println("++++++++++++++++++++++++++++ Id: " + id);
            New tinTuc = newRepo.findById(id).orElse(null);
            System.out.println("+++++++++++++++++ " + tinTuc);
            model.addAttribute("tinTuc", tinTuc);

            Date updateAt = new Date();


            if(result.hasErrors()) {
                System.out.println(result.getAllErrors());
//                return  "redirect:/admin/new/edit/{id}";
                return  "redirect:/admin/new";
            }

            if (!newDto.getImageFile().isEmpty()) {

                // Delete old image
                String uploadDir = "public/images/news/";
                Path oldImagePath = Paths.get(uploadDir + tinTuc.getImage());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }

                // Save new image file
                MultipartFile image = newDto.getImageFile();
                String storageFileName = updateAt.getTime() + "_" + image.getOriginalFilename();
                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);

                }catch (Exception ex) {
                    System.out.println("Exception" + ex.getMessage());
                }

                tinTuc.setImage(storageFileName);

            }



            tinTuc.setNewName(newDto.getNewName());
            tinTuc.setDescription(newDto.getDescription());
            tinTuc.setContent(newDto.getContent());

            tinTuc.setUpdatedAt(updateAt);

            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật Tin tuc thành công!");

            newRepo.save(tinTuc);

        }catch (Exception ex) {
            System.out.println("Exception" + ex.getMessage());
        }
        return "redirect:/admin/new";

    }


    @GetMapping("/admin/new/search")
    public String search(Model model,
                         @RequestParam String data){
        data = data.trim();
        String page = "admin-new";
        model.addAttribute("page", page);
//        System.out.println("++++++++++++++++++++DAta: " + data);

        List<New> news = new ArrayList<>();
        if(data.equals("")) {
            news = newRepo.findAll();
        } else{
            news = newRepo.getData(data);
        }
        model.addAttribute("news", news);
        return "admin-index";
    }
}
