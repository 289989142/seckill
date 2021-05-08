package com.lhy.seckill.order.controller.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 查询条件
 * @Author lihengyu
 * @Date 2021/5/6 17:23
 */
@Setter
@Getter
public class OrderQueryParam {

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

    /**成交价格*/
    private Double price;

    /**是否为秒杀商品*/
    private Integer isSeckill;


    private Long pageSize = 10L;

    /**MPP默认从1开始发分页*/
    private Long pageNum = 1L;

}
