package com.lhy.seckill.search.controller.param;

import lombok.Data;

/**
 * @Description 普通商品搜索参数
 * @Author lihengyu
 * @Date 2021/4/17 18:04
 */
@Data
public class GoodsSearchParam {

    /**货物名称*/
    private String name;

    /**货物价格*/
    private Double price;

    /**货物库存*/
    private Integer stock;

    /**货物简介*/
    private String description;

    /**货物图片*/
    private String picture;

    /**货物类型*/
    private String type;

    private int current=1;

    private int size=10;



}
