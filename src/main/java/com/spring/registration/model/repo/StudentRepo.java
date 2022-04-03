package com.spring.registration.model.repo;

import com.spring.registration.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;




public interface StudentRepo extends JpaRepository<Student,Long> {

 Student findByAccountId(long id);

}
