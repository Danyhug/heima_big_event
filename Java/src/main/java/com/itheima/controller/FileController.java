package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@RestController
public class FileController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        String originName = file.getOriginalFilename();
        // 改为uuid.xxx
        String objectName = UUID.randomUUID() + originName.substring(originName.lastIndexOf("."));
        InputStream in = file.getInputStream();

        return Result.success(AliOssUtil.upload(objectName, in));
    }
}
