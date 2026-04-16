package by.morozmaksim.deepseektaskservice.web.dto;

import by.morozmaksim.deepseektaskservice.domain.entity.TaskStatus;
import lombok.Data;

@Data
public class ResponseTaskDto {
    private Long id;
    private String title;
    private TaskStatus status;
    private Long userId;
}
