package com.ttm.crm.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// https://stackoverflow.com/questions/51221777/failed-to-configure-a-datasource-url-attribute-is-not-specified-and-no-embedd
@SpringBootApplication //(exclude = {DataSourceAutoConfiguration.class })
@EnableTransactionManagement
public class CrmApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CrmApplication.class, args);
	}
	
}
