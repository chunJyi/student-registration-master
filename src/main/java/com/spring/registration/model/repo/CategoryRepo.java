package com.spring.registration.model.repo;

import com.spring.registration.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {

}
