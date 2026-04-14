package by.morozmaksim.deepseekjavaproject.repository;

import by.morozmaksim.deepseekjavaproject.domain.entity.Task;
import by.morozmaksim.deepseekjavaproject.domain.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByStatus(TaskStatus status);
}
