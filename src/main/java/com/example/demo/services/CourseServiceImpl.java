package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.Course;

public interface CourseServiceImpl {

    public List<Course> findAllCourses() ;

    public Optional<Course> findCourseById(int id);

    public Course findByCourseName(String courseName) ;

}


