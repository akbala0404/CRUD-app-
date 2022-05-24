package com.example.demo.models;


import lombok.Data;

import java.util.List;

@Data
public class AddCourseRequest {
    private Integer userId;
    private List<Integer> courseIds;
}
