package by.morozmaksim.deepseektaskservice.service;

import by.morozmaksim.deepseektaskservice.client.UserClient;
import by.morozmaksim.deepseektaskservice.client.dto.UserDto;
import by.morozmaksim.deepseektaskservice.client.dto.UserWithTasksDto;
import by.morozmaksim.deepseektaskservice.domain.entity.Task;
import by.morozmaksim.deepseektaskservice.domain.entity.TaskStatus;
import by.morozmaksim.deepseektaskservice.domain.exception.InvalidStatusTransitionException;
import by.morozmaksim.deepseektaskservice.domain.exception.ResourceNotFoundException;
import by.morozmaksim.deepseektaskservice.repository.TaskRepository;
import by.morozmaksim.deepseektaskservice.web.dto.TaskDto;
import by.morozmaksim.deepseektaskservice.web.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserClient userClient;
    private final TaskMapper taskMapper;

    @Override
    public Task createTask(Task task) {
        if (task.getUserId() != null) userClient.getUserByUserId(task.getUserId());
        task.setStatus(TaskStatus.TODO);
        return taskRepository.save(task);
    }

    @Override
    @Cacheable(value = "tasks", key = "#id")
    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id=" + id + " not found"));
    }

    @Override
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @Override
    @CacheEvict(value = "tasks", key = "#id")
    public void delete(Long id) {
        Task task = getTask(id);
        taskRepository.delete(task);
    }

    @Override
    public Task updateStatus(Long taskId, String value) {
        TaskStatus newStatus = getStatusByValue(value);
        Task task = getTask(taskId);
        if (task.getStatus() == newStatus) {
            throw new IllegalStateException("Status already set to the old value.");
        }
        if (!canChangeStatus(task.getStatus(), newStatus)) {
            throw new InvalidStatusTransitionException("Cannot change status to this value. Current status="
                    + task.getStatus());
        }
        task.setStatus(newStatus);
        taskRepository.save(task);
        return task;
    }

    @Override
    public List<Task> findAllByStatus(String value) {
        TaskStatus status = getStatusByValue(value);
        return taskRepository.findAllByStatus(status);
    }

    public boolean canChangeStatus(TaskStatus oldStatus, TaskStatus newStatus) {
        return switch (oldStatus) {
            case TODO -> newStatus == TaskStatus.IN_PROGRESS || newStatus == TaskStatus.REJECTED;
            case IN_PROGRESS -> newStatus == TaskStatus.DONE || newStatus == TaskStatus.REJECTED;
            default -> false;
        };
    }

    public TaskStatus getStatusByValue(String value) {
        TaskStatus newStatus;
        try {
            newStatus = TaskStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid status.");
        }
        return newStatus;
    }

    @Override
    public TaskDto assignUserToTask(Long taskId, Long userId) {
        UserDto userDto = userClient.getUserByUserId(userId);
        Task task = getTask(taskId);
        task.setUserId(userId);
        taskRepository.save(task);
        TaskDto taskDto = taskMapper.taskToTaskDto(task);
        taskDto.setUser(userDto);
        return taskDto;
    }

    @Override
    public TaskDto unassignUserToTask(Long taskId) {
        Task task = getTask(taskId);
        task.setUserId(null);
        taskRepository.save(task);
        TaskDto taskDto = taskMapper.taskToTaskDto(task);
        return taskDto;
    }

    @Override
    public UserWithTasksDto getByUserId(Long userId) {
        List<Task> taskList = taskRepository.findByUserId(userId);
        UserDto user = userClient.getUserByUserId(userId);

        List<TaskDto> taskDtos = taskMapper.tasksToTaskDto(taskList);

        UserWithTasksDto userWithTasksDto = new UserWithTasksDto();
        userWithTasksDto.setTasks(taskDtos);
        userWithTasksDto.setUser(user);
        return userWithTasksDto;
    }
}
