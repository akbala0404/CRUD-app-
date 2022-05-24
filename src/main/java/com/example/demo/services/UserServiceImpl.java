package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import com.example.demo.models.User;

public interface UserServiceImpl {
    List<User> findAllUsers() ;
    Optional<User> findUserById(int id);
    User findByUserName(String userName) ;
     boolean addCourseToUser(Integer userId, List<Integer> courseIds);
}