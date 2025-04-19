package com.camel;

import java.io.InputStream;
import java.util.Properties;

import org.apache.camel.builder.RouteBuilder;

public class MyRouter  extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		Thread.currentThread().getContextClassLoader();
		String source =null;
		String destination=null;
		
		Properties props = new Properties();
		try(InputStream resource = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("config.properties")){
			props.load(resource);
			source= props.getProperty("camel.input");
			destination= props.getProperty("camel.output");
			
			from("file:"+source+"?fileName=keystore.p12&noop=true")
			.to("file:"+destination);
			System.out.println("MyRouter.....");
		}catch(Exception e) {
			e.printStackTrace();
		}
		

	}

}
