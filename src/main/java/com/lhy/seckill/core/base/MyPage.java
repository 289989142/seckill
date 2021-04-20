package com.lhy.seckill.core.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 分页类
 * @Author lihengyu
 * @Date 2021/4/17 17:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPage<T> {
    int current = 1;
    int size = 10;
    int total = 0;
    List<T> data;
}
