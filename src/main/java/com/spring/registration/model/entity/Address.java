package com.spring.registration.model.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
@Data
public class Address implements Serializable {

    private String street;
    private String township;
    private String city;
}
