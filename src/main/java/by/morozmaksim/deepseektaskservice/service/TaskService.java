package by.morozmaksim.deepseektaskservice.service;

import by.morozmaksim.deepseektaskservice.client.dto.UserWithTasksDto;
import by.morozmaksim.deepseektaskservice.domain.entity.Task;
import by.morozmaksim.deepseektaskservice.web.dto.TaskDto;

import java.util.List;

public interface TaskService {

    Task createTask(Task task);
    Task getTask(Long id);
    Task updateStatus(Long taskId, String status);
    List<Task> getTasks();
    void delete(Long id);
    List<Task> findAllByStatus(String status);
    TaskDto assignUserToTask(Long taskId, Long userId);
    TaskDto unassignUserToTask(Long taskId);
    UserWithTasksDto getByUserId(Long userId);
}
