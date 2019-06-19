package com.cqns.demo;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@SpringBootApplication(exclude={SecurityAutoConfiguration.class,org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@MapperScan(basePackages = {"com.cqns.demo.dao.mapper"})
@EnableJpaRepositories(basePackages = {"com.cqns.demo.dao.repository"} )
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
