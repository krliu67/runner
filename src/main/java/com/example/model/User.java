package com.example.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    @ApiModelProperty("主鍵id")
    private Long id;

    @Column(nullable = false,updatable = false)
    @ApiModelProperty("用戶id")
    private String userId;

    @ApiModelProperty("用戶名")
    private String username;

    @ApiModelProperty("登錄密碼")
    private String password;

    @ApiModelProperty("头像地址")
    private String avatarUrl;

    @ApiModelProperty("跑步得分")
    private Integer score;

    @ApiModelProperty("用戶邀請碼")
    private String inviteCode;

    @ApiModelProperty("用戶個性簽名")
    private String signature;

    @ApiModelProperty("用戶手機號")
    private String phone;
}
