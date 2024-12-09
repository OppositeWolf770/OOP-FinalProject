package edu.uca.final_project.ui;

import edu.uca.final_project.model.InventoryManager;
import edu.uca.final_project.model.Category;
import edu.uca.final_project.persistence.JSONPersistence;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConfigurationScreen extends JFrame {

    private InventoryManager inventoryManager;
    private JTextField categoryNameField;
    private DefaultListModel<String> subcategoriesListModel;
    private JList<String> subcategoriesList;
    private JTextField subcategoryNameField;
    private String fileName;

    public ConfigurationScreen(InventoryManager inventoryManager, String fileName) {
        this.inventoryManager = inventoryManager;
        this.fileName = fileName;

        // Setup window
        setTitle("Configure Categories");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Category name input
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new FlowLayout());
        categoryPanel.add(new JLabel("Category Name:"));
        categoryNameField = new JTextField(20);
        categoryPanel.add(categoryNameField);
        add(categoryPanel, BorderLayout.NORTH);

        // Subcategory input
        JPanel subcategoryPanel = new JPanel();
        subcategoryPanel.setLayout(new FlowLayout());
        subcategoryPanel.add(new JLabel("Subcategory Name:"));
        subcategoryNameField = new JTextField(15);
        subcategoryPanel.add(subcategoryNameField);
        JButton addSubcategoryButton = new JButton("Add Subcategory");
        subcategoryPanel.add(addSubcategoryButton);
        add(subcategoryPanel, BorderLayout.CENTER);

        // List of subcategories
        subcategoriesListModel = new DefaultListModel<>();
        subcategoriesList = new JList<>(subcategoriesListModel);
        JScrollPane scrollPane = new JScrollPane(subcategoriesList);
        add(scrollPane, BorderLayout.EAST);

        // Button to save and proceed
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save Category");
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add subcategory action
        addSubcategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subcategoryName = subcategoryNameField.getText();
                if (!subcategoryName.isEmpty()) {
                    subcategoriesListModel.addElement(subcategoryName);
                    subcategoryNameField.setText(""); // Clear the input
                }
            }
        });

        // Save category and subcategories
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoryName = categoryNameField.getText();
                if (!categoryName.isEmpty()) {
                    Category newCategory = new Category(categoryName);

                    // Add subcategories to the category
                    for (int i = 0; i < subcategoriesListModel.size(); i++) {
                        newCategory.addSubCategory(new Category(subcategoriesListModel.get(i)));
                    }

                    // Save the category and subcategories to the InventoryManager
                    inventoryManager.setRootCategory(newCategory);

                    // Save to JSON file
                    try {
                        JSONPersistence.saveToFile(inventoryManager, fileName);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error saving categories.");
                    }

                    // Close the configuration window
                    dispose();

                    // Launch the main window with the configured categories
                    SwingUtilities.invokeLater(() -> {
                        new MainWindow(inventoryManager);
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Category name cannot be empty.");
                }
            }
        });

        // Show the window
        setVisible(true);
    }
}
