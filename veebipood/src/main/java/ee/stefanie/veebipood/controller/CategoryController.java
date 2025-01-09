package ee.stefanie.veebipood.controller;

import ee.stefanie.veebipood.entity.Category;
import ee.stefanie.veebipood.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("http://localhost:4200")
@RestController
@Log4j2
public class CategoryController {

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
        log.info("Uue kategooria lisas {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        categoryRepository.save(category);
        return categoryRepository.findAll();
    }

    @DeleteMapping("categories/{id}")
    public List<Category> deleteCategory(@PathVariable Long id) {
        log.info("Kategooria kustutas {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        categoryRepository.deleteById(id);
        return categoryRepository.findAll();
    }

    @PutMapping("categories/{id}")
    public List<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        if (categoryRepository.findById(id).isPresent()) {
            log.info("Kategooria uuendas {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            categoryRepository.save(category);
        }
        return categoryRepository.findAll();
    }
}
