package com.lhy.seckill.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("elastic_seckill.sk_goods")
public class SkGoodsEntity {

  /**主键*/
  @TableId(type = IdType.AUTO)
  private Integer id;

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

  /**创建时间*/
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //arg in
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") //arg out
  private LocalDateTime createTime;

  /**最后更新时间*/
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //arg in
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") //arg out
  private LocalDateTime lastTime;

  public SkGoodsEntity(ElasticSearchGoodsEntity entity,String pic){
    id = entity.getId();
    name = entity.getName();
    price = entity.getPrice();
    stock = entity.getStock();
    description = entity.getDescription();
    type = entity.getType();
    picture = pic;
  }

}
