package com.lhy.seckill.goods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhy.seckill.goods.service.GoodsService;
import com.lhy.seckill.security.bean.SkGoodsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName AdminGoodsController
 * @Description 后台商品管理
 * @Author lihengyu
 * @Date 2021/2/1 15:42
 * @Version 1.0
 */
@Controller
public class AdminGoodsController {

    /**上传地址*/
    @Value("${file.upload.path}")
    private String filePath;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/admin/goods")
    public String adminGoods(Model model,
                              @RequestParam(required = false,defaultValue = "0",value = "pageNum") int pageNum,
                              @RequestParam(required = false,defaultValue = "5",value = "pageSize") int pageSize) {
        IPage<SkGoodsEntity> goodsPage = goodsService.getGoodsPage(pageNum, pageSize);
        model.addAttribute("pageInfo",goodsPage);
        return "/admin/goods";
    }

    /**
     * @Description //TODO 添加商品
     * @Param
     * @return
     **/
    @PostMapping("/addGoods")
    public String addGoods(SkGoodsEntity entity){
        goodsService.addGoods(entity);
        return "redirect:/admin/goods";
    }

    /**
     * @Description //TODO 跳转编辑修改文章
     * @Param
     * @return
     **/
    @GetMapping("admin/goods/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        SkGoodsEntity goods = goodsService.getGoods(id);
        model.addAttribute("goods", goods);
        model.addAttribute("imgDirectory",goods.getPicture());
        model.addAttribute("status",1);
        return "admin/goodsInput";
    }
    /**
    * @Description //TODO 更新商品
    * @Param [id, model]
    * @return java.lang.String
    **/
    @PostMapping("/admin/goods/update")
    public String updateGoods(SkGoodsEntity entity) {
        goodsService.updateGoods(entity);
        return "redirect:/admin/goods";
    }

    /**
     * @Description //TODO 删除物品
     * @Param [id, attributes]
     * @return java.lang.String
     **/
    @GetMapping("/admin/goods/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        goodsService.deleteGoods(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/goods";
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
