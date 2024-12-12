package edu.uca.final_project.model;

import java.util.Map;

public class Item {
    private String name;
    private String description;
    private int amount;
    private Map<String, String> customAttributes; // User-defined attributes

    // Constructor
    public Item(String name, String description, int amount, Map<String, String> customAttributes) {
        setName(name);
        setDescription(description);
        setAmount(amount);
        setCustomAttributes(customAttributes);
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public Map<String, String> getCustomAttributes() { return customAttributes; }
    public void setCustomAttributes(Map<String, String> customAttributes) { this.customAttributes = customAttributes; }
}
