package com.maybank;

import com.maybank.processor.DataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AvqDataProcessorApplication {

	

	public static void main(String[] args) {
		ApplicationContext context =  SpringApplication.run(AvqDataProcessorApplication.class, args);
DataProcessor data=context.getBean(DataProcessor.class);
	data.processFiles("PW");

//data.processDetails();


		
	}

}
