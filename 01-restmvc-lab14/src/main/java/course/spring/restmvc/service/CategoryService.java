package course.spring.restmvc.service;

import course.spring.restmvc.entity.Category;

import java.util.Collection;

public interface CategoryService {
    Collection<Category>  getAllCategorys();
    Category getCategoryById(Long id);
    Category addCategory(Category category);
    Category updateCategory(Category category);
    Category deleteCategory(Long id);
    long getCount();
}
