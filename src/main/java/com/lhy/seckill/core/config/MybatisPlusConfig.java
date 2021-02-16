package com.lhy.seckill.core.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName MybatisPlusConfig
 * @Description mybatisPlus配置
 * @Author lihengyu
 * @Date 2021/2/1 16:23
 * @Version 1.0
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.lhy.seckill.*.mapper")
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
