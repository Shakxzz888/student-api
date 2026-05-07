package com.studentmanagementapi.repository;




import org.springframework.data.domain.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentmanagementapi.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	
	

	Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
}