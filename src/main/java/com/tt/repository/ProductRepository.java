package com.tt.repository;

import com.tt.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "FROM Product WHERE productName LIKE CONCAT('%', :productName , '%')")
    List<Product> getData(String productName);


//    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:data% OR p.category.categoryName LIKE %:data%")
//    Page<Product> searchProducts(@Param("data") String data, Pageable pageable);

    Page<Product> findByProductNameContaining(String productName, Pageable pageable);


//    @Query(value = "FROM Product WHERE productName LIKE CONCAT('%', :productName , '%')")
//    List<Product> getData(String productName);

    List<Product> findByCategory_CategoryName(String categoryName);
    Page<Product> findByCategory_CategoryNameContaining(String categoryName, Pageable pageable);


    Page<Product> findByCategory_Brand_BrandNameContaining(String brandName, Pageable pageable);

    List<Product> findByCategory_Brand_BrandName(String brandName);

    Product findByProductName(String productName);

    // Sắp xếp theo giá tăng dần
//    List<Product> findAllByOrderByPriceAsc();

    Page<Product> findAllByOrderByPriceAsc(Pageable pageable);

    // Sắp xếp theo giá giảm dần
//    List<Product> findAllByOrderByPriceDesc();
    Page<Product> findAllByOrderByPriceDesc(Pageable pageable);

    // Tìm kiếm sản phẩm theo khoảng giá
    Page<Product> findByPriceBetween(Integer minPrice, Integer maxPrice, Pageable pageable);

    Page<Product> findByQuantityBetween(Integer minQuantity, Integer maxQuantity, Pageable pageable);

    List<Product> findByQuantitySoldBetween(Integer min, Integer max);

    // Sửa lại phương thức repository như sau
    @Query("SELECT DISTINCT  p.quantitySold FROM Product p ORDER BY p.quantitySold DESC")
    List<Integer> findTopByQuantitySold(Pageable pageable);

    // Sửa lại phương thức repository như sau
    @Query("SELECT DISTINCT p.quantitySold FROM Product p ORDER BY p.quantitySold ASC")
    List<Integer> findButtonByQuantitySold(Pageable pageable);

}
