package com.tt.repository;

import com.tt.entity.Account;
import com.tt.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Account findByUsername(String username);

    @Query(value = "FROM Account WHERE username = :username")
    Account getDataByUsername(String username);


    @Query(value = "FROM Account WHERE username LIKE CONCAT('%', :username , '%')")
    List<Account> getData(String username);


}
