package com.lhy.seckill.user.controller.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Description 查询条件
 * @Author lihengyu
 * @Date 2021/5/6 17:23
 */
@Setter
@Getter
public class UserQueryParam {

    /**null*/
    private Integer id;

    /**用户账号*/
    private String account;

    /**昵称*/
    private String nickname;

    /**角色*/
    private String role;

    /**创建时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //arg in
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") //arg out
    private LocalDateTime createTime;

    /**最后更新时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //arg in
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") //arg out
    private LocalDateTime lastTime;

    private Long pageSize = 10L;

    /**MPP默认从1开始发分页*/
    private Long pageNum = 1L;
}
