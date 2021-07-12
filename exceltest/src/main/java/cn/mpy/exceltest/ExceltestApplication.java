package cn.mpy.exceltest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.mpy.exceltest.mapper")
public class ExceltestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExceltestApplication.class, args);
    }

}
