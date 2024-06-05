package com.tt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "specification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specification extends AuditEntity{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    // Thông tin chung
    private String operatingSystem;
    private String language;

    // Màn hình
    private String screen;

    // Chụp hình & Quay phim
    private String rearCamera;
    private String frontCamera;
    private String advancedPhotography;
    private String videoRecording;

    // CPU & RAM
    private String chipset;
    private String ram;

    // Bộ nhớ & Lưu trữ
    private String memory;
    private String externalMemory;

    // Thiết kế & Trọng lượng
    private String dimensions;
    private String weight;

    // Thông tin pin
    private String batteryCapacity;

    // Kết nối & Cổng giao tiếp
    private String mobileNetwork;
    private String sim;
    private String wifi;
    private String charging;

    @OneToOne(cascade = CascadeType.ALL)
    private Product product;

    // Getters and setters
    // ...
}
