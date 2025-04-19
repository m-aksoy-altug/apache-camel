package com.camel.dao;

import java.util.ArrayList;
import java.util.List;

import com.camel.model.Course;

public class CourseDAO {
	private static List<Course> courseDetails = new ArrayList<>();
	public CourseDAO() {
		if(courseDetails!=null) {
			Course course = new Course(101,"Apache Maven",7);
			Course course1 = new Course(102,"Apache Kafka",7);
			Course course2 = new Course(103,"Spring",7);
			Course course3 = new Course(104,"Camel",7);
			courseDetails.add(course);
			courseDetails.add(course1);
			courseDetails.add(course2);
			courseDetails.add(course3);
		}
	}
	
	public Course addCourse( Course course)  {
		courseDetails.add(course);
		System.out.println("Course is inserted");
		return course;
	}
}
