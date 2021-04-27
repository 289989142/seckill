package com.lhy.seckill.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author lihengyu
 */
@Data
@TableName("sk_order")
public class SkOrderEntity {

 /**null*/
 @TableId(type = IdType.AUTO)
 private Integer id;

 /**用户id*/
 private Integer userId;

 /**货物id*/
 private Integer goodsId;

 /**创建时间*/
 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
 @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
 private Date createTime;

 /**最后更新时间*/
 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
 @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
 private Date lastTime;

 /**订单状态 0:待支付*/
 private Integer status;

 /**商品数量*/
 private Integer goodsCount;

}
