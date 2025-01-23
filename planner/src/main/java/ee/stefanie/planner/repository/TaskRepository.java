package ee.stefanie.planner.repository;

import ee.stefanie.planner.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCategory_Id(Long id);
    Page<Task> findByCategory_Id(Long id, Pageable pageable);
    Page<Task> findByTitleOrDescriptionContains(String searchTitle, String searchDesc, Pageable pageable);
    Page<Task> findByCategory_IdAndTitleOrDescriptionContains(Long categoryId, String searchTitle, String searchDesc, Pageable pageable);
}
