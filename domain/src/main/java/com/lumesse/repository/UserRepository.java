package com.lumesse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lumesse.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
