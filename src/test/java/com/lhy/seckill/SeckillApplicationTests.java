package com.lhy.seckill;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class SeckillApplicationTests {

    @Test
    public void contextLoads(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
        boolean matches = bCryptPasswordEncoder.matches("123456", encode);
        System.out.println(matches);
        boolean matches1 = bCryptPasswordEncoder.matches("1234567", encode);
        System.out.println(matches1);
    }

}