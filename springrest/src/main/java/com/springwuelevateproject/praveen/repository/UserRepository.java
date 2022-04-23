package com.springwuelevateproject.praveen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.springwuelevateproject.praveen.model.Student;
import com.springwuelevateproject.praveen.model.User;

public interface UserRepository 
extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	@Query(value="select * from users", nativeQuery = true)
	public List<User> getUserByName();
	
}