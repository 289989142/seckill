package com.lhy.seckill.goods.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName GoodsManager
 * @Description 商品管理
 * @Author lihengyu
 * @Date 2021/1/28 11:23
 * @Version 1.0
 */
@Controller
public class GoodsManager {

    /**上传地址*/
    @Value("${file.upload.path}")
    private String filePath;


    @RequestMapping("/goodsAdd")
    public String toGoodsAdd() {
        return "admin/goodsAdd";
    }
    @RequestMapping("/goodsManager")
    public String toGoodsManager() {
        return "admin/goods";
    }
    @RequestMapping("/seckillManager")
    public String toSeckillManger() {
        return "admin/seckillgoods";
    }
    @RequestMapping("/userManager")
    public String upload() {
        return "admin/users";
    }

    @RequestMapping("/")
    public String toGoodsList1() {
        return "goodsList";
    }
    @RequestMapping("/goodsList")
    public String toGoodsList() {
        return "goodsList";
    }
    @RequestMapping("/searchCenter")
    public String searchCenter() {
        return "searchCenter";
    }
    @RequestMapping("/seckill")
    public String toSeckillList() {
        return "seckillList";
    }
    @RequestMapping("/orders")
    public String orders() {
        return "orders";
    }
    @RequestMapping("/goods")
    public String toGoods() {
        return "admin/goods";
    }

    /**
    * @Description //TODO 上传图片及回显
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
}
