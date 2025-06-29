package com.neu.zboyn.car.file;


import com.neu.zboyn.car.dto.Response;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 图片， 文件， 视频， 音频上传共用接口
 *
 * @author Zyred
 */

@Controller
@RequestMapping
public class FileUploadUtilController {
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "\\uploads\\";

    /**
     * 文件上传
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/fileUpload/ds", produces = "application/json;charset=UTF-8")
    public Response<String> fileUpload(@RequestParam("file") MultipartFile file) {
        FileUploadUtil upload = new FileUploadUtil(file);
        String result = upload.uploadFile();
        return Response.success(result);
    }


    @GetMapping("/uploads/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        // 创建文件对象
        File file = new File(UPLOAD_DIR + filename);
        System.out.println("进入请求" + filename);
        // 如果文件存在，返回文件内容
        if (file.exists()) {
            Resource resource = new FileSystemResource(file);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } else {
            // 如果文件不存在，返回 404
            return ResponseEntity.notFound().build();
        }
    }

}