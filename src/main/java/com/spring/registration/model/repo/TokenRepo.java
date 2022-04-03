package com.spring.registration.model.repo;

import com.spring.registration.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<Token,Long> {
    Token findByToken(String token);

}
