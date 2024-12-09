package edu.uca.final_project.model;
import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private List<Item> items;
    private List<Category> subCategories;

    // Constructor
    public Category(String name) {
        this.name = name;
        this.items = new ArrayList<>();
        this.subCategories = new ArrayList<>();
    }

    // Methods for adding/removing items or subcategories
    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addSubCategory(Category category) {
        subCategories.add(category);
    }

    public void removeSubCategory(Category category) {
        subCategories.remove(category);
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public List<Category> getSubCategories() { return subCategories; }
    public void setSubCategories(List<Category> subCategories) { this.subCategories = subCategories; }
}