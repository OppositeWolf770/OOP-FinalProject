package edu.uca.final_project.ui;

import edu.uca.final_project.model.Category;
import edu.uca.final_project.model.InventoryManager;
import edu.uca.final_project.model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private InventoryManager inventoryManager;
    private JPanel contentPanel;

    public MainWindow(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical layout

        // Setup window
        setTitle("Inventory Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Start with displaying root categories
        displayCategories(inventoryManager.getRootCategory());

        // Add contentPanel to the frame
        this.add(new JScrollPane(contentPanel)); // Wrap contentPanel in JScrollPane for scroll functionality
    }

    private void displayCategories(Category category) {
        // Clear current contentPanel to add new category items
        contentPanel.removeAll();

        // Add category name as a label
        JLabel categoryLabel = new JLabel("Category: " + category.getName());
        contentPanel.add(categoryLabel);

        // Add a button for each subcategory
        for (Category subCategory : category.getSubCategories()) {
            JButton subCategoryButton = new JButton(subCategory.getName());
            subCategoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayCategories(subCategory); // Show subcategory content
                }
            });
            contentPanel.add(subCategoryButton);
        }

        // Display items in the current category
        for (Item item : category.getItems()) {
            JLabel itemLabel = new JLabel(item.getName() + " - " + item.getAmount() + " units");
            contentPanel.add(itemLabel);
        }

        // Back button to return to the root category or previous category
        if (category != inventoryManager.getRootCategory()) {
            JButton backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayCategories(inventoryManager.getRootCategory()); // Go back to root category
                }
            });
            contentPanel.add(backButton);
        }

        // Revalidate and repaint to update the UI with new content
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}