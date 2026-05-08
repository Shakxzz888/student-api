
package com.studentmanagementapi.dto;

public class StudentResponse {

    private Long id;
    private String name;
    private double marks;
    private String grade;

    public StudentResponse(Long id, String name, double marks, String grade) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.grade = grade;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getMarks() { return marks; }
    public String getGrade() { return grade; }
}