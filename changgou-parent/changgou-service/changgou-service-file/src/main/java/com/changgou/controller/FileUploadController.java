package com.changgou.controller;

import com.changgou.file.FastDFSFile;
import com.changgou.util.FastDFSClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: 郭师兄
 * @date: 2019/7/27 20:37
 */
@RestController
@CrossOrigin
public class FileUploadController {
    /**
     * 文件上传
     * http://localhost:18082/upload
     * @return 文件的访问url
     */
    @PostMapping("/upload")
    public String upload(@RequestParam(value = "file")MultipartFile file) throws IOException {
        //封装一个文件上传对象    FastDFSFile                 文件名字      文件字节数组          获取扩展名
        FastDFSFile fastDFSFile = new FastDFSFile(file.getOriginalFilename(),
                                                    file.getBytes(),
                                                     StringUtils.getFilenameExtension(file.getOriginalFilename()));
        //调用工具类,将文件传到FastDFS中
        String[] uploadResult = FastDFSClient.upload(fastDFSFile);
        //拼接访问地址:
        String url = "http://192.168.228.132/"+uploadResult[0]+"/"+uploadResult[1];
        return url;
    }
}
