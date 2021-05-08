package com.lhy.seckill.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lhy.seckill.security.entity.UserDetailImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author lihengyu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("elastic_seckill.sk_user")
public class SkUserEntity {

  /**null*/
  @TableId(type = IdType.AUTO)
  private Integer id;

  /**用户账号*/
  private String account;

  /**昵称*/
  private String nickname;

  /**密码*/
  private String password;

  /**头像*/
  private String avatar;

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

  public SkUserEntity(UserDetailImpl userDetail){
    id = userDetail.getId();
    account = userDetail.getUsername();
    nickname = userDetail.getNickName();
    password = userDetail.getPassword();
    avatar = userDetail.getAvatar();
    role = String.valueOf(userDetail.getAuthorities().toArray()[0]);
  }

}
