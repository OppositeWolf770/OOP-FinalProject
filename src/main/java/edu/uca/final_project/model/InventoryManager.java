package edu.uca.final_project.model;

public class InventoryManager {
    private Category rootCategory = new Category("Root");

    // Setter for rootCategory to set it after loading from a file
    public void setRootCategory(Category rootCategory) {
        this.rootCategory = rootCategory;
    }

    public Category getRootCategory() {
        return rootCategory;
    }
}