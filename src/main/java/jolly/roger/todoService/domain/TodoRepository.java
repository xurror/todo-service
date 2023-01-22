package jolly.roger.todoService.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query("select t from Todo t where t.status = ?1 and t.dueDate < ?2")
    List<Todo> findByStatusAndDueDateBefore(Status status, Instant dueDate);
    @Query("select t from Todo t where t.status in ?1")
    List<Todo> findByStatusIn(Collection<Status> statuses);
}
