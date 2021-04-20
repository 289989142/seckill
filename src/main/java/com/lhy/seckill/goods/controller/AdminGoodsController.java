package com.lhy.seckill.goods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhy.seckill.goods.entity.SkGoodsSeckillEntity;
import com.lhy.seckill.goods.service.GoodsService;
import com.lhy.seckill.goods.service.SeckillGoodsService;
import com.lhy.seckill.goods.entity.SkGoodsEntity;
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
    @Autowired
    SeckillGoodsService seckillGoodsService;
    /**
    * @Description // 商品页面展示
    * @Param [model, pageNum, pageSize]
    * @return java.lang.String
    **/
    @RequestMapping("/admin/goods")
    public String adminGoods(Model model,
                              @RequestParam(required = false,defaultValue = "0",value = "pageNum") int pageNum,
                              @RequestParam(required = false,defaultValue = "5",value = "pageSize") int pageSize) {
        IPage<SkGoodsEntity> goodsPage = goodsService.getGoodsPage(pageNum, pageSize);
        model.addAttribute("pageInfo",goodsPage);
        return "/admin/goods";
    }
    @RequestMapping("/admin/seckillGoods")
    public String adminSeckillGoods(Model model,
                             @RequestParam(required = false,defaultValue = "0",value = "pageNum") int pageNum,
                             @RequestParam(required = false,defaultValue = "5",value = "pageSize") int pageSize) {
        IPage<SkGoodsSeckillEntity> goodsPage = seckillGoodsService.getGoodsPage(pageNum, pageSize);
        model.addAttribute("pageInfo",goodsPage);
        return "admin/seckillGoods";
    }

    /**
    * @Description //去新增商品页面
    * @Param []
    * @return java.lang.String
    **/
    @RequestMapping("/goodsAdd")
    public String toGoodsAdd() {
        return "admin/goodsAdd";
    }
    @RequestMapping("/seckillGoodsAdd")
    public String toSeckillGoodsAdd() {
        return "admin/seckillGoodsAdd";
    }
    /**
     * @Description //添加商品
     * @Param
     * @return
     **/
    @PostMapping("/addGoods")
    public String addGoods(SkGoodsEntity entity){
        goodsService.addGoods(entity);
        return "redirect:/admin/goods";
    }
    @PostMapping("/addSeckillGoods")
    public String addSeckillGoods(SkGoodsSeckillEntity entity){
        seckillGoodsService.addGoods(entity);
        return "redirect:/admin/seckillGoods";
    }

    /**
     * @Description //跳转编辑修改商品
     * @Param
     * @return
     **/
    @GetMapping("admin/goods/{id}/input")
    public String editInput(@PathVariable Integer id, Model model) {
        SkGoodsEntity goods = goodsService.getGoods(id);
        model.addAttribute("goods", goods);
        model.addAttribute("imgDirectory",goods.getPicture());
        model.addAttribute("status",1);
        return "admin/goodsInput";
    }
    @GetMapping("admin/seckillGoods/{id}/input")
    public String seckillEditInput(@PathVariable Long id, Model model) {
        SkGoodsSeckillEntity goods = seckillGoodsService.getGoods(id);
        model.addAttribute("goods", goods);
        model.addAttribute("imgDirectory",goods.getPicture());
        model.addAttribute("status",1);
        return "admin/seckillGoodsInput";
    }
    /**
    * @Description //更新商品
    * @Param [id, model]
    * @return java.lang.String
    **/
    @PostMapping("/admin/goods/update")
    public String updateGoods(SkGoodsEntity entity) {
        goodsService.updateGoods(entity);
        return "redirect:/admin/goods";
    }
    @PostMapping("/admin/seckillGoods/update")
    public String updateSKGoods(SkGoodsSeckillEntity entity) {
        seckillGoodsService.updateGoods(entity);
        return "redirect:/admin/seckillGoods";
    }

    /**
     * @Description //删除物品
     * @Param [id, attributes]
     * @return java.lang.String
     **/
    @GetMapping("/admin/goods/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes attributes) {
        goodsService.deleteGoods(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/goods";
    }
    @GetMapping("/admin/seckillGoods/{id}/delete")
    public String seckillGoodsDelete(@PathVariable Long id, RedirectAttributes attributes) {
        seckillGoodsService.deleteGoods(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/seckillGoods";
    }

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
