package com.maybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//@EnableAsync
public class AvqDataProcessorApplication {

	

	public static void main(String[] args) {
		ApplicationContext context =  SpringApplication.run(AvqDataProcessorApplication.class, args);
//		DataProcessor data=context.getBean(DataProcessor.class);
//		data.processFilesData("PW");
	}
}
