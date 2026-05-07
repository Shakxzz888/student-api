package com.studentmanagementapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class StudentRequest {

	@NotBlank(message = "Name is required")
    private String name;
	
	
	 @Min(value = 0, message = "Marks cannot be negative")
    @Max(value = 100, message = "Marks cannot be more than 100")
    private double marks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }
}