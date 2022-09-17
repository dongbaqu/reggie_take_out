package com.cwj.reggie.controller;

import com.cwj.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author cwj
 * @version 1.0.0
 * @date 2022/9/16 21:06
 * @description:
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){//file变量名不能随便取，应于name的值相同：form-data; name="file"; filename="0a3b3288-3446-4420-bbff-f263d0c02d8e.jpg"
        //file是一个临时文件，需要转存到指定位置，则本次请求结束文件会被删除
        log.info("file ==== >" + file);

        //原始文件名
        String originalFilename = file.getOriginalFilename();

        String[] split = originalFilename.split("\\.");
        String suffix = split[split.length - 1];
        log.info("suffix ====== > " + suffix);
        //使用UUID重载生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + "." + suffix;

        //创建一个目录对象
        File dir = new File(basePath);
        if (!dir.exists()){
            //目录不存在，需要创建
            dir.mkdirs();
        }

        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success(fileName);
    }

    /**
     * 文件下载
     * @param name
     * @param response
     * @return
     */
    @GetMapping("/download")
    public void downLoad(String name, HttpServletResponse response){
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            //输入流，通过输入流读取文件内容
            fileInputStream = new FileInputStream(basePath + name);
            //输入流，通过输入输出文件回溯浏览器，在浏览器显示图片
            outputStream = response.getOutputStream();

            response.setContentType("image/jpg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}