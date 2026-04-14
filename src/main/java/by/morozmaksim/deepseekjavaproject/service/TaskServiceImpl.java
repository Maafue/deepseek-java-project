package by.morozmaksim.deepseekjavaproject.service;

import by.morozmaksim.deepseekjavaproject.domain.Task;
import by.morozmaksim.deepseekjavaproject.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTask(Long id) {
        return taskRepository.getReferenceById(id);
    }

    @Override
    public Task createTask(Task task) {
        if (task.getCompleted() == null ) task.setCompleted(false);
        return taskRepository.save(task);
    }
}
