package com.lhy.seckill.goods.entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @ClassName ESGoodsEntity
 * @Description es商品实体类
 * @Author lihengyu
 * @Date 2021/4/8 15:41
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "sk_goods" )
public class ElasticSearchGoodsEntity {

    /**主键*/
    @Id
    private Integer id;

    /**货物名称*/
    @Field(type = FieldType.Text,analyzer = "ik_smart")
    private String name;

    /**货物价格*/
    @Field(type = FieldType.Double)
    private Double price;

    /**货物库存*/
    @Field(type = FieldType.Integer)
    private Integer stock;

    /**货物简介*/
    @Field(type = FieldType.Text,analyzer = "ik_smart")
    private String description;

    /**货物类型*/
    @Field(type = FieldType.Keyword)
    private String type;

    /**创建时间*/
    @Field(type = FieldType.Date,format = DateFormat.basic_date)
    private LocalDate createTime;

    /**最后更新时间*/
    @Field(type = FieldType.Date,format = DateFormat.basic_date)
    private LocalDate lastTime;

}
