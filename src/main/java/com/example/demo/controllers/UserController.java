package com.example.demo.controllers;

import java.util.List;
import javax.annotation.security.RolesAllowed;

import com.example.demo.models.AddCourseRequest;
import com.example.demo.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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

import com.example.demo.models.User;
import com.example.demo.services.UserService;

@Controller
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class UserController {

  @Autowired
  UserService userService;
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
  @GetMapping("/users")
  public String getAllUsers(Model model) {
    List<User> users = userService.findAllUsers();
    model.addAttribute("users", users);
    return "users";
  }

  @PostMapping("/users")
  public ResponseEntity<User> saveusers(@RequestBody User newUser,Authentication auth) {
    System.out.println(newUser.getUserName()+"  "+auth.getName());
    return ResponseEntity.status(HttpStatus.CREATED).body((userService.saveUser(newUser)));

  }

  @PreAuthorize("@userSecurity.hasUserId(authentication,#userId)")
  @GetMapping("/users/{userId}")
  public ResponseEntity<User> getUserById(@PathVariable("userId") int userId, Authentication authentication) {
    System.out.println("Inside getuserbyid method");
    return ResponseEntity.ok().body(userService.findUserById(userId).get());

  }
  @PutMapping("/users/{userId}")
  public ResponseEntity<User> updateUser(@PathVariable("userId") int UserId,@RequestBody User newUser) {
    return ResponseEntity.ok().body(userService.updateUser(UserId,newUser));

  }
  @RequestMapping ("/deleteUser/{userId}")
  public String deleteUser(@PathVariable("userId") int UserId) {
    userService.deleteUser(UserId);
    return "redirect:/";

  }

  @RequestMapping("/addCourse")
  public String coursetoUser(Model model){
    AddCourseRequest request=new AddCourseRequest();
    model.addAttribute("course",request);
    return "addCourseToUser";
  }
  @PostMapping("/addcourse")
  public String addCourseToUser(AddCourseRequest request){
    userService.addCourseToUser(request.getUserId(), request.getCourseIds());
    return "redirect:/";
  }
}