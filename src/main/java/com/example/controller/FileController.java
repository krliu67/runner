package com.example.controller;

import com.example.config.MinioConfig;
import com.example.utils.MinioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private MinioConfig minioConfig;

    @Autowired
    private MinioUtils minioUtils;

    /**
     * 上传文件
     * @param file
     * @param bucketName 桶名称
     * @return 返回对象名称和外链地址
     */
    @PostMapping(value = "/")
    public ResponseEntity<HashMap<String, String>> uploadFile(MultipartFile file, @RequestParam(required = false) String bucketName) {
        bucketName = StringUtils.hasLength(bucketName) ? bucketName : minioConfig.getDefaultBucketName();
        String objectName = minioUtils.getDatePath() + file.getOriginalFilename();
        minioUtils.upload(bucketName, objectName, file);
        String viewPath = minioUtils.getPresignedObjectUrl(bucketName, objectName, 60, TimeUnit.SECONDS);
        HashMap<String, String> objectInfo = new HashMap<>();
        objectInfo.put("objectName", objectName);
        //只能预览图片、txt等部分文件
        objectInfo.put("viewPath", viewPath);
        return ResponseEntity.ok(objectInfo);
    }


    /**
     * 删除指定桶里的某个对象
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @return
     */
    @DeleteMapping(value = "/")
    public ResponseEntity<String> deleteByPath(@RequestParam(required = false) String bucketName, String objectName) {
        bucketName = StringUtils.hasLength(bucketName) ? bucketName : minioConfig.getDefaultBucketName();
        minioUtils.delete(bucketName, objectName);
        return ResponseEntity.ok("删除成功");
    }

    /**
     * 下载文件
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @param response  相应结果
     */
    @GetMapping("/")
    public void downLoad(@RequestParam(required = false) String bucketName, String objectName, HttpServletResponse response) {
        // 获取文件
        minioUtils.downResponse(bucketName,objectName,response);
    }
}

