-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 03, 2024 at 07:30 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tb_36_37`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `id` int(11) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`id`, `created_at`, `updated_at`, `address`, `email`, `full_name`, `password`, `phone`, `role`, `username`) VALUES
(1, '2024-05-29 19:08:17.000000', NULL, 'ki tuc xa My Dinh 2, Ha Noi', 'tung12@gmail.com', 'The Tung', '$2a$12$0uOnGrccD0Ewp9ZCsL3z2.k2a53zezvUiLzjSm9wlYl5RerYi888u', '0337737958', 'ROLE_ADMIN', 'tung'),
(4, '2024-05-31 15:18:52.000000', NULL, 'Ha Noi', 'gnar2312004@gmail.com', 'Tu', '$2a$12$0uOnGrccD0Ewp9ZCsL3z2.k2a53zezvUiLzjSm9wlYl5RerYi888u', '22334455', 'ROLE_USER', 'Tu123'),
(5, '2024-06-01 16:59:55.000000', NULL, NULL, 'trankiencuong1204@gmail.com', NULL, '$2a$10$OMo5aUyMq6oQ3SqaRwIRhOh0XLA9Ru64LMtSGVkHDZqvzU2GoiXI.', NULL, 'ROLE_USER', 'cuong'),
(6, '2024-06-03 09:21:38.000000', NULL, 'Thái BÌnh', 'anh12@gmail.com', 'Pham The ANh', '$2a$10$buYpShCb8AP6i8vRInfA3u.t.5T5YeAeKKl2Jkr50mgPRdapEuMz2', '1234567890', 'ROLE_USER', 'anh');

-- --------------------------------------------------------

--
-- Table structure for table `brands`
--

CREATE TABLE `brands` (
  `id` int(11) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `brand_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `brands`
--

INSERT INTO `brands` (`id`, `created_at`, `updated_at`, `brand_name`) VALUES
(1, NULL, NULL, 'SamSung'),
(2, NULL, NULL, 'Iphone');

-- --------------------------------------------------------

--
-- Table structure for table `cart_item`
--

CREATE TABLE `cart_item` (
  `id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `account_id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `product_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `cart_item`
--

INSERT INTO `cart_item` (`id`, `quantity`, `account_id`, `order_id`, `product_id`) VALUES
(2, 107, 5, NULL, 3),
(4, 5, 4, 3, 3),
(5, 5, 4, 3, 2),
(8, 1, 4, 5, 2),
(22, 4, 4, 7, 3),
(23, 10, 5, NULL, 20),
(24, 5, 4, 3, 20),
(25, 1, 4, 4, 20),
(26, 3, 4, 6, 20),
(27, 1, 4, 10, 3),
(28, 4, 4, 11, 3),
(29, 5, 1, 12, 20),
(30, 4, 1, 13, 4),
(31, 4, 4, 14, 2),
(32, 4, 4, 15, 2);

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `created_at`, `updated_at`, `category_name`, `brand_id`) VALUES
(1, NULL, NULL, 'Samsung S24', 1),
(2, NULL, NULL, 'Samsung S23', 1),
(3, NULL, NULL, 'Samsung S22', 1),
(4, NULL, NULL, 'Samsung S21', 1),
(5, NULL, NULL, 'Samsung S20', 1),
(8, NULL, NULL, 'Samsung Galaxy Z Flip4', 1),
(11, NULL, NULL, 'Samsung Note 20', 1),
(15, NULL, NULL, 'Iphone 14', 2),
(17, NULL, NULL, 'Iphone 13', 2);

-- --------------------------------------------------------

--
-- Table structure for table `discount_code`
--

CREATE TABLE `discount_code` (
  `id` int(11) NOT NULL,
  `code_name` varchar(255) DEFAULT NULL,
  `percent` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `discount_code`
--

INSERT INTO `discount_code` (`id`, `code_name`, `percent`, `quantity`) VALUES
(1, 'abcd70', 70, 4),
(2, 'THETUNG20', 20, 8);

-- --------------------------------------------------------

--
-- Table structure for table `news`
--

CREATE TABLE `news` (
  `id` int(11) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `new_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `news`
--

INSERT INTO `news` (`id`, `created_at`, `updated_at`, `content`, `description`, `image`, `new_name`) VALUES
(2, '2024-06-01 19:58:56.000000', NULL, 'djakfhdsjk\r\n', 'Mieu ta tuyen sinh don nguyen 3', '1717246736233_anh_8-3.jpg', 'tuyen sinh dơn nguyen 3'),
(3, '2024-06-01 19:58:56.000000', NULL, 'djakfhdsjk\r\n', 'Mieu ta tuyen sinh don nguyen 3', '1717246736233_anh_8-3.jpg', 'tuyen sinh dơn nguyen 3'),
(4, '2024-06-01 19:58:56.000000', NULL, 'djakfhdsjk\r\n', 'Mieu ta tuyen sinh don nguyen 3', '1717246736233_anh_8-3.jpg', 'tuyen sinh dơn nguyen 3');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `discount_code` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_price` int(11) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  `cancel_order` bit(1) NOT NULL,
  `order_status` enum('XAC_NHAN','DANG_GIAO','DA_GIAO') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `created_at`, `updated_at`, `discount_code`, `note`, `status`, `total_price`, `account_id`, `cancel_order`, `order_status`) VALUES
(3, '2024-06-01 20:30:31.000000', NULL, NULL, NULL, 'Cho xac nhan', 208680000, 4, b'1', 'XAC_NHAN'),
(4, '2024-06-01 20:36:33.000000', NULL, NULL, NULL, 'Cho xac nhan', 22990000, 4, b'1', 'XAC_NHAN'),
(5, '2024-06-01 20:43:53.000000', '2024-06-02 22:33:56.000000', NULL, 'dong hang can thanh', 'Cho xac nhan', 16990000, 4, b'1', 'DANG_GIAO'),
(6, '2024-06-01 21:58:19.000000', '2024-06-02 23:56:55.000000', NULL, 'fdgsdgsd', 'Cho xac nhan', 55176000, 4, b'1', 'DANG_GIAO'),
(7, '2024-06-02 14:28:43.000000', NULL, NULL, 'Don 2/6', NULL, 39008000, 4, b'0', 'XAC_NHAN'),
(10, '2024-06-02 22:35:36.000000', NULL, NULL, '12342134', NULL, 9752000, 4, b'0', 'XAC_NHAN'),
(11, '2024-06-02 23:56:48.000000', NULL, NULL, '124324', NULL, 39008000, 4, b'0', 'XAC_NHAN'),
(12, '2024-06-03 10:22:19.000000', '2024-06-03 10:22:47.000000', NULL, 'don 03/06', NULL, 91960000, 1, b'1', 'XAC_NHAN'),
(13, '2024-06-03 10:29:38.000000', NULL, NULL, 'ko co gi', NULL, 23968000, 1, b'0', 'XAC_NHAN'),
(14, '2024-06-03 11:22:35.000000', NULL, NULL, '65e46', NULL, 67960000, 4, b'0', 'XAC_NHAN'),
(15, '2024-06-03 11:24:20.000000', NULL, NULL, '4325', NULL, 67960000, 4, b'0', 'XAC_NHAN');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `specification_id` int(11) DEFAULT NULL,
  `quantity_sold` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `created_at`, `updated_at`, `description`, `image`, `price`, `product_name`, `quantity`, `category_id`, `specification_id`, `quantity_sold`) VALUES
(2, '2024-06-01 14:17:02.000000', '2024-06-03 10:38:05.000000', NULL, '1717231023722_samsungs23ultra_vang.jpg', 16990000, 'Samsung S23 Ultra Pro', 91, 2, NULL, 9),
(3, '2024-06-01 14:26:23.000000', NULL, NULL, '1717226783235_samsungs22ultra_den.png', 12190000, 'Samsung S22 Ultra', 0, 3, NULL, 20),
(4, '2024-06-01 14:33:46.000000', '2024-06-02 14:42:11.000000', NULL, '1717228410658_samsung_z_flip4_vang.png', 7490000, 'Samsung Galaxy Z Flip4', 96, 8, NULL, 4),
(5, '2024-06-01 14:47:26.000000', '2024-06-02 14:31:18.000000', NULL, '1717228449331_samsungs22_plus_hong.png', 7990000, 'Samsung S22 Plus', 100, 3, NULL, 0),
(6, '2024-06-01 14:52:36.000000', '2024-06-02 14:32:09.000000', NULL, '1717228498615_samsungs21_ultra_bac.png', 8590000, 'Samsung S21 Ultra', 100, 4, NULL, 0),
(7, '2024-06-01 15:03:16.000000', '2024-06-02 14:31:58.000000', NULL, '1717229368064_samsungs21_plus_bac.jpg', 6390000, 'Samsung S21 Plus', 1000, 4, NULL, 0),
(8, '2024-06-01 15:08:46.000000', '2024-06-02 14:31:45.000000', NULL, '1717229326459_samsungs21_tim.jpg', 6990000, 'Samsung S21', 100, 4, NULL, 0),
(9, '2024-06-01 15:15:30.000000', '2024-06-02 14:32:54.000000', NULL, '1717229730541_samsung_note_20_ultra_trang.jpg', 8290000, 'Samsung Note 20 Ultra', 100, 11, NULL, 0),
(10, '2024-06-01 15:20:48.000000', '2024-06-02 14:32:29.000000', NULL, '1717230048915_samsungs21_fe_trang.jpg', 6290000, 'Samsung S21 FE', 100, 4, NULL, 0),
(11, '2024-06-01 15:25:30.000000', NULL, NULL, '1717230330038_samsung_note_20_den.jpg', 5790000, 'Samsung Note 20', 100, 11, NULL, 0),
(12, '2024-06-01 15:33:20.000000', NULL, NULL, '1717230800230_samsungs20_ultra_xam.jpg', 6590000, 'Samsung S20 Ultra', 100, 5, NULL, 0),
(13, '2024-06-01 16:21:46.000000', '2024-06-02 14:33:19.000000', NULL, '1717233706598_iphone14_pro_max_vang.jpg', 20390000, 'Iphone 14 Pro Max', 100, 15, NULL, 0),
(14, '2024-06-01 16:27:26.000000', NULL, NULL, '1717234046303_iphone14_pro_vang.jpg', 17390000, 'Iphone 14 Pro', 100, 15, NULL, 0),
(15, '2024-06-01 16:45:25.000000', '2024-06-02 14:33:50.000000', NULL, '1717235125049_iphone13_pro_max_trang.jpg', 15390000, 'Iphone 13 Pro Max', 100, 17, NULL, 0),
(16, '2024-06-01 16:52:34.000000', NULL, NULL, '1717235554776_iphone13_pro_trang.jpg', 13190000, 'Iphone 13 Pro', 100, 17, NULL, 0),
(20, '2024-06-01 13:59:19.000000', '2024-06-01 14:24:59.000000', NULL, '1717225159861_samsungs24ultra_cam.jpg', 22990000, 'Samsung S24 Ultra', 84, 1, NULL, 16);

-- --------------------------------------------------------

--
-- Table structure for table `product_images`
--

CREATE TABLE `product_images` (
  `product_id` int(11) NOT NULL,
  `images` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `product_images`
--

INSERT INTO `product_images` (`product_id`, `images`) VALUES
(3, '1717226783235_samsungs22ultra_do.png'),
(3, '1717226783235_samsungs22ultra_trang.png'),
(3, '1717226783235_samsungs22ultra_xanhla.png'),
(11, '1717230330038_samsung_note_20_vang.jpg'),
(11, '1717230330038_samsung_note_20_xanh.jpg'),
(12, '1717230800230_samsungs20_ultra_den.webp'),
(14, '1717234046303_iphone14_pro_den.jpg'),
(14, '1717234046303_iphone14_pro_tim.jpg'),
(14, '1717234046303_iphone14_pro_trang.jpg'),
(16, '1717235554776_iphone13_pro_vang.jpg'),
(16, '1717235554776_iphone13_pro_xam.jpg'),
(16, '1717235554776_iphone13_pro_xanhduong.jpg'),
(16, '1717235554776_iphone13_pro_xanhla.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `specification`
--

CREATE TABLE `specification` (
  `id` int(11) NOT NULL,
  `advanced_photography` varchar(255) DEFAULT NULL,
  `battery_capacity` varchar(255) DEFAULT NULL,
  `charging` varchar(255) DEFAULT NULL,
  `chipset` varchar(255) DEFAULT NULL,
  `dimensions` varchar(255) DEFAULT NULL,
  `external_memory` varchar(255) DEFAULT NULL,
  `front_camera` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `memory` varchar(255) DEFAULT NULL,
  `mobile_network` varchar(255) DEFAULT NULL,
  `operating_system` varchar(255) DEFAULT NULL,
  `ram` varchar(255) DEFAULT NULL,
  `rear_camera` varchar(255) DEFAULT NULL,
  `screen` varchar(255) DEFAULT NULL,
  `sim` varchar(255) DEFAULT NULL,
  `video_recording` varchar(255) DEFAULT NULL,
  `weight` varchar(255) DEFAULT NULL,
  `wifi` varchar(255) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `specification`
--

INSERT INTO `specification` (`id`, `advanced_photography`, `battery_capacity`, `charging`, `chipset`, `dimensions`, `external_memory`, `front_camera`, `language`, `memory`, `mobile_network`, `operating_system`, `ram`, `rear_camera`, `screen`, `sim`, `video_recording`, `weight`, `wifi`, `product_id`, `created_at`, `updated_at`) VALUES
(1, 'Ảnh Raw  Zoom quang học  Zoom kỹ thuật số  Xóa phông  Video chân dung  Video chuyên nghiệp  Tự động lấy nét (AF)  Trôi nhanh thời gian (Time Lapse)  Toàn cảnh (Panorama)  Super HDR  Siêu độ phân giải  Quét mã QR  Quay video hiển thị kép  Quay Siêu chậm (S', '5000 mAh', 'Sạc nhanh 45W', 'Snapdragon 8 Gen 3', '163.3 x 77.9 x 8.9 mm', 'Không hỗ trợ', '12MP', 'Tiếng Việt', '256G/512G', '2G/3G/4G/5G', 'Android 1', '12G', 'Chính 200 MP & Phụ 50 MP, 12 MP, 10 MP', 'Dynamic AMOLED 2X 6,8 inches 2K+ (1440 x 3088 Pixels)', '2 Sim (esim + sim vật lý)', 'HD 720p@30fps  FullHD 1080p@60fps  FullHD 1080p@30fps  FullHD 1080p@240fps  FullHD 1080p@120fps  8K 4320p@30fps  4K 2160p@60fps  4K 2160p@30fps  4K 2160p@120fps', 'Nặng 228 g', 'Wi-Fi 802.11 a/b/g/n/ac/6, dual-band, Wi-Fi Direct, hotspot', NULL, NULL, NULL),
(2, 'AI Camera  Ban đêm (Night Mode)  Chuyên nghiệp (Pro)  Chạm lấy nét  Chống rung quang học (OIS)  Chống rung điện tử kỹ thuật số (EIS)  Góc rộng (Wide)  Góc siêu rộng (Ultrawide)  HDR  Làm đẹp (Beautify)  Lấy nét bằng laser  Lấy nét theo pha (PDAF)  Nhã', '5000 mAh', 'Sạc nhanh 45W, sạc không dây 15W', 'Snapdragon 8 Gen 2 8 nhân', '163.3 x 77.9 x 8.9 mm', 'Không hỗ trợ', '12MP, F2.2, Dual Pixel AF', 'Tiếng Việt', '256G/512G', '2G/3G/4G/5G', 'Android 13 x One UI 5.1', '8G', 'Chính 200 MP & Phụ 12 MP, 10 MP, 10 MP', 'Dynamic AMOLED 2X 6,8 inches 2K+ (1440 x 3088 Pixels)', '2 Sim (esim + sim vật lý)', '4K 2160p@30fps  4K 2160p@60fps  8K 4320p@24fps  FullHD 1080p@240fps  FullHD 1080p@30fps  FullHD 1080p@60fps  HD 720p@30fps  HD 720p@960fps', 'Nặng 228 g', 'Wi-Fi 802.11 a/b/g/n/ac/6, dual-band, Wi-Fi Direct, hotspot', 2, NULL, NULL),
(4, 'AI Camera  Ban đêm (Night Mode)  Chuyên nghiệp (Pro)  Chạm lấy nét  Chống rung quang học (OIS)  Chống rung điện tử kỹ thuật số (EIS)  Góc rộng (Wide)  Góc siêu rộng (Ultrawide)  HDR  Làm đẹp (Beautify)  Lấy nét bằng laser  Lấy nét theo pha (PDAF)  Nhã', '5000 mAh', 'Sạc nhanh 45W, sạc không dây', 'Snapdragon 8 Gen 1 8 nhân', '163.3 x 77.9 x 8.9 mm', 'Không hỗ trợ', '40MP', 'Tiếng Việt', '128G/256G/512G', '2G/3G/4G/5G', 'Android 13', '8G/12G', 'Chính 108 MP & Phụ 12 MP, 10 MP, 10 MP', 'Dynamic AMOLED 2X 6,8 inches 2K+ (1440 x 3088 Pixels)', '2 Sim (esim + sim vật lý)', '4K 2160p@30fps  4K 2160p@60fps  8K 4320p@24fps  FullHD 1080p@240fps  FullHD 1080p@30fps  FullHD 1080p@60fps  HD 720p@30fps  HD 720p@960fps', 'Nặng 228 g', 'Wi-Fi 802.11 a/b/g/n/ac/6, dual-band, Wi-Fi Direct, hotspot', 3, NULL, NULL),
(5, 'AI Camera  Ban đêm (Night Mode)  Bộ lọc màu  Chuyên nghiệp (Pro)  Chống rung quang học (OIS)  Góc rộng (Wide)  Góc siêu rộng (Ultrawide)  Hiệu ứng Bokeh  Live Photo  Làm đẹp  Quay chậm (Slow Motion)  Quay Siêu chậm (Super Slow Motion)  Quay video hiển th', '3700 mAh', 'Fast battery charging 45W USB Power Delivery 3.0 Fast Qi/PMA wireless charging 15W Power bank/Reverse wireless charging 9W', 'Snapdragon 8+ Gen 1 (4 nm)', 'Dài 165.2 mm - Ngang 71.9 mm - Dày 6.9 mm', 'microSD, up to 1 TB', '10 MP, f/2.4', 'Đa ngôn ngữ', '128 GB', 'Hỗ trợ 5G', 'Android 13', '8 GB RAM', 'Camera góc rộng: 12 MP, f/1.8, PDAF, OIS Camera góc siêu rộng: 12 MP, f/2.2, 123˚', 'Dynamic AMOLED 2X', '2 SIM (nano‑SIM và eSIM)', '4K 2160p@30fps  4K 2160p@60fps  FullHD 1080p@30fps  FullHD 1080p@60fps  HD 720p@30fps', 'Nặng 187 g', 'Wi-Fi 802.11 a/b/g/n/ac, dual-band, Wi-Fi Direct, hotspot', 4, NULL, NULL),
(6, 'AI Camera  Ban đêm (Night Mode)  Chuyên nghiệp (Pro)  Chạm lấy nét  Chống rung quang học (OIS)  Chống rung điện tử kỹ thuật số (EIS)  Góc rộng (Wide)  Góc siêu rộng (Ultrawide)  HDR  Làm đẹp (Beautify)  Lấy nét bằng laser  Lấy nét theo pha (PDAF)  Nhã', '4500 mAh', 'Sạc nhanh 25W, sạc không dây', 'Snapdragon 8 Gen 1 8 nhân', 'Dài 157.4 mm - Ngang 75.8 mm - Dày 7.6 mm', 'microSDXC', '10 MP, f/2.2, 26mm (wide)', 'Tiếng Việt', '128GB', '2G/3G/4G/5G', 'android 13', '8GB', '50 MP, f/1.8, (wide) + 10 MP, f/2.4, (telephoto) + 12 MP, f/2.2, 13mm, 120˚ (ultrawide)', 'Dynamic AMOLED 2X 6.6 inches 1080 x 2340 pixels, 120 Hz', '2 Sim (esim + sim vật lý)', '4K 2160p@30fps  4K 2160p@60fps  8K 4320p@24fps  FullHD 1080p@240fps  FullHD 1080p@30fps  FullHD 1080p@60fps  HD 720p@30fps  HD 720p@960fps', 'Nặng 195 g', 'Wi-Fi 802.11 a/b/g/n/ac/6, dual-band, Wi-Fi Direct, hotspot', 5, NULL, NULL),
(7, 'AI Camera  Ban đêm (Night Mode)  Chuyên nghiệp (Pro)  Chạm lấy nét  Chống rung quang học (OIS)  Chống rung điện tử kỹ thuật số (EIS)  Góc rộng (Wide)  Góc siêu rộng (Ultrawide)  HDR  Làm đẹp (Beautify)  Lấy nét bằng laser  Lấy nét theo pha (PDAF)  Nhã', '5000 mAh', 'Sạc nhanh 25W, sạc không dây', 'Qualcomm SM8350 Snapdragon 888 5G (5 nm)', 'Dài 165.1 mm - Ngang 75.6 mm - Dày 8.9 mm', 'Không', '40MP', 'Tiếng Việt', '128GB', '2G/3G/4G/5G', 'android 13', '12GB', '108 MP, 12MP và 10MP+ 10MP+ 0.3 MP', 'Dynamic AMOLED 2X6.8\"Quad HD+ (2K+)', '2 Sim (esim + sim vật lý)', '4K 2160p@30fps  4K 2160p@60fps  8K 4320p@24fps  FullHD 1080p@240fps  FullHD 1080p@30fps  FullHD 1080p@60fps  HD 720p@960fps', 'Nặng 228 g', 'Wi-Fi 802.11 a/b/g/n/ac/6, dual-band, Wi-Fi Direct, hotspot', 6, NULL, NULL),
(8, 'AI Camera  Ban đêm (Night Mode)  Chuyên nghiệp (Pro)  Chạm lấy nét  Chống rung quang học (OIS)  Chống rung điện tử kỹ thuật số (EIS)  Góc rộng (Wide)  Góc siêu rộng (Ultrawide)  HDR  Làm đẹp (Beautify)  Lấy nét bằng laser  Lấy nét theo pha (PDAF)  Nhã', '4800 mAh', 'Sạc nhanh 25W, sạc không dây', 'Qualcomm SM8350 Snapdragon 888 5G (5 nm)', '161.4 x 75.6 x 7.8 mm', 'microSDXC', '10 MP, f/2.2, 26mm (wide), 1/3.24\", 1.22µm, Dual Pixel PDAF', 'Tiếng Việt', '128GB', '2G/3G/4G/5G', 'android 13', '8GB', '64 MP, f/2.0, 28mm + 12 MP, f/1.8 + 12 MP, f/2.2', 'Dynamic AMOLED 2X 6.7 inches 1080 x 2400 pixels', '2 Sim (esim + sim vật lý)', '4K 2160p@30fps  4K 2160p@60fps  8K 4320p@24fps  FullHD 1080p@240fps  FullHD 1080p@30fps  FullHD 1080p@60fps  HD 720p@960fps', 'Nặng 202 g', 'Wi-Fi 802.11 a/b/g/n/ac/6, dual-band, Wi-Fi Direct, hotspot', 7, NULL, NULL),
(9, 'AI Camera Ban đêm (Night Mode) Chuyên nghiệp (Pro) Chạm lấy nét Chống rung quang học (OIS) Chống rung điện tử kỹ thuật số (EIS) Góc rộng (Wide) Góc siêu rộng (Ultrawide) HDR Làm đẹp (Beautify) Lấy nét bằng laser Lấy nét theo pha (PDAF) Nhã', '4000 mAh', 'Sạc nhanh 25W, sạc không dây', 'Qualcomm SM8350 Snapdragon 888 5G (5 nm)', '151.7 x 71.2 x 7.9 mm', NULL, '10 MP, f/2.2, 26mm (wide), 1/3.24\", 1.22µm, Dual Pixel PDAF', 'Tiếng Việt', '128GB', '2G/3G/4G/5G', 'android 13', '8GB', '64 MP, f/2.0, 28mm (telephoto) + 12 MP, f/1.8, 26mm (wide) + 12 MP, f/2.2, 13mm, 120˚ (ultrawide)', '6.2 inches, 1080 x 2400 pixels, Dynamic AMOLED 2X, 120Hz, HDR10+, 1300 nits (peak)', '2 Sim (esim + sim vật lý)', '4K 2160p@30fps 4K 2160p@60fps 8K 4320p@24fps FullHD 1080p@240fps FullHD 1080p@30fps FullHD 1080p@60fps HD 720p@960fps', '171 g', 'Wi-Fi 802.11 a/b/g/n/ac/6, dual-band, Wi-Fi Direct, hotspot', 8, NULL, NULL),
(10, 'LED flash, auto-HDR, panorama', '4500 mAh', 'Sạc nhanh 25W, sạc không dây', 'Snapdragon 865+', '164.8 x 77.2 x 8.1 mm', NULL, '10 MP', 'Tiếng Việt', '128GB', '2G/3G/4G/5G', 'android 13', '12GB', '108 MP + 12 MP + 12 MP', 'Dynamic AMOLED 6.9 inches 1440 x 3088 pixels', '2 Sim (esim + sim vật lý)', '8K@24fps, 4K@30/60fps, 1080p@30/60/240fps, 720p@960fps, HDR10+, stereo sound rec., gyro-EIS & OIS', '208 g', 'Wi-Fi 802.11 a/b/g/n/ac/6, dual-band, Wi-Fi Direct, hotspot', 9, NULL, NULL),
(11, 'Quay video hiển thị kép  Trôi nhanh thời gian (Time Lapse)  Góc siêu rộng (Ultrawide)  Zoom kỹ thuật số  Quay chậm (Slow Motion)  Xóa phông  Toàn cảnh (Panorama)  Ban đêm (Night Mode)  Chuyên nghiệp (Pro)  HDR', '4500 mAh', 'Sạc nhanh 25W, sạc không dây', 'Qualcomm SM8350 Snapdragon 888 5G (5 nm)', 'Dài 155.7 mm - Ngang 74.5 mm - Dày 7.9 mm', NULL, '32 MP', 'Tiếng Việt', '128GB', '2G/3G/4G/5G', 'android 13', '6GB', 'Chính 12 MP & Phụ 12 MP, 8 MP', '6.4\" Full HD+ (1080 x 2340 Pixels) - Tần số quét 120 Hz', '1 Sim', 'FullHD 1080p@60fps4K 2160p@60fps', 'Nặng 177 g', 'Wi-Fi 802.11 a/b/g/n/ac/6, dual-band, Wi-Fi Direct, hotspot', 10, NULL, NULL),
(12, 'LED flash, auto-HDR, panorama', '4300 mAh', 'Sạc nhanh 25W USB Sạc ngược Sạc không dây', 'Snapdragon 865', '161.6 x 75.2 x 8.3 mm', NULL, '10 MP', 'Tiếng Việt', '128GB', '2G / 3G / 4G / 5G', 'android 13', '8GB', '12 MP + 64 MP + 12 MP', 'Super AMOLED Plus 6.7 inches 1080 x 2400 pixels', '2 SIM', '8K@24fps, 4K@30/60fps, 1080p@30/60/240fps, 720p@960fps, HDR10+, stereo sound rec., gyro-EIS & OIS', '192 g', 'Wi-Fi 802.11 a/b/g/n/ac/6, dual-band, Wi-Fi Direct, hotspot', 11, NULL, NULL),
(13, 'Chụp HDR, Chụp xóa phông, Chụp đêm, Lấy nét tự động', '5000 mAh', 'Sạc nhanh 45W Sạc nhanh không dây 15W Power Delivery 3.0', 'Snapdragon 865', '166.9 x 76 x 8.8 mm', NULL, '40 MP, f/2.2, 26mm (wide), PDAF', 'Tiếng Việt', '128 GB', '2G/3G/4G', 'android 12', '12 GB', 'Camera chính: 108 MP, f/1.8, 26mm (wide), PDAF, OIS Camera siêu rộng:12 MP, f/2.2, 13mm (ultrawide), 1.4µm, AF, Super Steady video Camera Zoom: 48 MP, f/3.6, 102mm (telephoto), 1/2\", 0.8µm, PDAF, OIS, 10x hybrid optical zoom Camera TOF: 0.3 MP', 'Dynamic AMOLED 6.9 inches 3200 x 1440 pixels', '2 SIM (Nano-SIM)', '8K@24fps, 4K@30/60fps, 1080p@30/60/240fps, 720p@960fps, HDR10+, stereo sound rec., gyro-EIS & OIS', '222 g', 'Wi-Fi 802.11 a/b/g/n/ac/ax, dual-band, Wi-Fi Direct, hotspot', 12, NULL, NULL),
(14, 'Quad-LED dual-tone flash, HDR (photo/panorama)', '4.352 mAh', 'Sạc nhanh, sạc không dây', 'Apple A16 Bionic', '160.7 x 77.6 x 7.9 mm', NULL, '12 MP', 'Tiếng Việt', '128/256/512 GB', '2G/3G/4G/5G', 'iOS 15', '6 GB', '48MP + 12MP + 12MP', 'Super Retina XDR OLED 6.7 inches Dynamic Island', '1 sim vật lý + 1 esim', 'Quay phim Cinematic, Chế độ ProRes, Quay phim Dolby Vision HDR', '240 gr', 'Wi-Fi 802.11 a/b/g/n/ac/ax, dual-band, hotspot', 13, NULL, NULL),
(15, 'Quad-LED dual-tone flash, HDR (photo/panorama)', '3200 mAh', 'Sạc nhanh, sạc không dây', 'Apple A14 Bionic 6 nhân', 'Dài 174.5mm - Ngang: 71.5mm - Dày 7.85mm', NULL, '12 MP', 'Tiếng Việt', '128/256 GB', '2G/3G/4G/5G', 'iOS 15', '6 GB', '48MP + 12MP + 12MP', 'Super Retina XDR OLED 6.1 inches Dynamic Island', '1 sim vật lý + 1 esim', '4K @24 fps, 25 fps, 30 fps, 60 fps, 1080p @25 fps, 30 fps, 60 fps, 720p @30 fps, 4K HDR @30 fps, 2.8K @ 60 fps, HDR với Dolby Vision @60 fps, ProRes 4K @ 30fps, Chuyển động chậm 1080p @ 120fps, 240 fps', '206g', 'Wi-Fi 802.11 a/b/g/n/ac/ax, dual-band, hotspot', 14, NULL, NULL),
(16, 'Quad-LED dual-tone flash, HDR (photo/panorama)', '4352 mAh', 'Sạc nhanh, sạc không dây', 'Apple A15 Bionic', 'Dài 160.8 mm - Ngang 78.1 mm - Dày 7.65 mm', NULL, '12 MP', 'Tiếng Việt', '128G/256G/512G', '2G/ 3G/ 4G', 'iOS 15', '6 GB', '12MP + 12MP + 12MP', 'Super Retina XDR OLED 6.7 inches 1284 x 2778 Pixels, Tần số quét 120 Hz', '1 SIM NANO', 'Ban đêm (Night Mode), Chạm lấy nét, Chống rung quang học (OIS), Cinematic, Deep Fusion, Dolby Vision HDR, Góc rộng (Wide), Góc siêu rộng (Ultrawide), Nhận diện khuôn mặt, Quay chậm (Slow Motion), Siêu cận (Macro), Smart HDR 4, Toàn cảnh (Panorama), Trôi', 'Nặng 240 g', 'Wi-Fi 802.11 a/b/g/n/ac/ax, dual-band, hotspot', 15, NULL, NULL),
(17, 'Quad-LED dual-tone flash, HDR (photo/panorama)', '3095 mAh', 'Sạc nhanh, sạc không dây', 'Apple A15 Bionic 6 nhân', 'Dài 146.7 mm - Ngang 71.5 mm - Dày 7.65 mm', NULL, '12 MP', 'Tiếng Việt', '128G/256G/512G', '2G/ 3G/ 4G / 5G', 'iOS 15', '6 GB', 'Cảm biến LiDAR, Triple 12Mp + 12Mp + 12Mp', 'Super Retina XDR OLED 6.1 inches 1170 x 2532 Pixels', '1 Nano SIM & 1 eSIM', 'Ban đêm (Night Mode), Chạm lấy nét, Chống rung quang học (OIS), Cinematic, Deep Fusion, Dolby Vision HDR, Góc rộng (Wide), Góc siêu rộng (Ultrawide), Nhận diện khuôn mặt, Quay chậm (Slow Motion), Siêu cận (Macro), Smart HDR 4, Toàn cảnh (Panorama), Trôi', 'Nặng 204 g', 'Wi-Fi 802.11 a/b/g/n/ac/ax, dual-band, hotspot', 16, NULL, NULL),
(19, '', '', '', '', '', '', '', '', '', '', 'Android 14', '', '', '', '', '', '', '', NULL, NULL, NULL),
(20, '', '', '', '', '', '', '', '', '', '', 'Android 14', '', '', '', '', '', '', '', NULL, NULL, NULL),
(21, '', '', '', '', '', '', '', '', '', '', 'Android 14', '', '', '', '', '', '', '', NULL, NULL, NULL),
(25, '', '', '', '', '', '', '', 'Tiéng Viẹt', '', '', 'Android 14', '', '', '', '', '', '', '', 20, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_n7ihswpy07ci568w34q0oi8he` (`email`),
  ADD UNIQUE KEY `UK_k8h1bgqoplx0rkngj01pm1rgp` (`username`);

--
-- Indexes for table `brands`
--
ALTER TABLE `brands`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cart_item`
--
ALTER TABLE `cart_item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKr73mlc9qnqbuohor52oa82e8u` (`account_id`),
  ADD KEY `FK3mu9lcrqocn2rdcm6xhbqrg3b` (`order_id`),
  ADD KEY `FKqkqmvkmbtiaqn2nfqf25ymfs2` (`product_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqo7dpwn19xkagkhnbqva3sgws` (`brand_id`);

--
-- Indexes for table `discount_code`
--
ALTER TABLE `discount_code`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `news`
--
ALTER TABLE `news`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKagh5svlor3slbay6tq5wqor1o` (`account_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_4ura3utlwfm3ce3t2ybd7uahu` (`specification_id`),
  ADD KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`);

--
-- Indexes for table `product_images`
--
ALTER TABLE `product_images`
  ADD KEY `FKqnq71xsohugpqwf3c9gxmsuy` (`product_id`);

--
-- Indexes for table `specification`
--
ALTER TABLE `specification`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_arqbqg58r9r0atlx0ayict9g4` (`product_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `brands`
--
ALTER TABLE `brands`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `cart_item`
--
ALTER TABLE `cart_item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `discount_code`
--
ALTER TABLE `discount_code`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `news`
--
ALTER TABLE `news`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `specification`
--
ALTER TABLE `specification`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart_item`
--
ALTER TABLE `cart_item`
  ADD CONSTRAINT `FK3mu9lcrqocn2rdcm6xhbqrg3b` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `FKqkqmvkmbtiaqn2nfqf25ymfs2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `FKr73mlc9qnqbuohor52oa82e8u` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`);

--
-- Constraints for table `categories`
--
ALTER TABLE `categories`
  ADD CONSTRAINT `FKqo7dpwn19xkagkhnbqva3sgws` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FKagh5svlor3slbay6tq5wqor1o` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FKlvqan4le1d04bguf0b0mcktsv` FOREIGN KEY (`specification_id`) REFERENCES `specification` (`id`),
  ADD CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

--
-- Constraints for table `product_images`
--
ALTER TABLE `product_images`
  ADD CONSTRAINT `FKqnq71xsohugpqwf3c9gxmsuy` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `specification`
--
ALTER TABLE `specification`
  ADD CONSTRAINT `FKm12ldn36q7bp32q207j0j54ec` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
