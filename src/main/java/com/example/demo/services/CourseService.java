package com.example.demo.services;

import org.springframework.stereotype.Service;
import com.example.demo.models.Course;
import com.example.demo.repo.CourseRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class  CourseService implements  CourseServiceImpl{

    @Autowired
    private CourseRepository courseRepository;


    public List<Course> findAllCourses()  {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Optional<Course> findCourseById(int id) {
        return Optional.empty();
    }

    public Optional<Course> findCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course findByCourseName(String courseName) {
        Course course=courseRepository.findCourseByName(courseName);
        return course;

    }

    public Course saveCourse(Course newCourse) {

        Course course1=courseRepository.save(newCourse);
        return course1;

    }

    public Course updateCourse(Long id,Course course) {

        Optional<Course> retrievedCourse=courseRepository.findById(id);

        if(retrievedCourse==null)
            try {
                throw new Exception("Course not found");
            } catch (Exception e) {
                e.printStackTrace();
            }
        courseRepository.save(course);
        return courseRepository.findById(id).get();

    }

    public Course deleteCourse(Long id) {

        Optional<Course> retrievedCourse = courseRepository.findById(id);
        if (retrievedCourse == null)
            try {
                throw new Exception("Car not found");
            } catch (Exception e) {
                e.printStackTrace();
            }
        courseRepository.deleteById(id);
        return retrievedCourse.get();
    }
    }



