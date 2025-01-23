package ee.stefanie.planner.controller;

import ee.stefanie.planner.entity.Category;
import ee.stefanie.planner.entity.Task;
import ee.stefanie.planner.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping()
    public Page<Task> getAllTasks(Pageable pageable, @RequestParam Long categoryId, String search) {
        if (categoryId > 0 && search.isEmpty()) {
            return taskRepository.findByCategory_Id(categoryId, pageable);
        } else if (categoryId == 0 && !search.isEmpty()){
            return taskRepository.findByTitleOrDescriptionContains(search, search, pageable);
        } else if (categoryId > 0 && !search.isEmpty()) {
            return taskRepository.findByCategory_IdAndTitleOrDescriptionContains(categoryId, search, search, pageable);
        }
        return taskRepository.findAll(pageable);
    }

    @GetMapping("/edit/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @PutMapping("/edit/{id}")
    public List<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.save(task);
        }
        return taskRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public List<Task> deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return taskRepository.findAll();

    }
}
