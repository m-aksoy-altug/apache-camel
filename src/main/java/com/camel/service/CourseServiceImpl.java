package com.camel.service;

import javax.ws.rs.core.Response;

import com.camel.model.Course;

public class CourseServiceImpl implements CourseService{

	@Override
	public Response addCourse(Course course) throws Exception {
		// Saving logic here
		return Response.ok(course).build();
	}

}
