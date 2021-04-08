package com.lhy.seckill.goods.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;


/**
 * @author HASEE
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("elastic_seckill.sk_goods")
@Document(indexName = "sk_goods" )
public class SkGoodsEntity {


  /**主键*/
  @Id
  @TableId(type = IdType.AUTO)
  private Integer id;

  /**货物名称*/
  @Field(type = FieldType.Text,analyzer = "ik_max_word")
  private String name;

  /**货物价格*/
  @Field(type = FieldType.Double)
  private Double price;

  /**货物库存*/
  @Field(type = FieldType.Integer)
  private Integer stock;

  /**货物简介*/
  @Field(type = FieldType.Text)
  private String description;

  /**货物类型*/
  @Field(type = FieldType.Keyword)
  private String type;

  /**商品图片**/
  @Field(store = false)
  private String picture;

  /**创建时间*/
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //arg in
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") //arg out
  @Field(type = FieldType.Date)
  private LocalDateTime createTime;

  /**最后更新时间*/
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //arg in
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") //arg out
  @Field(type = FieldType.Date)
  private LocalDateTime lastTime;

}
