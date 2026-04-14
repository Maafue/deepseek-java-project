package by.morozmaksim.deepseekjavaproject.service;

import by.morozmaksim.deepseekjavaproject.domain.entity.Task;
import by.morozmaksim.deepseekjavaproject.domain.entity.TaskStatus;
import by.morozmaksim.deepseekjavaproject.domain.exception.InvalidStatusTransitionException;
import by.morozmaksim.deepseekjavaproject.domain.exception.ResourceNotFoundException;
import by.morozmaksim.deepseekjavaproject.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        if (task.getCompleted() == null) task.setCompleted(false);
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

        if (!canChangeStatus(task.getStatus(), newStatus)){
            throw new InvalidStatusTransitionException("Cannot change status to this value.");
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

    public TaskStatus getStatusByValue(String value){
        TaskStatus newStatus;
        try  {
            newStatus = TaskStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid status.");
        }
        return newStatus;
    }

}
