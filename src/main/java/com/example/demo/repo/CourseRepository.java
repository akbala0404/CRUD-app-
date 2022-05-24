package com.example.demo.repo;

import com.example.demo.models.Course;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
    Course findCourseByName(String name);
    Course findCourseById(Long id);
}
