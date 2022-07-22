package com.tianhua.datafactory.controller;

import com.coderman.utils.response.ResultDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * date: 2022/4/11
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Controller
public class FileUploadController {

    @PostMapping(value = "/apidoc/upload")
    public @ResponseBody
    ResultDataDto upload(MultipartFile file){
        // 1. 获取要上传文件的文件名
        // 1. 获取要上传文件的文件名
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        // 2. 自定义上传路径
        String path = "/Users/dasouche/scworkspace/myspace/amis4j/doc";
        // 3. 判断路径是否存在，不存在则新建
        File apiDocFile = new File(path);
        if (!apiDocFile.exists()){
            apiDocFile.mkdir();
        }

        // 3.改名,避免重名,定义新文件的名字
        String newName = path + File.separator + System.currentTimeMillis() +
                fileName.substring(fileName.lastIndexOf("."));

        try {
            file.transferTo(new File(newName));
            Map<String,String> map = new HashMap<>();
            map.put("value",newName);
            return ResultDataDto.success(map);
        } catch (IOException e) {
            return ResultDataDto.fail("500","失败");
        }
    }


}
