package com.lrm.web.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller     //ai4-16
public class FileUploadController {

    // 上传目录：项目运行目录下的 static/uploads/ （Spring Boot 会自动映射 /uploads/**）
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/static/uploads/";

    @PostMapping("/upload/image")
    @ResponseBody
    public Map<String, String> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, String> result = new HashMap<>();
        if (file.isEmpty()) {
            result.put("error", "文件为空");
            return result;
        }
        // 1. 校验文件类型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            result.put("error", "只允许上传图片文件");
            return result;
        }
        // 2. 校验文件大小（限制 10MB）
        if (file.getSize() > 10 * 1024 * 1024) {
            result.put("error", "图片不能超过 10MB");
            return result;
        }
        // 3. 生成文件名（日期 + UUID + 原扩展名）
        String originalFilename = file.getOriginalFilename();
        String ext = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + UUID.randomUUID().toString().substring(0, 8) + ext;
        // 4. 按日期分文件夹（例如 2026/04/）
        String datePath = new SimpleDateFormat("yyyy/MM/").format(new Date());
        File destDir = new File(UPLOAD_DIR + datePath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File destFile = new File(destDir, newFileName);
        try {
            file.transferTo(destFile);
            // 5. 返回访问 URL（注意：URL 中的反斜杠要转成正斜杠）
            String url = "/uploads/" + datePath + newFileName;
            url = url.replace("\\", "/");
            result.put("url", url);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("error", "上传失败");
        }
        return result;
    }
}