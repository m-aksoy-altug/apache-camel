package com.camel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		
//		String originalFileName = (String) exchange.getIn()
//				.getHeader(Exchange.FILE_NAME, String.class);
//		
//		exchange.getIn().getBody();
//		
//		System.out.println("originalFileName:::   "+ originalFileName);
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String changedFileName = dateFormat.format(date) + originalFileName;
//        exchange.getIn().setHeader(Exchange.FILE_NAME, changedFileName);
//        System.out.println("changedFileName:::   "+ changedFileName);
//		exchange.getPattern();
		Map<String,Object> mapHeaders = new HashMap<>();
		
		mapHeaders= exchange.getIn().getHeaders();		
		
		String body = exchange.getIn().getBody(String.class);
		
		System.out.println("Response boyd:::   "+ body);
		System.out.println("Response headers:::   "+ mapHeaders);

	}

}
