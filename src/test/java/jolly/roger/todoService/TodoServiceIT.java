package jolly.roger.todoService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jolly.roger.todoService.domain.Status;
import jolly.roger.todoService.domain.Todo;
import jolly.roger.todoService.dto.TodoDTO;
import jolly.roger.todoService.domain.TodoRepository;
import net.bytebuddy.utility.RandomString;
import org.hamcrest.collection.ArrayAsIterableMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoServiceIT {

    @Autowired
    private MockMvc restMockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TodoRepository todoRepository;

    //    add an item,
    @Test
    public void createTodo() throws Exception {
        String testDescription = "A Test Todo";
        LocalDateTime testDueDate = LocalDateTime.now().plus(5, ChronoUnit.DAYS);
        TodoDTO testTodo = TodoDTO.builder()
                .description(testDescription)
                .dueDate(testDueDate)
                .build();
        restMockMvc.perform(post("/todos")
                        .content(mapper.writeValueAsBytes(testTodo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.description").value(testDescription))
                .andExpect(jsonPath("$.dueDate").value(testDueDate))
                .andReturn();
    }

//    change description of an item,
    @Test
    public void changeTodoDescription() throws Exception {

        Todo testTodo = Todo.builder()
                .description("")
                .build();
        testTodo = todoRepository.save(testTodo);

        assert testTodo.getId() != null;
        assert testTodo.getDescription().equals("");

        Long testId = testTodo.getId();
        String updatedDescription = "Todo with new Description";
        testTodo.setDescription(updatedDescription);

        restMockMvc.perform(put("/todos")
                .content(mapper.writeValueAsBytes(testTodo)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(testId))
                .andExpect(jsonPath("$.description").value(updatedDescription))
                .andReturn();

    }

//    mark an item as "done",
    @Test
    public void markStatusAsDone() throws Exception {
        Todo testTodo = Todo.builder()
                .description(RandomString.make(10))
                .build();
        testTodo = todoRepository.save(testTodo);

        assert testTodo.getId() != null;
        assert testTodo.getStatus() == Status.TODO;

        Long testId = testTodo.getId();
        testTodo.setStatus(Status.DONE);

        restMockMvc.perform(put("/todos")
                        .content(mapper.writeValueAsBytes(testTodo)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(testId))
                .andExpect(jsonPath("$.status").value(Status.DONE.getCode()))
                .andReturn();
    }

//    mark an item as "not done",
    @Test
    public void markStatusAsNotDone() throws Exception {
        Todo testTodo = Todo.builder()
                .description(RandomString.make(10))
                .status(Status.DONE)
                .build();
        testTodo = todoRepository.save(testTodo);

        assert testTodo.getId() != null;
        assert testTodo.getStatus() == Status.DONE;

        Long testId = testTodo.getId();
        testTodo.setStatus(Status.TODO);

        restMockMvc.perform(put("/todos")
                        .content(mapper.writeValueAsBytes(testTodo)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(testId))
                .andExpect(jsonPath("$.status").value(Status.TODO.getCode()))
                .andReturn();
    }

//    get all items that are "not done" (with an option to retrieve all items),
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getTodosWithStatusNotDone() throws Exception {
        List<Todo> todos = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            int status = i % 3;
            if (status == 0) {
                status = 3;
            }
            todos.add(Todo.builder()
                    .description(RandomString.make(10))
                    .status(Status.fromInt(status))
                    .build());
        }
        this.todoRepository.saveAll(todos);

        restMockMvc.perform(get("/todos")
                        .param("status", String.valueOf(Status.TODO.getCode())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").exists())
                .andExpect(jsonPath("$[*].status").isArray())
                .andDo(result -> {
                    List<TodoDTO> todoDTOS = mapper.readValue(result.getResponse().getContentAsByteArray(),
                            new TypeReference<>() {});
                    todoDTOS.forEach(todo -> assertThat(todo.status(), is(Status.TODO.getCode())));
                })
                .andReturn();
    }

//    get details of a specific item.
    @Test
    public void getATodoDetails() throws Exception {
        Todo testTodo = Todo.builder()
                .description(RandomString.make(10))
                .status(Status.DONE)
                .build();
        testTodo = todoRepository.save(testTodo);

        assert testTodo.getId() != null;
        Long testId = testTodo.getId();

        restMockMvc.perform(get("/todos/" + testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId))
                .andExpect(jsonPath("$.status").value(Status.DONE.getCode()))
                .andExpect(jsonPath("$.description").isString())
                .andExpect(jsonPath("$.createdDate").isString())
                .andReturn();
    }
}
