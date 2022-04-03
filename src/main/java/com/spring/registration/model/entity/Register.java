package com.spring.registration.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data
public class Register implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Course course;
    private Boolean enable;
    private Boolean look;

    public  Register(){
        enable=false;
        look = false;
    }

    public boolean isEnable() {
        return enable;
    }
}
