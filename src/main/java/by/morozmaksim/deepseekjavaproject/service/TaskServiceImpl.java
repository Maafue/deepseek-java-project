package by.morozmaksim.deepseekjavaproject.service;

import by.morozmaksim.deepseekjavaproject.domain.entity.Task;
import by.morozmaksim.deepseekjavaproject.domain.exception.ResourceNotFoundException;
import by.morozmaksim.deepseekjavaproject.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        if (task.getCompleted() == null ) task.setCompleted(false);
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

}
