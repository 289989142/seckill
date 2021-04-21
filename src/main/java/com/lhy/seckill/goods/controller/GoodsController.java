package com.lhy.seckill.goods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhy.seckill.core.base.MyPage;
import com.lhy.seckill.goods.service.GoodsService;
import com.lhy.seckill.goods.entity.SkGoodsEntity;
import com.lhy.seckill.goods.service.param.GoodsSearchParam;
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


    @GetMapping("/goodsManager")
    public String toGoodsManager() {
        return "admin/goods";
    }
    @GetMapping("/seckillManager")
    public String toSeckillManger() {
        return "seckillGoods";
    }
    @GetMapping("/userManager")
    public String upload() {
        return "admin/users";
    }

    @GetMapping({"/goodsList", "/"})
    public String toGoodsList(Model model,
                              @RequestParam(required = false,defaultValue = "0",value = "pageNum") int pageNum,
                              @RequestParam(required = false,defaultValue = "9",value = "pageSize") int pageSize) {
        IPage<SkGoodsEntity> goodsPage = goodsService.getGoodsPage(pageNum, pageSize);
        model.addAttribute("pageInfo",goodsPage);
        return "goodsList";
    }

    @GetMapping("/searchCenter")
    public String searchCenter() {
        return "searchCenter";
    }
    @GetMapping("/seckill")
    public String toSeckillList() {
        return "seckillList";
    }
    @GetMapping("/orders")
    public String orders() {
        return "orders";
    }
    @GetMapping("/goods")
    public String toGoods() {
        return "admin/goods";
    }


//    @GetMapping("/search")
//    @ResponseBody
//    public MyPage<SkGoodsEntity> search(GoodsSearchParam param){
//        return goodsService.search(param);
//    }

    @GetMapping("/search")
    public String search(Model model,GoodsSearchParam param){
        MyPage<SkGoodsEntity> result = goodsService.search(param);
        result.judgePageBoundary();
        model.addAttribute("pageInfo",result);
        return "search";
    }

}
