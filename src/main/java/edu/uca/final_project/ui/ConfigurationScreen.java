package edu.uca.final_project.ui;

import edu.uca.final_project.model.InventoryManager;
import edu.uca.final_project.model.Category;
import edu.uca.final_project.persistence.JsonIOManager;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class ConfigurationScreen extends JFrame {
    private final DefaultListModel<String> categoriesListModel;
    private final JTextField categoryNameField;
    private final JTextArea categoryDescriptionField;

    public ConfigurationScreen(InventoryManager inventoryManager, String fileName) {

        // Setup window
        setTitle("Configure Categories");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Set the icon for configuration screen
        setIconImage(new ImageIcon(getClass().getResource("/wrench-regular-24.png")).getImage());

        // Set the root category name to "Root"
        Category rootCategory = new Category("Root", "");
        inventoryManager.setRootCategory(rootCategory);

        // Main Panel to hold all components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Category name input panel
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoryPanel.add(new JLabel("Category Name:"));
        categoryNameField = new JTextField(20);
        categoryPanel.add(categoryNameField);
        JButton addCategoryButton = new JButton("Add Category");
        categoryPanel.add(addCategoryButton);
        mainPanel.add(categoryPanel);

        // Category description input panel
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.add(new JLabel("Description:"), BorderLayout.NORTH);
        categoryDescriptionField = new JTextArea(2, 40);
        categoryDescriptionField.setBorder(new LineBorder(Color.GRAY));
        categoryDescriptionField.setFont(categoryNameField.getFont());
        descriptionPanel.add(categoryDescriptionField, BorderLayout.CENTER);
        mainPanel.add(descriptionPanel);

        // Subcategory list panel
        JPanel listPanel = new JPanel(new BorderLayout());
        categoriesListModel = new DefaultListModel<>();
        JList<String> categoriesList = new JList<>(categoriesListModel);
        categoriesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(categoriesList);
        listPanel.add(new JLabel("Main Categories:"), BorderLayout.NORTH);
        listPanel.add(listScrollPane, BorderLayout.CENTER);
        mainPanel.add(listPanel);

        // Add and save buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        JButton saveButton = new JButton("Save Categories");
        saveButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalGlue());
        mainPanel.add(buttonPanel);

        // Add main panel to the JFrame
        add(mainPanel, BorderLayout.CENTER);

        // Add category action (when button is pressed)
        addCategoryButton.addActionListener(_ -> {
            String categoryName = categoryNameField.getText().trim();
            if (!categoryName.isEmpty()) {
                // Check if the category name already exists in the list
                if (categoriesListModel.contains(categoryName)) {
                    JOptionPane.showMessageDialog(null, "This category already exists.", "Duplicate Category", JOptionPane.ERROR_MESSAGE);
                } else {
                    categoriesListModel.addElement(categoryName);
                    categoryNameField.setText(""); // Clear the input
                    categoryDescriptionField.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a category name.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add KeyListener to the category name field to handle Enter key press
        categoryNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addCategoryButton.doClick(); // Simulate button click
                }
            }
        });

        // Save categories
        saveButton.addActionListener(_ -> {
            // Add subcategories to the root category
            for (int i = 0; i < categoriesListModel.size(); i++) {
                String categoryName = categoriesListModel.get(i);
                rootCategory.addSubCategory(new Category(categoryName, ""));
            }

            // Save to JSON file
            try {
                JsonIOManager.saveToFile(inventoryManager, fileName);
                JOptionPane.showMessageDialog(null, "Categories saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving categories.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Close the configuration window
            dispose();

            // Launch the main window with the configured categories
            SwingUtilities.invokeLater(() -> new MainWindow(inventoryManager));
        });

        // Set the window visible
        setVisible(true);
    }
}
