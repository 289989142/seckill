package com.lhy.seckill.goods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhy.seckill.goods.service.GoodsService;
import com.lhy.seckill.goods.entity.SkGoodsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName GoodsManager
 * @Description 商品管理
 * @Author lihengyu
 * @Date 2021/1/28 11:23
 * @Version 1.0
 */
@Controller
public class GoodsController {

    @Autowired
    GoodsService goodsService;


    @RequestMapping("/goodsManager")
    public String toGoodsManager() {
        return "admin/goods";
    }
    @RequestMapping("/seckillManager")
    public String toSeckillManger() {
        return "seckillGoods";
    }
    @RequestMapping("/userManager")
    public String upload() {
        return "admin/users";
    }

    @RequestMapping({"/goodsList", "/"})
    public String toGoodsList(Model model,
                              @RequestParam(required = false,defaultValue = "0",value = "pageNum") int pageNum,
                              @RequestParam(required = false,defaultValue = "5",value = "pageSize") int pageSize) {
        IPage<SkGoodsEntity> goodsPage = goodsService.getGoodsPage(pageNum, pageSize);
        model.addAttribute("pageInfo",goodsPage);
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




}
