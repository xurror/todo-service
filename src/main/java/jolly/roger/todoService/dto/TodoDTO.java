package jolly.roger.todoService.dto;

import lombok.Builder;
import java.time.Instant;

@Builder
public record TodoDTO(Long id, String description, Integer status, Instant dueDate, Instant doneDate, Instant createdDate) {
}
