package edu.uca.final_project.ui;

import edu.uca.final_project.model.Category;
import javax.swing.*;
import java.awt.*;

public class CategoryView extends JPanel {
    private Category category;

    public CategoryView(Category category) {
        this.category = category;
        this.setLayout(new BorderLayout());

        // Add category name and item list
        JLabel categoryLabel = new JLabel("Category: " + category.getName());
        this.add(categoryLabel, BorderLayout.NORTH);

        JList<String> itemList = new JList<>(category.getItems().stream()
                .map(item -> item.getName()).toArray(String[]::new));
        this.add(new JScrollPane(itemList), BorderLayout.CENTER);
    }
}
