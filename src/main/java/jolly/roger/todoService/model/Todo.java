package jolly.roger.todoService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table("todo")
public class Todo {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column("description")
    private String description;


    @Column("status")
    @JdbcTypeCode(SqlTypes.TINYINT)
    @Convert(converter = StatusAttributeConverter.class)
    private Status status;

    @Column("due_date")
    private Instant dueDate;

    @Column("done_date")
    private Instant doneDate;

    @CreatedDate
    @Column("created_date")
    private Instant createdDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Todo todo = (Todo) o;
        return id != null && Objects.equals(id, todo.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
