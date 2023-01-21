package jolly.roger.todoService.controller;

import jolly.roger.todoService.dto.TodoDTO;
import jolly.roger.todoService.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/{todoId}")
    public TodoDTO getTodo(@PathVariable Long todoId) {
        return todoService.getTodo(todoId);
    }
}
