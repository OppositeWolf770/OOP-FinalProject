package edu.uca.final_project;

import edu.uca.final_project.model.InventoryManager;
import edu.uca.final_project.persistence.JSONPersistence;
import edu.uca.final_project.ui.MainWindow;
import javax.swing.*;
import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        InventoryManager manager = null;
        String fileName = "inventory.json";

        // Try loading data from a file, else initialize a new InventoryManager
        try {
            manager = JSONPersistence.loadFromFile(fileName);
        } catch (IOException e) {
            manager = new InventoryManager(); // If file doesn't exist, create a new one
        }

        // Initialize main window
        InventoryManager finalManager = manager;
        SwingUtilities.invokeLater(() -> {
            new MainWindow(finalManager);
        });
    }
}