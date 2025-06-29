package com.neu.zboyn.car.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 文件上传方法， 该类可以传 视频， 文件， 音频， 图片
 *
 * @author Zyred
 */
public class FileUploadUtil {
    Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);
    private MultipartFile file;

    /**
     * 文件访问地址
     */
    private String resultPath;
    /**
     * 根据类型读取配置文件构造器
     *
     * @param file 文件
     */
    public FileUploadUtil(MultipartFile file) {
        this.file = file;
    }
    /**
     * 根据保存路径构造器
     *
     * @param file     文件
     * @param savePath 保存路径如： C:\\upload
     */
    public FileUploadUtil(MultipartFile file, String savePath) {
        this.file = file;

    }
    /**
     * 上传文件方法
     *
     * @return
     */
    public String uploadFile() {
        String save_path = null;
        InputStream is = null;
        FileOutputStream os = null;

        // 判断文件不为空
        if (file == null || file.isEmpty()) {
            return "上传的文件为空";
        }

        // 获取项目根路径
        String projectRoot = System.getProperty("user.dir");

        // 设置保存路径，存储在项目的 'uploads' 文件夹中
        save_path = projectRoot + File.separator + "uploads" + File.separator;

        // 获取文件的原始名称
        String oldFileName = file.getOriginalFilename();
        logger.info("上传文件名称：" + oldFileName);
        String oldFileNameSufix = oldFileName.substring(oldFileName.lastIndexOf("."), oldFileName.length());

        // 得到文件的新名称
        String newFileName = UUID.randomUUID().toString() + oldFileNameSufix;
        logger.info("上传文件新名称：" + newFileName);

        // 保存后的文件路径
        String affterPath = save_path + newFileName;
        logger.info("文件保存位置：" + affterPath);

        // 创建文件对象
        File dest = new File(affterPath);

        // 判断文件父目录是否存在，如果不存在则创建
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        // 保存文件
        try {
            is = file.getInputStream();
            os = new FileOutputStream(dest);
            int len = 0;
            byte[] buffer = new byte[2048 * 512];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            String middle = "uploads/";
            // 拼接文件的下载地址
            resultPath =middle + newFileName;
            logger.info("文件下载地址：" + resultPath);
        } catch (IOException e) {
            e.printStackTrace();
            return "服务器异常，请稍后重试";
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "流关闭异常";
            }
        }
        return resultPath;
    }


}