package com.tt.service;

import com.tt.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface AccountService extends UserDetailsService {

    // abstraact method, public access modifier
    List<Account> getAll();

    Account findById(Integer id);

    Optional<Account> findByUsername(String username);

    boolean saveAccount(Account account);

}
