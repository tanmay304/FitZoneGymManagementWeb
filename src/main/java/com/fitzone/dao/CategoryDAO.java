package com.fitzone.dao;

import com.fitzone.model.Category;
import java.util.List;

public interface CategoryDAO {
    List<Category> getAllCategories();
    boolean addCategory(Category category);
    boolean updateCategory(Category category);
    boolean deleteCategory(int id);
}
