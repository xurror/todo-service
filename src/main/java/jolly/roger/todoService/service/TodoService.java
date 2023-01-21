package jolly.roger.todoService.service;

import jolly.roger.todoService.dto.TodoDTO;

import java.util.List;

public interface TodoService {
    
    TodoDTO getTodo(Long todoId);

    List<TodoDTO> getTodos(List<Integer> status);
}
