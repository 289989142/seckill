package com.lhy.seckill.goods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhy.seckill.goods.entity.SkGoodsSeckillEntity;
import com.lhy.seckill.goods.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @Description 秒杀商品
 * @Author lihengyu
 * @Date 2021/4/25 18:04
 */
@Controller
public class SeckillGoodsController {
    @Autowired
    SeckillGoodsService seckillGoodsService;

    /** @Description 带数据的页面跳转
    * @Param [model, pageNum, pageSize]
    * @return java.lang.String
    **/
    @GetMapping("/admin/seckillGoods")
    public String adminSeckillGoods(Model model,
                                    @RequestParam(required = false,defaultValue = "0",value = "pageNum") int pageNum,
                                    @RequestParam(required = false,defaultValue = "10",value = "pageSize") int pageSize) {
        IPage<SkGoodsSeckillEntity> goodsPage = seckillGoodsService.getGoodsPage(pageNum, pageSize);
        model.addAttribute("pageInfo",goodsPage);
        return "admin/seckillGoods";
    }

    /** @Description 秒杀商品新增页面
    * @Param []
    * @return java.lang.String
    **/
    @RequestMapping("/admin/toSeckillGoodsAdd")
    public String toSeckillGoodsAdd() {
        return "admin/seckillGoodsAdd";
    }

    /** @Description 秒杀商品新增请求
    * @Param [entity]
    * @return java.lang.String
    **/
    @PostMapping("/admin/addSeckillGoods")
    public String addSeckillGoods(SkGoodsSeckillEntity entity){
        seckillGoodsService.addGoods(entity);
        return "redirect:/admin/seckillGoods";
    }

    /** @Description 秒杀商品更新页面 携带数据
    * @Param [id, model]
    * @return java.lang.String
    **/
    @GetMapping("/admin/seckillGoods/{id}/toInput")
    public String seckillEditInput(@PathVariable Long id, Model model) {
        SkGoodsSeckillEntity goods = seckillGoodsService.getGoods(id);
        model.addAttribute("goods", goods);
        model.addAttribute("imgDirectory",goods.getPicture());
        model.addAttribute("status",1);
        return "admin/seckillGoodsInput";
    }

    /** @Description 更新普通商品
    * @Param [entity]
    * @return java.lang.String
    **/
    @PostMapping("/admin/seckillGoods/update")
    public String updateSKGoods(SkGoodsSeckillEntity entity) {
        seckillGoodsService.updateGoods(entity);
        return "redirect:/admin/seckillGoods";
    }

    /** @Description 删除普通商品
    * @Param [id, attributes]
    * @return java.lang.String
    **/
    @GetMapping("/admin/seckillGoods/{id}/delete")
    public String seckillGoodsDelete(@PathVariable Long id, RedirectAttributes attributes) {
        seckillGoodsService.deleteGoods(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/seckillGoods";
    }

}
