package com.spring.registration.model.repo;

import com.spring.registration.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AccountRepo extends JpaRepository<Account,Long> {


    Account findByEmail(String email);
    Account findByEmailAndUsernameAndBirthday(String email, String name, LocalDate birthday);
}
