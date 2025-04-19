package com.camel;


import org.apache.activemq.ActiveMQConnectionFactory;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.engine.DefaultProducerTemplate;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.camel.model.Course;

import jakarta.jms.ConnectionFactory;

public class CamelApp {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CamelApp.class);

	public static void main(String[] args) {
//		MultiCastParalelProcessing.start();
//		filterByPredicate();
//		sendHTTP();
		transferAfile();
//		processOrder();
//		bindJavaObjectToXML();
//		sendDummyData();
//		springIntegration();
	}
	
	private static void sendHTTP() {
		try (CamelContext context=new DefaultCamelContext();){
			context.setTracing(true);
	 		context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() {
					errorHandler(defaultErrorHandler()
							.maximumRedeliveries(2)
							.redeliveryDelay(1000)
							.retryAttemptedLogLevel(LoggingLevel.WARN));
					
//					from("direct:start")
				from("timer://foo?delay=1")
//				from("timer://foo?fixedRate=true&period=1000")
//					from("direct:post")
					
//					.transform(body().regexReplaceAll("name","product"))  // same as .process(exchange) // data manipulation
				.process(exchange -> LOGGER.info("LOGS....\n\n\n"))  
//				.process(exchange -> exchange.getIn().setBody("{\"title\":\"Java 21\",\"body\":\"Virtual Thread\",\"userId\":\"1\"}"))
				  .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				  .setHeader(Exchange.HTTP_METHOD, constant("GET"))
				  .bean(new MyProcessor()) 
//				  .to("http://192.168.1.113:3000")
				  .log("Body: ${body}")
				  .to("http://localhost:8080/dummy")
				  .bean(new MyProcessor()) // same as .process(exchange) // data manipulation
				  .process(exchange -> LOGGER.info("The HTTP response code is: {}", 
						  exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE)));
				}
			});
	 		System.out.println("Starts..");
	 		context.start();
			System.out.println("context.getRoutes()"+context.getRoutes().toString());
			System.out.println("context.getStatus()"+context.getStatus().toString());
	 		Thread.sleep(100_000_000);
	  		context.stop();
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Finish..");
	}
	
	private static void filterByPredicate() {
		try(CamelContext context=new DefaultCamelContext();) {
			context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() {
					from("direct:start")
					.filter(new Predicate() {
						@Override
						public boolean matches(Exchange exchange) {
							final String body= exchange.getIn().getBody(String.class);
							return (body!=null) && body.startsWith("Spring");
						}
						
					})
					.to("stream:out");
				}
			});
		
			context.start();
			ProducerTemplate template= new DefaultProducerTemplate(context);
			template.start();
			template.sendBody("direct:start","Camel Test");
			template.sendBody("direct:start","Camel Components");
			template.sendBody("direct:start","Camel DSL");
			template.sendBody("direct:start","Camel CBR");
			template.sendBody("direct:start","Spring integration");
			Thread.sleep(5000);
			context.stop();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	private static void transferAfile() {
		System.out.println("Starts..");
		CamelContext context=new DefaultCamelContext();		
		try {
			context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() {
					String source ="/home/altug/Desktop";
					String destination="/home/altug/Desktop/JAVA/";
					from("file:"+source+"?fileName=keystore.p12&noop=true")
					.log("File processed")
					.to("file:"+destination+"?fileName=keystore.p12&fileExist=Append");
				}
			});
		
			context.start();
			
			System.out.println("context.getRoutes()"+context.getRoutes().toString());
			System.out.println("context.getStatus()"+context.getStatus().toString());
			Thread.sleep(5000);
			context.stop();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception:"+ e.getMessage());
		}
		System.out.println("Finish..");
	}

	
	private static void bindJavaObjectToXML() {
		try (CamelContext context=new DefaultCamelContext();){
			context.setTracing(true);
	 		context.addRoutes(new RouteBuilder() {
	 			
								
				@Override
				public void configure() {
				from("file:data:/inbox?noop=true")
				.marshal().jaxb()
				.to("file:data:/inbox?noop=true")
				  .process(exchange -> LOGGER.info("The HTTP response code is: {}", 
						  exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE)));
				
//				from("seda:orderQeue")
//				.process(new MyProcessor())
//				.marshal().json(JsonLibrary.Gson)
//				.to("file:data/outbox?fileName=JsonOutput.txt");
				}
			});
	 		context.start();
	 		context.stop();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void sendDummyData() {
	CamelContext context=new DefaultCamelContext();	
	try {
		ConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(activeMQConnectionFactory));	
 		context.setTracing(true);
 		
 		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() {
				from("timer:mytimer?period=5000")
		        .setBody(constant("HELLO from Camel!"))
		        .to("jms:queue:HELLO.WORLD");
			}
		});
 		System.out.println("Starts..");
 		context.start();
 		System.out.println("context.getRoutes()"+context.getRoutes().toString());
		System.out.println("context.getStatus()"+context.getStatus().toString());
 		context.stop();
	}catch(Exception e) {
		e.printStackTrace();
	}
	System.out.println("Finish..");
}

	
	private static void processOrder() {
		CamelContext context=new DefaultCamelContext();	
		try {
			ConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
	 		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(activeMQConnectionFactory));	
	 		context.setTracing(true);
	 		
	 		context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() {
					String source ="/home/altug/Desktop";
					String destination="jms:Orders";
					from("file:"+source+"?fileName=keystore.p12&noop=true")
					.log("${body}")
					.process(new MyProcessor())
					.wireTap("jms:orderAudit")
					.choice()
						.when(header("CamelFileName").endsWith(".jks"))
						.to(destination)
						.when(header("CamelFileName").endsWith(".csv"))
						.to("jms:csvOrders")
						.otherwise()
						.stop();
//						.to("jms:invalidOrders").stop(); 							
					;
				}
			});
	 		System.out.println("Starts..");
	 		context.start();
	 		System.out.println("context.getRoutes()"+context.getRoutes().toString());
			System.out.println("context.getStatus()"+context.getStatus().toString());
	 		Thread.sleep(5000);
	 		context.stop();
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Finish..");
	}
	
	private static void springIntegration() {
		try {
		ApplicationContext	context	= 
				new ClassPathXmlApplicationContext("SpringDSL.xml");
		@SuppressWarnings("deprecation")
		CamelContext camelCxt= SpringCamelContext.springCamelContext(context, false);
		ProducerTemplate template= camelCxt.createProducerTemplate();
		camelCxt.start();
		Course course = new Course(100,"Apache Camel",60);
		template.sendBodyAndHeader("direct:start", course, "METHOD", "POST");
		
		System.out.println("Application context is started ");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
		
}
