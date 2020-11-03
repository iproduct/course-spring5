package course.spring.restmvc.service.impl;

import course.spring.restmvc.dao.CategoryRepository;
import course.spring.restmvc.entity.Category;
import course.spring.restmvc.exception.EntityNotFoundException;
import course.spring.restmvc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public Collection<Category> getAllCategorys() {
        return categoryRepo.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Category with ID:%s not found.", id)));
    }

    @Override
    public Category addCategory(Category category) {
        category.setId(null);
        category.setCreated(LocalDateTime.now());
        category.setModified(LocalDateTime.now());
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        Category found = getCategoryById(category.getId());
        category.setModified(LocalDateTime.now());
        return categoryRepo.save(category);
    }

    @Override
    public Category deleteCategory(Long id) {
        Category deleted = categoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Category with ID:%s not found.", id)));
        categoryRepo.deleteById(id);
        return deleted;
    }

    @Override
    public long getCount() {
        return categoryRepo.count();
    }
}
