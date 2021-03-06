package com.cos.security1.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동생성 mysql에 위임
    private int id;
    private String username;
    private String password;
    private String email;
    private String role; // 권한자

    @CreationTimestamp
    private Timestamp createDate;
}
