package com.tulasiskillhub.taskproject1.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tulasiskillhub.taskproject1.entity.Task;
import com.tulasiskillhub.taskproject1.entity.Users;
import com.tulasiskillhub.taskproject1.exception.APIException;
import com.tulasiskillhub.taskproject1.exception.TaskNotFound;
import com.tulasiskillhub.taskproject1.exception.UserNotFound;
import com.tulasiskillhub.taskproject1.payload.TaskDto;
import com.tulasiskillhub.taskproject1.repository.TaskRepository;
import com.tulasiskillhub.taskproject1.repository.UserRepository;
import com.tulasiskillhub.taskproject1.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public TaskDto saveTask(long userid, TaskDto taskDto) {
		Users user = userRepository.findById(userid).orElseThrow(

				() -> new UserNotFound(String.format("User Id %d not found", userid))

		);
		Task task = modelMapper.map(taskDto, Task.class);
		task.setUsers(user);
		// After setting the user,we are storing the data in DB
		Task savedtask = taskRepository.save(task);
		return modelMapper.map(savedtask, TaskDto.class);
	}

	@Override
	public List<TaskDto> getAllTasks(long userid) {

		userRepository.findById(userid).orElseThrow(

				() -> new UserNotFound(String.format("User Id %d not found", userid))

		);
		List<Task> tasks = taskRepository.findAllByUsersId(userid);

		return tasks.stream().map(

				task -> modelMapper.map(task, TaskDto.class)

		).collect(Collectors.toList());
	}

	@Override
	public TaskDto getTask(long userid, long taskid) {

		Users users = userRepository.findById(userid).orElseThrow(

				() -> new UserNotFound(String.format("User Id %d not found", userid))

		);
		Task task = taskRepository.findById(taskid).orElseThrow(

				() -> new TaskNotFound(String.format("Task Id %d not found", taskid))

		);

		if (users.getId() != task.getUsers().getId()) {

			throw new APIException(String.format("Task Id %d is not belongs to User Id %d", taskid, userid));
		}

		return modelMapper.map(task, TaskDto.class);
	}

	@Override
	public void deleteTask(long userid, long taskid) {
		Users users = userRepository.findById(userid).orElseThrow(

				() -> new UserNotFound(String.format("User Id %d not found", userid))

		);
		Task task = taskRepository.findById(taskid).orElseThrow(

				() -> new TaskNotFound(String.format("Task Id %d not found", taskid))

		);

		if (users.getId() != task.getUsers().getId()) {

			throw new APIException(String.format("Task Id %d is not belongs to User Id %d", taskid, userid));
		}
		taskRepository.deleteById(taskid);// delete the task

	}

}
