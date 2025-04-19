package com.camel;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.camel.bean.SampleBean;

public class MultiCastParalelProcessing {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MultiCastParalelProcessing.class);
	
	public static void start() {
		try (CamelContext context=new DefaultCamelContext();){
			context.addRoutes(new RouteBuilder() {
				ExecutorService executor = Executors.newFixedThreadPool(10);
				@Override
				public void configure() {
				final String ROUTEEXPN= "body: ${body}, thread : ${threadName}";
				final String TOCOMPONENT= "stream:out";
				
				from("direct:start")
				.multicast().parallelProcessing()
				.executorService(executor)
			    .to("direct:first","direct:second","direct:third");
				
			    from("direct:first").bean(SampleBean.class,"addFirst")
			    .setBody(simple(ROUTEEXPN)).to(TOCOMPONENT);
			    
			    from("direct:second").bean(SampleBean.class,"addSecond")
			    .setBody(simple(ROUTEEXPN)).to(TOCOMPONENT);
			    
			    from("direct:third").bean(SampleBean.class,"addThird")
			    .setBody(simple(ROUTEEXPN)).to(TOCOMPONENT);
			    
				}
			});
			
			ProducerTemplate template = context.createProducerTemplate();
			context.start();
			template.sendBody("direct:start","Multicast");
			Thread.sleep(5*60*1000);
			context.stop();
			
		}catch(Exception e) {
				e.printStackTrace();
		}
		
		
		
		
	}

}
