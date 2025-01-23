package ee.stefanie.planner.controller;

import ee.stefanie.planner.entity.Category;
import ee.stefanie.planner.repository.CategoryRepository;
import ee.stefanie.planner.repository.TaskRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
public class CategoryController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("categories")
    public List<Category> getCategory() {
        return categoryRepository.findAll();
    }

    @GetMapping("categories/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @PostMapping("categories")
    public List<Category> addCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return categoryRepository.findAll();
    }

    @DeleteMapping("categories/{id}")
    public List<Category> deleteCategory(@PathVariable Long id) {
        if (!taskRepository.findByCategory_Id(id).isEmpty()) {
            throw new RuntimeException("Category is in use!");
        }
        categoryRepository.deleteById(id);
        return categoryRepository.findAll();
    }

    @PutMapping("categories/{id}")
    public List<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        if (categoryRepository.findById(id).isPresent()) {
            categoryRepository.save(category);
        }
        return categoryRepository.findAll();
    }
}
