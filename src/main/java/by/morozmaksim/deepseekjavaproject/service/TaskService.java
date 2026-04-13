package by.morozmaksim.deepseekjavaproject.service;

import by.morozmaksim.deepseekjavaproject.domain.Task;

import java.util.List;

public interface TaskService {

    Task createTask(Task task);
    Task getTask(Long id);
    List<Task> getTasks();
    void delete(Long id);
}
