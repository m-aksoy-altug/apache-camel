package com.camel.service;

import javax.ws.rs.core.Response;

import com.camel.model.Course;

public interface CourseService {
	public Response addCourse(Course course) throws Exception;
}
