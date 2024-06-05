package com.tt.dto;


import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
public class NewDto {

    private Integer id;

    @NotEmpty(message = "The name is required")
    private String newName;

    private String description;

    private String content;

    private MultipartFile imageFile;

    private LocalDateTime createdAt;

    public String getCreatedDay() {
        return createdAt.format(DateTimeFormatter.ofPattern("dd"));
    }

    public String getCreatedMonth() {
        return createdAt.format(DateTimeFormatter.ofPattern("MM"));
    }}
