package com.studentmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import com.studentmanagementapi.dto.StudentRequest;
import com.studentmanagementapi.dto.StudentResponse;
import com.studentmanagementapi.model.Student;
import com.studentmanagementapi.service.StudentService;

import jakarta.validation.Valid;

import java.util.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    
    
    @GetMapping("/paged")
    public Map<String, Object> getStudents(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<Student> studentPage;

        // 🔍 SEARCH + PAGINATION + SORT
        if (name != null && !name.isEmpty()) {
            studentPage = studentService.searchByName(name, page, size, sortBy, direction);
        } else {
            studentPage = studentService.getStudents(page, size, sortBy, direction);
        }

        List<StudentResponse> response = new ArrayList<>();

        for (Student s : studentPage.getContent()) {
            response.add(new StudentResponse(
                    s.getId(),
                    s.getName(),
                    s.getMarks(),
                    studentService.getGrade(s.getMarks())
            ));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("content", response);
        result.put("currentPage", studentPage.getNumber());
        result.put("totalItems", studentPage.getTotalElements());
        result.put("totalPages", studentPage.getTotalPages());

        return result;
    }
    
    
    
   
    // ➕ Add 
    @PostMapping
    public StudentResponse add(@Valid @RequestBody StudentRequest req) {

        Student s = new Student();
        s.setName(req.getName());
        s.setMarks(req.getMarks());

        Student saved = studentService.addStudent(s);

        return new StudentResponse(
                saved.getId(),
                saved.getName(),
                saved.getMarks(),
                studentService.getGrade(saved.getMarks())
        );
    }
    

    // 📄 Get All
    @GetMapping
    public List<StudentResponse> getAllStudents() {

        List<Student> students = studentService.getAllStudents();
        List<StudentResponse> response = new ArrayList<>();

        for (Student s : students) {
            response.add(new StudentResponse(
                    s.getId(),
                    s.getName(),
                    s.getMarks(),
                    studentService.getGrade(s.getMarks()) // if you have this method
            ));
        }

        return response;
    }
    
    
    // 📊 Statistics API
    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        List<Student> students = studentService.getAllStudents();

        double total = 0;
        int pass = 0, fail = 0;

        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for (Student s : students) {
            double m = s.getMarks();
            total += m;

            if (m >= 50) pass++;
            else fail++;

            if (m > max) max = m;
            if (m < min) min = m;
        }

        Map<String, Object> stats = new HashMap<>();

        stats.put("average", students.isEmpty() ? 0 : total / students.size());
        stats.put("pass", pass);
        stats.put("fail", fail);
        stats.put("highest", max == Double.MIN_VALUE ? 0 : max);
        stats.put("lowest", min == Double.MAX_VALUE ? 0 : min);

        return stats;
    }
    
    
    

    // ❌ Delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    // ✏️ Update
    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student s) {
        return studentService.updateStudent(id, s);
    }

   
    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }
    
    
    
}