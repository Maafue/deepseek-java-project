package by.morozmaksim.deepseekjavaproject.service;

import by.morozmaksim.deepseekjavaproject.domain.entity.Task;

import java.util.List;

public interface TaskService {

    Task createTask(Task task);
    Task getTask(Long id);
    Task updateStatus(Long taskId, String status);
    List<Task> getTasks();
    void delete(Long id);
    List<Task> findAllByStatus(String status);
}
