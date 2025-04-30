package com.wzh.wzhpictruebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.wzh.wzhpictruebackend.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class WzhPictrueBackendApplication  {

    public static void main(String[] args) {
        SpringApplication.run(WzhPictrueBackendApplication.class, args);
    }

}
