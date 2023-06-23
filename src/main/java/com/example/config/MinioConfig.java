package com.example.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioConfig {

    @Value("${minio.endpoint}")
    /**
     * minio部署的机器ip地址
     */
    private String endpoint;

    @Value("${minio.port}")
    /**
     * minio使用的端口
     */
    private Integer port;

    @Value("${minio.accessKey}")
    /**
     *唯一标识的账户
     */
    private String accessKey;

    @Value("${minio.secretKey}")
    /**
     * 账户的密码
     */
    private String secretKey;

    @Value("${minio.secure}")
    /**
     * 如果是true，则用的是https而不是http,默认值是true
     */
    private Boolean secure;

    @Value("${minio.defaultBucketName}")
    /**
     * 默认使用的桶名称
     */
    private String defaultBucketName;

    /**
     * 对象交给spring管理
     */
    @Bean
    public MinioClient getMinioClient() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint , port , secure)
                .credentials(accessKey, secretKey)
                .build();
        return minioClient;
    }
}


