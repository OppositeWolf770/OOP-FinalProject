package edu.uca.final_project.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uca.final_project.model.Category;
import edu.uca.final_project.model.InventoryManager;
import edu.uca.final_project.model.Item;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONPersistence {

    public static InventoryManager loadFromFile(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(fileName);

        // Read JSON file and map it to an InventoryManager
        InventoryData inventoryData = objectMapper.readValue(file, InventoryData.class);

        // Create an InventoryManager and populate it
        InventoryManager inventoryManager = new InventoryManager();
        Category rootCategory = new Category(inventoryData.getRootCategory().getName());
        loadCategory(rootCategory, inventoryData.getRootCategory());

        inventoryManager.setRootCategory(rootCategory);
        return inventoryManager;
    }

    private static void loadCategory(Category category, CategoryData categoryData) {
        // Add items to category
        for (ItemData itemData : categoryData.getItems()) {
            Item item = new Item(itemData.getName(), itemData.getDescription(), itemData.getAmount(), null);
            category.addItem(item);
        }

        // Add subcategories
        for (CategoryData subCategoryData : categoryData.getSubCategories()) {
            Category subCategory = new Category(subCategoryData.getName());
            loadCategory(subCategory, subCategoryData);
            category.addSubCategory(subCategory);
        }
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
        private List<CategoryData> subCategories;
        private List<ItemData> items;

        public String getName() {
            return name;
        }

        public List<CategoryData> getSubCategories() {
            return subCategories;
        }

        public List<ItemData> getItems() {
            return items;
        }
    }

    private static class ItemData {
        private String name;
        private String description;
        private int amount;

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getAmount() {
            return amount;
        }
    }
}