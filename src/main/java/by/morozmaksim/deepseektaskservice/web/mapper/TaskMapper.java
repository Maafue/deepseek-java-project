package by.morozmaksim.deepseektaskservice.web.mapper;

import by.morozmaksim.deepseektaskservice.domain.entity.Task;
import by.morozmaksim.deepseektaskservice.web.dto.RequestTaskDto;
import by.morozmaksim.deepseektaskservice.web.dto.ResponseTaskDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task requestTaskToTask (RequestTaskDto requestTaskDto);
    ResponseTaskDto taskToResponseTask(Task task);
    List<ResponseTaskDto> tasksToResponseTaskDto(List<Task> tasks);
}