package cn.crudapi.crudapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan({"cn.crudapi", "cn.crudapi.crudapi"})
@ServletComponentScan(basePackages = {"cn.crudapi"}) 
@EnableTransactionManagement
@EnableAsync
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class CrudapiApplication {
	public static void main(String[] args) {
		SpringApplication.run(CrudapiApplication.class, args);
	}
}
