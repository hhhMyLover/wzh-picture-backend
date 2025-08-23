package com.wzh.picture;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.wzh.picture.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class WzhPictureBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WzhPictureBackendApplication.class, args);
    }

}
