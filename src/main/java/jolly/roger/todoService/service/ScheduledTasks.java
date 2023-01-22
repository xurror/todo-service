package jolly.roger.todoService.service;

import jolly.roger.todoService.domain.Status;
import jolly.roger.todoService.domain.Todo;
import jolly.roger.todoService.domain.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final TodoRepository todoRepository;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void updateTodoStatus() {
        List<Todo> overdueTodos = this.todoRepository.findByStatusAndDueDateBefore(Status.TODO, Instant.now());
        if (!overdueTodos.isEmpty()) {
            overdueTodos.forEach(todo -> todo.setStatus(Status.OVERDUE));
            this.todoRepository.saveAll(overdueTodos);
        }
    }
}
