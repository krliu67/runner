package com.example.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@Table(name = "code_list")
@Entity
public class CodeList {
    @Id
    @Column(name = "status")
    private Integer status;

    @Column(name = "info")
    private String info;
}
