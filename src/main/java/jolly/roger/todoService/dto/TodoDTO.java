package jolly.roger.todoService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jolly.roger.todoService.domain.Status;
import jolly.roger.todoService.domain.Todo;
import lombok.Builder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Builder
public record TodoDTO(Long id, String description, Integer status,
                      @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dueDate,
                      @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime doneDate,
                      @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime createdDate) {


    public static TodoDTO fromEntity(final Todo todo) {
        return TodoDTO.builder()
                .id(todo.getId())
                .description(todo.getDescription())
                .status(todo.getStatus().getCode())
                .dueDate(toLocalDateTime(todo.getDueDate()))
                .doneDate(toLocalDateTime(todo.getDoneDate()))
                .createdDate(toLocalDateTime(todo.getCreatedDate()))
                .build();
    }

    public Todo toEntity() {
        return Todo.builder()
                .id(this.id())
                .description(this.description())
                .status(Status.fromInt(this.status()))
                .dueDate(toInstant(this.dueDate()))
                .doneDate(toInstant(this.doneDate()))
                .createdDate(toInstant(this.createdDate()))
                .build();
    }

    private static LocalDateTime toLocalDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    private Instant toInstant(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        ZoneId zoneId = ZoneId.systemDefault();
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(Instant.now());
        return dateTime.toInstant(zoneOffset);
    }
}
