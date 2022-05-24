package com.example.demo.services;


import com.example.demo.models.Course;
import com.example.demo.repo.CourseRepository;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService implements  UserServiceImpl {

  @Autowired
  private UserRepository userRepo;
  @Autowired
  private CourseRepository courseRepository;

  public List<User> findAllUsers() {
    return userRepo.findAll();
  }


  public Optional<User> findUserById(int id) {
    return userRepo.findById(id);
  }

  public User findByUserName(String userName) {

    User user = userRepo.findByUserName(userName);
    return user;

  }

  public User saveUser(User newUser) {

    User user = userRepo.save(newUser);
    return user;

  }

  public User updateUser(int id, User user) {
    Optional<User> retrievedUser = userRepo.findById(id);
    if (retrievedUser == null)
      try {
        throw new Exception("User not found");
      } catch (Exception e) {
        e.printStackTrace();
      }
    userRepo.save(user);
    return userRepo.findById(id).get();

  }

  public User deleteUser(int userId) {

    Optional<User> retrievedUser = userRepo.findById(userId);
    if (retrievedUser == null)
      try {
        throw new Exception("User not found");
      } catch (Exception e) {
        e.printStackTrace();
      }
    userRepo.deleteById(userId);
    return retrievedUser.get();

  }
  public boolean addCourseToUser(Integer userId, List<Integer> productIds) {
    Optional<User> userOptional = userRepo.findById((int) userId.longValue());
    User user = userOptional.orElse(null);
    if (user == null) {
      return false;
    }
    List<Course> courses= new ArrayList<>();
    productIds.forEach(id -> courseRepository.findById(id.longValue()).ifPresent(p -> courses.add(p)));
    user.setCourses(courses);
    userRepo.save(user);
    return true;
  }
}