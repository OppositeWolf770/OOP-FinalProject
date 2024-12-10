package edu.uca.final_project;

import edu.uca.final_project.model.InventoryManager;
import edu.uca.final_project.persistence.JsonIOManager;
import edu.uca.final_project.ui.ConfigurationScreen;
import edu.uca.final_project.ui.MainWindow;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        String fileName = "inventory.json";
        File file = new File(fileName);

        SwingUtilities.invokeLater(() -> {
            InventoryManager manager;
            if (!file.exists()) {
                // If the file doesn't exist, create a new InventoryManager
                manager = new InventoryManager();
                ConfigurationScreen configurationScreen = new ConfigurationScreen(manager, fileName);
                configurationScreen.setVisible(true);
            } else {
                // If the file exists, load data from it
                try {
                    manager = JsonIOManager.loadFromFile(fileName);
                } catch (IOException e) {
                    System.err.println("Error loading inventory data: " + e.getMessage());
                    manager = new InventoryManager(); // Create a new manager if loading fails
                }

                // Show the main window
                MainWindow mainWindow = new MainWindow(manager);
                mainWindow.setVisible(true);
            }
        });
    }
}
