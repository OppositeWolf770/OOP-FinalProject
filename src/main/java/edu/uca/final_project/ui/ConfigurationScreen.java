package edu.uca.final_project.ui;

import javax.swing.*;
import java.awt.*;

public class ConfigurationScreen extends JPanel {

    public ConfigurationScreen() {
        this.setLayout(new BorderLayout());

        // Setup UI components for category configuration
        JLabel label = new JLabel("Configure your categories:");
        this.add(label, BorderLayout.NORTH);

        // Add buttons and inputs for categories/subcategories
        JButton createCategoryButton = new JButton("Create Category");
        this.add(createCategoryButton, BorderLayout.SOUTH);
    }
}
