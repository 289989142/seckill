package com.lhy.seckill.core.controller.upload;

import com.lhy.seckill.goods.service.GoodsService;
import com.lhy.seckill.goods.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Description 上传
 * @Author lihengyu
 * @Date 2021/4/25 18:06
 */
@Controller
public class UploadController {


    /**上传地址*/
    @Value("${file.upload.path}")
    private String filePath;

    /**
     * @Description //上传图片及回显
     * @Param [map, fileUpload]
     * @return java.lang.String
     **/
    @RequestMapping("uploadPicture")
    public String upload(@RequestParam("img") MultipartFile file, Model model) {
        // 获取上传文件名
        String filename = file.getOriginalFilename();
        // 定义上传文件保存路径
        String path = filePath;
        // 新建文件
        File filepath = new File(path, filename);
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        try {
            // 写入文件
            file.transferTo(new File(path + File.separator + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将src路径发送至html页面
        model.addAttribute("imgDirectory", "/images/"+filename);
        model.addAttribute("status",1);
        return "admin/goodsAdd";
    }

    @RequestMapping("uploadPictureSK")
    public String uploadSK(@RequestParam("img") MultipartFile file, Model model) {
        // 获取上传文件名
        String filename = file.getOriginalFilename();
        // 定义上传文件保存路径
        String path = filePath;
        // 新建文件
        File filepath = new File(path, filename);
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        try {
            // 写入文件
            file.transferTo(new File(path + File.separator + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将src路径发送至html页面
        model.addAttribute("imgDirectory", "/images/"+filename);
        model.addAttribute("status",1);
        return "admin/seckillGoodsAdd";
    }
}
