package com.spring.registration.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data
public class AdminInfo implements Serializable {
    private static final Long serialVersion=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String school;
    private String phone;
    private String address;
    private String facebook;
    private String youtube;
    private String instagram;
    @OneToOne
    private Account account;

}
