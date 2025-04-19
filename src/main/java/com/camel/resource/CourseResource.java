package com.camel.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.camel.model.Course;
import com.camel.service.CourseServiceImpl;

@Path("/courses")
@Produces("applicaiton/xml")
public class CourseResource {
	CourseServiceImpl service= new CourseServiceImpl();
	@POST
	@Consumes("applicaiton/xml")
	public Response addCourse(Course course) {
		try {
			Response responses= service.addCourse(course);
			System.out.println("responses"+responses);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return Response.ok(course).build();
	}

}
