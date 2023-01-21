package jolly.roger.todoService.service.impl;

import jolly.roger.todoService.domain.Todo;
import jolly.roger.todoService.domain.TodoRepository;
import jolly.roger.todoService.dto.TodoDTO;
import jolly.roger.todoService.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public TodoDTO getTodo(Long todoId) {
        Todo todo = this.todoRepository.findById(todoId).orElseThrow();
        return TodoDTO.fromEntity(todo);
    }
}
