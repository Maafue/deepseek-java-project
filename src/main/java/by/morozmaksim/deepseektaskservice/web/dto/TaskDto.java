package by.morozmaksim.deepseektaskservice.web.dto;

import by.morozmaksim.deepseektaskservice.client.dto.UserDto;
import by.morozmaksim.deepseektaskservice.domain.entity.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private TaskStatus status;
    private UserDto user;
}
