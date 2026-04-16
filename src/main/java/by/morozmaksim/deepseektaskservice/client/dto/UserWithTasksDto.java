package by.morozmaksim.deepseektaskservice.client.dto;

import by.morozmaksim.deepseektaskservice.web.dto.TaskDto;
import lombok.Data;

import java.util.List;

@Data
public class UserWithTasksDto {
    private UserDto user;
    private List<TaskDto> tasks;
}
