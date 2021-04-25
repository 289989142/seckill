package com.lhy.seckill.search.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhy.seckill.core.base.MyPage;
import com.lhy.seckill.goods.entity.SkGoodsEntity;
import com.lhy.seckill.goods.service.GoodsService;
import com.lhy.seckill.goods.service.param.GoodsSearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description es搜索接口
 * @Author lihengyu
 * @Date 2021/4/25 18:17
 */
@Controller
public class SearchController {

    @Autowired
    GoodsService goodsService;

    /** @Description 携带数据跳转到搜索页面
    * @Param [model, pageNum, pageSize]
    * @return java.lang.String
    **/
    @GetMapping("/toSearchCenter")
    public String searchCenter(Model model,
                               @RequestParam(required = false,defaultValue = "0",value = "pageNum") int pageNum,
                               @RequestParam(required = false,defaultValue = "12",value = "pageSize") int pageSize) {
        IPage<SkGoodsEntity> goodsPage = goodsService.getGoodsPage(pageNum, pageSize);
        model.addAttribute("pageInfo",goodsPage);
        return "search";
    }

    /** @Description 执行搜索
    * @Param [model, param]
    * @return java.lang.String
    **/
    @GetMapping("/search")
    public String search(Model model, GoodsSearchParam param){
        MyPage<SkGoodsEntity> result = goodsService.search(param);
        result.judgePageBoundary();
        model.addAttribute("pageInfo",result);
        return "search :: goodsList";
    }
}
