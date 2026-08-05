package com.fitzone.service.impl;

import com.fitzone.dao.CategoryDAO;
import com.fitzone.dao.impl.CategoryDAOImpl;
import com.fitzone.model.Category;
import com.fitzone.service.CategoryService;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryServiceImpl() {
        this.categoryDAO = new CategoryDAOImpl();
    }

    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    @Override
    public boolean addCategory(Category category) {
        return categoryDAO.addCategory(category);
    }

    @Override
    public boolean updateCategory(Category category) {
        return categoryDAO.updateCategory(category);
    }

    @Override
    public boolean deleteCategory(int id) {
        return categoryDAO.deleteCategory(id);
    }
}
