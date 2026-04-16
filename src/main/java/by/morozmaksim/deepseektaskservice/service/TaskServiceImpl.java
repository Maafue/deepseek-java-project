package by.morozmaksim.deepseektaskservice.service;

import by.morozmaksim.deepseektaskservice.client.UserClient;
import by.morozmaksim.deepseektaskservice.domain.entity.Task;
import by.morozmaksim.deepseektaskservice.domain.entity.TaskStatus;
import by.morozmaksim.deepseektaskservice.domain.exception.InvalidStatusTransitionException;
import by.morozmaksim.deepseektaskservice.domain.exception.ResourceNotFoundException;
import by.morozmaksim.deepseektaskservice.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserClient userClient;

    @Override
    @CachePut(value = "tasks", key = "#result.id")
    public Task createTask(Task task) {
        if (task.getUserId() != null) userClient.checkUserExist(task.getUserId());
        task.setStatus(TaskStatus.TODO);
        return taskRepository.save(task);
    }

    @Override
    @CachePut(value = "tasks", key = "#result.id")
    public Task updateTask(Long id, Task task) {
        Task existTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id=" + id + " not found"));
        if (task.getTitle() != null) existTask.setTitle(task.getTitle());
        if (task.getStatus() != null) existTask.setStatus(task.getStatus());
        return taskRepository.save(existTask);
    }

    @Override
    @Cacheable(value = "tasks", key = "#id")
    public Task getTask(Long id) {
        return findTaskById(id);
    }

    @Override
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @Override
    @CacheEvict(value = "tasks", key = "#id")
    public void delete(Long id) {
        if (!taskRepository.existsById(id)){
            throw new ResourceNotFoundException("Task with id=" + id + " not found");
        }
        taskRepository.deleteById(id);
    }

    @Override
    @CachePut(value = "tasks", key = "#result.id")
    public Task updateStatus(Long taskId, String value) {
        TaskStatus newStatus = getStatusByValue(value);
        Task task = findTaskById(taskId);
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
    @CachePut(value = "tasks", key = "#result.id")
    public Task assignUserToTask(Long taskId, Long userId) {
        userClient.checkUserExist(userId);
        Task task = findTaskById(taskId);
        task.setUserId(userId);
        taskRepository.save(task);
        return task;
    }

    @Override
    @CachePut(value = "tasks", key = "#result.id")
    public Task unassignUserToTask(Long taskId) {
        Task task = findTaskById(taskId);
        task.setUserId(null);
        taskRepository.save(task);
        return task;
    }

    @Override
    public List<Task> getAllByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    private Task findTaskById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id=" + id + " not found"));
    }
}
