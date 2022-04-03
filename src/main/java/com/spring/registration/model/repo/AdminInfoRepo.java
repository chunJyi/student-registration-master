package com.spring.registration.model.repo;

import com.spring.registration.model.entity.AdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminInfoRepo extends JpaRepository<AdminInfo,Integer> {

    AdminInfo findByAccountId(Long id);
}
