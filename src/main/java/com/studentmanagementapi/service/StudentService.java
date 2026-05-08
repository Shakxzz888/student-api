package com.studentmanagementapi.service;

import com.studentmanagementapi.model.Student;


import java.util.List;

import org.springframework.data.domain.Page;

public interface StudentService {
	
	Page<Student> getStudents (int page, int size, String sortBy, String direction);

    Student addStudent(Student student);

    List<Student> getAllStudents();
    
    Page<Student> searchByName(String name, int page,int size,String sortBy, String direction);

    Student getStudentById(Long id);
    
    
    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);
    
    String getGrade(double marks);
}