package com.American.reggie.controller;

import com.American.reggie.common.R;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String BasePath;

    @PostMapping("/upload")
    public R<String>upload(MultipartFile file)  {

       String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString()+suffix;
        File dir=new File(BasePath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(BasePath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            FileInputStream fis=new FileInputStream(BasePath+name);

            response.setContentType("image/jpeg");
            ServletOutputStream outputStream = response.getOutputStream();
            int len=0;
            byte[]bytes=new byte[1024];
            while ((len=fis.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            outputStream.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
