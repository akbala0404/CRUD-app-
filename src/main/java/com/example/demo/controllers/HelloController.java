package com.example.demo.controllers;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.AddCourseRequest;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Course;
import com.example.demo.services.CourseService;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
  @Autowired
  CourseService courseService;
  @GetMapping("/courses")
  public String viewHomePage(Model model) {
    List<Course> courses = courseService.findAllCourses();
    model.addAttribute("courses", courses);
    return "courses";
  }

  @RequestMapping("/new")
  public String newCourse(Model model){
    Course course=new Course();
    model.addAttribute("course",course);
    return "addCourse";
  }
  @PostMapping("/add")
  public String addCourse(Course course){
    courseService.saveCourse(course);
    return "redirect:/";
  }

  @GetMapping("/courses/{id}")
  public ResponseEntity<Course> getCourseById(@PathVariable("id") Long courseId) {
    return ResponseEntity.ok().body(courseService.findCourseById(courseId).get());

  }

  @RequestMapping("/edit/{id}")
  public ModelAndView updateForm(@PathVariable("id") Long id ){
    ModelAndView mv=new ModelAndView("updateCourse");
    Course course=courseService.findCourseById(id).orElse(null);
    mv.addObject("course",course);
    return mv;
  }
  @PostMapping("/update/{id}")
  public String showEditpage(@PathVariable("id") Long courseId,Model model) {
    Optional<Course> course=courseService.findCourseById(courseId);
    model.addAttribute("course",course);
    return "updateCourse";
  }

  @RequestMapping("/delete/{id}")
  public String deleteCourse(@PathVariable("id") Long courseId){
    courseService.deleteCourse(courseId);
    return "redirect:/";
  }
}