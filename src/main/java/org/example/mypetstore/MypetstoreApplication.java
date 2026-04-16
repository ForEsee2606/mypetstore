package org.example.mypetstore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.mypetstore.mapper")
public class MypetstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MypetstoreApplication.class, args);
    }

}
