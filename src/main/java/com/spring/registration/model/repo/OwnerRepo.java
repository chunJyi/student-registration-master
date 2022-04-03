package com.spring.registration.model.repo;

import com.spring.registration.model.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepo extends JpaRepository<Owner,Long> {
}
