package jolly.roger.todoService.dto;

import jolly.roger.todoService.domain.Status;
import jolly.roger.todoService.domain.Todo;
import lombok.Builder;
import java.time.Instant;

@Builder
public record TodoDTO(Long id, String description, Integer status, Instant dueDate, Instant doneDate, Instant createdDate) {

    public static TodoDTO fromEntity(final Todo todo) {
        return TodoDTO.builder()
                .id(todo.getId())
                .description(todo.getDescription())
                .status(todo.getStatus().getCode())
                .dueDate(todo.getDueDate())
                .doneDate(todo.getDoneDate())
                .createdDate(todo.getCreatedDate())
                .build();
    }

    public Todo toEntity() {
        return Todo.builder()
                .id(this.id())
                .description(this.description())
                .status(Status.fromInt(this.status()))
                .dueDate(this.dueDate())
                .doneDate(this.doneDate())
                .createdDate(this.createdDate())
                .build();
    }
}
