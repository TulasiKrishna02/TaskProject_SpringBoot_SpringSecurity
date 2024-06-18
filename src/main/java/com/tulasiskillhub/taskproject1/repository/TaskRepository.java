package com.tulasiskillhub.taskproject1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tulasiskillhub.taskproject1.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	
	public List<Task> findAllByUsersId(long userid);

}
