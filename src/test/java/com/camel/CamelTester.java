package com.camel;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class CamelTester extends CamelTestSupport {
	
	@Produce(property="jms:partnerAudit")
	ProducerTemplate partnerAudit;
		
	 @Test
	    public void testMock() throws Exception {
	        getMockEndpoint("mock:result").expectedBodiesReceived("Hello World");

	        template.sendBody("direct:start", "Hello World");

	        MockEndpoint.assertIsSatisfied(context);
	    }

	    @Override
	    protected RoutesBuilder createRouteBuilder() {
	        return new RouteBuilder() {
	            @Override
	            public void configure() {
	                from("direct:start").to("mock:result");
	            }
	        };
	    }
	    
	}
