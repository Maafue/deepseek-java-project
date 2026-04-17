package by.morozmaksim.deepseektaskservice.web.controller;

import by.morozmaksim.deepseektaskservice.domain.entity.Task;
import by.morozmaksim.deepseektaskservice.service.TaskService;
import by.morozmaksim.deepseektaskservice.web.dto.RequestTaskDto;
import by.morozmaksim.deepseektaskservice.web.dto.ResponseTaskDto;
import by.morozmaksim.deepseektaskservice.web.mapper.TaskMapper;
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
    private final TaskMapper taskMapper;

    @PostMapping
    public ResponseEntity<ResponseTaskDto> createTask(@Valid @RequestBody RequestTaskDto requestTaskDto) {
        Task task = taskMapper.requestTaskToTask(requestTaskDto);
        Task createdTask = taskService.createTask(task);
        ResponseTaskDto responseRequestTaskDto = taskMapper.taskToResponseTask(createdTask);
        URI location = URI.create("/tasks/" + createdTask.getId());
        return ResponseEntity.created(location).body(responseRequestTaskDto);
    }

    @PatchMapping("/{id}")
    public ResponseTaskDto updateTask(@PathVariable Long id, @Valid @RequestBody RequestTaskDto requestTaskDto){
        Task task = taskMapper.requestTaskToTask(requestTaskDto);
        Task createdTask = taskService.updateTask(id, task);
        ResponseTaskDto responseRequestTaskDto = taskMapper.taskToResponseTask(createdTask);
        return responseRequestTaskDto;
    }

    @GetMapping
    public List<ResponseTaskDto> getAllTasks(){
        return taskMapper.tasksToResponseTaskDto(taskService.getTasks());
    }

    @GetMapping("/{id}")
    public ResponseTaskDto getTask(@PathVariable Long id){
        return taskMapper.taskToResponseTask(taskService.getTask(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{taskId}/status")
    public ResponseTaskDto updateStatus(@PathVariable Long taskId, @RequestParam String status){
        return taskMapper.taskToResponseTask(taskService.updateStatus(taskId, status));
    }

    @GetMapping("/status")
    public List<ResponseTaskDto> findAllByStatus(@RequestParam String status){
        return taskMapper.tasksToResponseTaskDto(taskService.findAllByStatus(status));
    }

    @PatchMapping("/{id}/assign")
    public ResponseTaskDto assignUserToTask(@PathVariable Long id, @RequestParam Long userId){
        return taskMapper.taskToResponseTask(taskService.assignUserToTask(id, userId));
    }
    @PatchMapping("/{id}/unassign")
    public ResponseTaskDto unassignUserFromTask(@PathVariable Long id){
        return taskMapper.taskToResponseTask(taskService.unassignUserToTask(id));
    }

    @GetMapping("/user/{userId}")
    public List<ResponseTaskDto> getByUserId(@PathVariable Long userId){
        return taskMapper.tasksToResponseTaskDto(taskService.getAllByUserId(userId));
    }
}
