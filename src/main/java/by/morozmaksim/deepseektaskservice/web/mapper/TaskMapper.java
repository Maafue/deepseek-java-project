package by.morozmaksim.deepseektaskservice.web.mapper;

import by.morozmaksim.deepseektaskservice.domain.entity.Task;
import by.morozmaksim.deepseektaskservice.web.dto.TaskDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto taskToTaskDto (Task task);
    List<TaskDto> tasksToTaskDto (List<Task> tasks);
}
