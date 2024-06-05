package com.tt.service;

import com.tt.dto.AccountDto;
import com.tt.entity.Account;
import com.tt.repository.AccountRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    // c1: @Autowired
    private AccountRepository accountRepo;

    // c2:
    public AccountServiceImpl(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = accountRepo.findAll();
        return accounts;
    }

    @Override
    public Account findById(Integer id) {
        Account account = accountRepo.findById(id).orElse(null);
        return account;
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public boolean saveAccount(Account account) {
        accountRepo.save(account);
        return true;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepo.getDataByUsername(username);
        if(account == null) {
            throw new UsernameNotFoundException("Account not found");
        }

        // Lấy HttpSession hiện tại
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true); // true để tạo session nếu chưa có
        session.setAttribute("account", account);

        List<GrantedAuthority> listRole = new ArrayList<>();
        listRole.add(new SimpleGrantedAuthority(account.getRole()));
        return new User(username, account.getPassword(), listRole);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
}
