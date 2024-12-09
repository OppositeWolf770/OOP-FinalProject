package edu.uca.final_project.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uca.final_project.model.Category;
import edu.uca.final_project.model.InventoryManager;
import edu.uca.final_project.model.Item;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JSONPersistence {

    public static InventoryManager loadFromFile(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(fileName);

        // Read JSON file and map it to an InventoryData object
        InventoryData inventoryData = objectMapper.readValue(file, InventoryData.class);

        // Create an InventoryManager
        InventoryManager inventoryManager = new InventoryManager();

        // Create root category and load its data
        Category rootCategory = new Category(inventoryData.getRootCategory().getName());

        // Recursively load the categories and their subcategories
        loadCategory(rootCategory, inventoryData.getRootCategory());

        // Set the root category in the InventoryManager
        inventoryManager.setRootCategory(rootCategory);

        return inventoryManager;
    }

    public static void saveToFile(InventoryManager inventoryManager, String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InventoryData inventoryData = new InventoryData();

        // Convert InventoryManager to InventoryData for saving
        Category rootCategory = inventoryManager.getRootCategory();
        inventoryData.setRootCategory(convertCategoryToData(rootCategory));

        // Write data to file
        objectMapper.writeValue(new File(fileName), inventoryData);
    }

private static void loadCategory(Category category, CategoryData categoryData) {
    // Load subcategories recursively
    for (CategoryData subCategoryData : categoryData.getSubCategories()) {
        Category subCategory = new Category(subCategoryData.getName());
        category.addSubCategory(subCategory);  // Add the subcategory to the parent category
        loadCategory(subCategory, subCategoryData);  // Recursively load subcategories
    }

    // Load items for the category
    for (ItemData itemData : categoryData.getItems()) {
        // Create an empty map for custom attributes (or populate with values if necessary)
        Map<String, String> customAttributes = new HashMap<>();

        // Pass customAttributes as the fourth argument when creating the Item
        Item item = new Item(itemData.getName(), itemData.getDescription(), itemData.getAmount(), customAttributes);
        category.addItem(item);  // Add the item to the category
    }
}

    private static CategoryData convertCategoryToData(Category category) {
        CategoryData categoryData = new CategoryData();
        categoryData.setName(category.getName());
        categoryData.setSubCategories(new java.util.ArrayList<>());
        categoryData.setItems(new java.util.ArrayList<>());

        // Add subcategories recursively
        for (Category subCategory : category.getSubCategories()) {
            categoryData.getSubCategories().add(convertCategoryToData(subCategory));
        }

        // Add items (if any)
        for (Item item : category.getItems()) {
            ItemData itemData = new ItemData();
            itemData.setName(item.getName());
            itemData.setDescription(item.getDescription());
            itemData.setAmount(item.getAmount());
            categoryData.getItems().add(itemData);
        }

        return categoryData;
    }

    // Helper classes to map the JSON structure
    private static class InventoryData {
        private CategoryData rootCategory;

        public CategoryData getRootCategory() {
            return rootCategory;
        }

        public void setRootCategory(CategoryData rootCategory) {
            this.rootCategory = rootCategory;
        }
    }

    private static class CategoryData {
        private String name;
        private java.util.List<CategoryData> subCategories;
        private java.util.List<ItemData> items;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public java.util.List<CategoryData> getSubCategories() {
            return subCategories;
        }

        public void setSubCategories(java.util.List<CategoryData> subCategories) {
            this.subCategories = subCategories;
        }

        public java.util.List<ItemData> getItems() {
            return items;
        }

        public void setItems(java.util.List<ItemData> items) {
            this.items = items;
        }
    }

    private static class ItemData {
        private String name;
        private String description;
        private int amount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}