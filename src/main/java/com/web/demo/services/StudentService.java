package com.web.demo.services;

import com.web.demo.dtos.StudentDTO;
import com.web.demo.dtos.StudentMarksDTO;

import java.util.List;

public interface StudentService {

    List<StudentDTO> readStudentDetails();

    List<StudentMarksDTO> readStudentWithMarks();
}
