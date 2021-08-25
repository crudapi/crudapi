package cn.crudapi.api.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@ComponentScan({"cn.crudapi", "cn.crudapi.api"})
@EntityScan("cn.crudapi.core.entity")
@EnableTransactionManagement
@SpringBootApplication
public class ApiApplication {
	 public static void main(String[] args) {
		 SpringApplication.run(ApiApplication.class, args);
	 }
}
