package com.gmail.sebastian.pisarski.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.sebastian.pisarski.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
