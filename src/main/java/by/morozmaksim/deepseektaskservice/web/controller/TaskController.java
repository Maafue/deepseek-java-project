package by.morozmaksim.deepseektaskservice.web.controller;

import by.morozmaksim.deepseektaskservice.client.dto.UserWithTasksDto;
import by.morozmaksim.deepseektaskservice.domain.entity.Task;
import by.morozmaksim.deepseektaskservice.service.TaskService;
import by.morozmaksim.deepseektaskservice.web.dto.TaskDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task created = taskService.createTask(task);
        URI location = URI.create("/tasks/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getTasks();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id){
        return taskService.getTask(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{taskId}/status")
    public Task updateStatus(@PathVariable Long taskId, @RequestParam String status){
        return taskService.updateStatus(taskId, status);
    }

    @GetMapping("/status")
    public List<Task> findAllByStatus(@RequestParam String status){
        return taskService.findAllByStatus(status);
    }

    @PatchMapping("/{id}/assign")
    public TaskDto assignUserToTask(@PathVariable Long id, @RequestParam Long userId){
        return taskService.assignUserToTask(id, userId);
    }
    @PatchMapping("/{id}/unassign")
    public TaskDto unassignUserFromTask(@PathVariable Long id){
        return taskService.unassignUserToTask(id);
    }

    @GetMapping("/user/{userId}")
    public UserWithTasksDto getByUserId(@PathVariable Long userId){
        return taskService.getByUserId(userId);
    }
}
