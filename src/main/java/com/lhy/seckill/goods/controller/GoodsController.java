package com.lhy.seckill.goods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhy.seckill.goods.entity.SkGoodsEntity;
import com.lhy.seckill.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

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

    private static final String REDIRECT_TO_ADMIN_GOODS = "redirect:/admin/goods/list";
    private static final String SESSION_USERNAME = "username";
    //TODO 修改 只有admin才能进管理后台
    /**
     * @Description // 普通商品页面跳转展示
     * @Param [model, pageNum, pageSize]
     * @return java.lang.String
     **/
    @GetMapping("/admin/goods/list")
    public String adminGoods(Model model,
                             @RequestParam(required = false,defaultValue = "0",value = "pageNum") int pageNum,
                             @RequestParam(required = false,defaultValue = "10",value = "pageSize") int pageSize) {
        IPage<SkGoodsEntity> goodsPage = goodsService.getGoodsPage(pageNum, pageSize);
        model.addAttribute("pageInfo",goodsPage);
        return "admin/goods";
    }

    /** @Description 后台like查询商品名
    * @Param [model, goodsName, pageNum, pageSize]
    * @return java.lang.String
    **/
    @PostMapping("/admin/goods/list")
    public String adminSelectGoods(Model model,String goodsName,
                             @RequestParam(required = false,defaultValue = "0",value = "pageNum") int pageNum,
                             @RequestParam(required = false,defaultValue = "10",value = "pageSize") int pageSize) {

        IPage<SkGoodsEntity> goodsPage = goodsService.getGoodsPage(pageNum, pageSize,goodsName);
        model.addAttribute("pageInfo",goodsPage);
        return "admin/goods :: goodsList";
    }

    /**
     * @Description //去新增商品页面
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping("/admin/toGoodsAdd")
    public String toGoodsAdd() {
        return "admin/goodsAdd";
    }

    /**
     * @Description //普通商品新增请求
     * @Param
     * @return
     **/
    @PostMapping("/addGoods")
    public String addGoods(SkGoodsEntity entity){
        goodsService.addGoods(entity);
        return REDIRECT_TO_ADMIN_GOODS;
    }

    /**
     * @Description //跳转编辑修改商品携带数据
     * @Param
     * @return
     **/
    @GetMapping("admin/goods/{id}/toInput")
    public String editInput(@PathVariable Integer id, Model model) {
        SkGoodsEntity goods = goodsService.getGoods(id);
        model.addAttribute("goods", goods);
        model.addAttribute("imgDirectory",goods.getPicture());
        model.addAttribute("status",1);
        return "admin/goodsInput";
    }

    /**
     * @Description //更新普通商品
     * @Param [id, model]
     * @return java.lang.String
     **/
    @PutMapping("/admin/goods/update")
    public String updateGoods(SkGoodsEntity entity) {
        goodsService.updateGoods(entity);
        return REDIRECT_TO_ADMIN_GOODS;
    }

    /**
     * @Description //删除普通商品
     * @Param [id, attributes]
     * @return java.lang.String
     **/
    @DeleteMapping("/admin/goods/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes attributes) {
        goodsService.deleteGoods(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_TO_ADMIN_GOODS;
    }

    /** @Description 普通商品列表
    * @Param [model, pageNum, pageSize]
    * @return java.lang.String
    **/
    @GetMapping({"/goods/list", "/"})
    public String toGoodsList(Model model, HttpSession session,
                              @RequestParam(required = false, defaultValue = "0", value = "pageNum") int pageNum,
                              @RequestParam(required = false,defaultValue = "12",value = "pageSize") int pageSize) {
        IPage<SkGoodsEntity> goodsPage = goodsService.getGoodsPage(pageNum, pageSize);
        model.addAttribute("pageInfo",goodsPage);
        setSession(session);
        return "goodsList";
    }

    /** @Description 进入首页时setSession
    * @Param [session]
    * @return void
    **/
    private void setSession(HttpSession session){
        if(null==session.getAttribute(SESSION_USERNAME) || "anonymousUser".equals(session.getAttribute(SESSION_USERNAME))){
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            //springSecurity默认的值
            if ("anonymousUser".equals(name)){
                session.setAttribute(SESSION_USERNAME,null);
            }else {
                session.setAttribute(SESSION_USERNAME,name);
            }

        }
    }

    /** @Description 根据id获取商品详情
    * @Param [id, model]
    * @return java.lang.String
    **/
    @GetMapping("goods/{id}")
    public String goodsDetail(@PathVariable("id") Integer id , Model model){
        SkGoodsEntity goods = goodsService.getGoods(id);
        model.addAttribute("goods",goods);
        return "goodsDetail";
    }
}
