package com.lhy.seckill.security.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
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

}
