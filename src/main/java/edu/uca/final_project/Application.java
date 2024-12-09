package edu.uca.final_project;

import edu.uca.final_project.model.InventoryManager;
import edu.uca.final_project.persistence.JSONPersistence;
import edu.uca.final_project.ui.ConfigurationScreen;
import edu.uca.final_project.ui.MainWindow;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
        String fileName = "inventory.json";
        File file = new File(fileName);

        // If the file doesn't exist, show the configuration screen
        if (!file.exists()) {
            InventoryManager finalManager = manager;
            SwingUtilities.invokeLater(() -> {
                new ConfigurationScreen(finalManager, fileName); // Show the configuration screen
            });
        } else {
            // Try loading data from the file
            try {
                manager = JSONPersistence.loadFromFile(fileName);
            } catch (IOException e) {
                manager = new InventoryManager(); // If file loading fails, create a new manager
            }
            // Initialize main window
            InventoryManager finalManager = manager;
            SwingUtilities.invokeLater(() -> {
                new MainWindow(finalManager);
            });
        }
    }
}