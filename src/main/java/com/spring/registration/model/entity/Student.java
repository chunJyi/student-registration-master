package com.spring.registration.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data
public class Student implements Serializable {

    private static final Long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;
    private String name;
    private String PhNo;
    @Embedded
    private Address address;
    @OneToOne
    private Account account;
    @Enumerated(EnumType.STRING)
    private Gender gender;

}
