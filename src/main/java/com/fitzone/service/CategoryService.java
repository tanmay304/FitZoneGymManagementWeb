package com.fitzone.service;

import com.fitzone.model.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    boolean addCategory(Category category);
    boolean updateCategory(Category category);
    boolean deleteCategory(int id);
}
