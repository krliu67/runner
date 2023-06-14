package com.example.model;

import com.example.utils.SensitiveConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "user")
@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    private Long id;

    @Column(nullable = false,updatable = false)
    private String userId;

    private String username;

    private String password;

    private String avatarUrl;

    private Integer score;

    private String inviteCode;

    private String signature;

    private String phone;
}
