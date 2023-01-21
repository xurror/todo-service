package jolly.roger.todoService.controller;

import jolly.roger.todoService.dto.TodoDTO;
import jolly.roger.todoService.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/{todoId}")
    public TodoDTO getTodo(@PathVariable Long todoId) {
        return todoService.getTodo(todoId);
    }

    @GetMapping
    public List<TodoDTO> getTodos(@RequestParam List<Integer> status) {
        return todoService.getTodos(status);
    }

    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(@RequestBody TodoDTO todoDTO) {
        return ResponseEntity.ok(this.todoService.createTodo(todoDTO));
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<TodoDTO> createTodo(@PathVariable Long todoId, @RequestBody TodoDTO todoDTO) {
        return ResponseEntity.accepted().body(this.todoService.updateTodo(todoId, todoDTO));
    }
}
