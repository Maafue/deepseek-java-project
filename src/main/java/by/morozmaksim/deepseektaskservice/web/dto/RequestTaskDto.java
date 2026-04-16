package by.morozmaksim.deepseektaskservice.web.dto;

import by.morozmaksim.deepseektaskservice.domain.entity.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestTaskDto {

    @NotBlank(message = "Title must not be blank.")
    @Size(min = 3, max = 100, message = "Title must be min = 3, max = 100")
    private String title;
    private TaskStatus status;
    @JsonProperty("user_id")
    private Long userId;
}
