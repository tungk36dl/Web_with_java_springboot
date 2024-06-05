package com.tt.repository;

import com.tt.entity.New;
import com.tt.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewRepository extends JpaRepository<New, Integer> {
    New findByNewName(String newName);

    @Query(value = "FROM New WHERE newName LIKE CONCAT('%', :newName , '%')")
    List<New> getData(String newName);

    @Query("SELECT n FROM New n ORDER BY n.createdAt DESC")
    List<New> findAllByOrderByCreatedAtDesc();

}
