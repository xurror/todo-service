package jolly.roger.todoService.service.impl;

import jolly.roger.todoService.domain.Status;
import jolly.roger.todoService.domain.Todo;
import jolly.roger.todoService.domain.TodoRepository;
import jolly.roger.todoService.dto.TodoDTO;
import jolly.roger.todoService.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public TodoDTO getTodo(Long todoId) {
        Todo todo = this.todoRepository.findById(todoId).orElseThrow();
        return TodoDTO.fromEntity(todo);
    }

    @Override
    public List<TodoDTO> getTodos(List<Integer> status) {
        final List<Status> statuses = status.stream().map(Status::fromInt).toList();
        List<Todo> todos = this.todoRepository.findByStatusIn(statuses);
        return todos.stream().map(TodoDTO::fromEntity).toList();
    }

    @Override
    public TodoDTO createTodo(TodoDTO todoDTO) {
        Todo todo = todoDTO.toEntity();
        todo = this.todoRepository.save(todo);
        return TodoDTO.fromEntity(todo);
    }

    @Override
    public TodoDTO updateTodo(Long todoId, TodoDTO todoDTO) {
        Todo todo = todoDTO.toEntity();
        if (todo.getDueDate() != null && Instant.now().isAfter(todo.getDueDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Forbidden Update, Item is past due");
        }
        todo = this.todoRepository.save(todo);
        return TodoDTO.fromEntity(todo);
    }
}
