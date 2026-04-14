package by.morozmaksim.deepseekjavaproject.web.controller;

import by.morozmaksim.deepseekjavaproject.domain.entity.Task;
import by.morozmaksim.deepseekjavaproject.service.TaskService;
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
}
