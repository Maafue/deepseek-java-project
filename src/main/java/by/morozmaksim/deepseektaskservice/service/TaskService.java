package by.morozmaksim.deepseektaskservice.service;

import by.morozmaksim.deepseektaskservice.domain.entity.Task;

import java.util.List;

public interface TaskService {

    Task createTask(Task task);
    Task getTask(Long id);
    Task updateStatus(Long taskId, String status);
    List<Task> getTasks();
    void delete(Long id);
    List<Task> findAllByStatus(String status);
    Task assignUserToTask(Long taskId, Long userId);
    Task unassignUserToTask(Long taskId);
    List<Task> getAllByUserId(Long userId);
}
