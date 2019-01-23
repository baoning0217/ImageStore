package com.vincce.image;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.vincce.image.mapper")
public class ImageStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageStoreApplication.class, args);
	}

}

