package com.camel.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement(name="course")
public class Course {
	private int courseId;
	private String courseName;
	private int duration;
	public int getCourseId() {
		return courseId;
	}
	@XmlElement(name="courseId")
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	@XmlElement(name="courseName")
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getDuration() {
		return duration;
	}
	@XmlElement(name="duration")
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Course(int courseId, String courseName, int duration) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.duration = duration;
	}
	public Course() {
		super();
	}
	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", duration=" + duration + "]";
	}
	

}
