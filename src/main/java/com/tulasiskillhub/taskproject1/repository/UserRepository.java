package com.tulasiskillhub.taskproject1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tulasiskillhub.taskproject1.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long>{

	public Optional<Users> findByEmail(String email);

}
