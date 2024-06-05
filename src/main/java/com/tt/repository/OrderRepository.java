package com.tt.repository;

import com.tt.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByAccount_Id(Integer id);

    List<Order> findByAccount_IdAndCancelOrderOrderByCreatedAtDesc(Integer id, Boolean cancelOrder);


    Order findByAccount_IdAndCreatedAt(Integer accountId, Date timeBuy);

    List<Order> findAllByOrderByCreatedAtDesc();

    List<Order> findByAccount_IdOrderByCreatedAtDesc(Integer id);

    List<Order> findByCancelOrderOrderByCreatedAtDesc(Boolean cancelOrder);


//    @Query("SELECT COUNT(o) FROM Order o WHERE DATE(o.createdAt) = CURRENT_DATE")
//    Integer countOrdersToday();
//
//    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE DATE(o.createdDate) = CURRENT_DATE")
//    Long sumTotalRevenueToday();
//
//    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE YEAR(o.createdDate) = YEAR(CURRENT_DATE) AND MONTH(o.createdDate) = MONTH(CURRENT_DATE)")
//    Long sumTotalRevenueThisMonth();


    List<Order> findByCreatedAtBetween(Date start, Date end);



}
