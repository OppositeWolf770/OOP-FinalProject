package edu.uca.final_project.ui;

import edu.uca.final_project.model.Category;
import edu.uca.final_project.model.InventoryManager;
import edu.uca.final_project.model.Item;
import edu.uca.final_project.persistence.JsonIOManager;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends JFrame {
    private final InventoryManager inventoryManager;
    private JTable itemsTable;
    private TableRowSorter<TableModel> sorter;
    private JTextField filterField;
    private DefaultTableModel tableModel;
    RowFilter<TableModel, Integer> textFilter;
    private JTree categoryTree;
    private Category currentCategory;

    public MainWindow(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
        setTitle("Inventory Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        initializeUI();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Left panel: Category tree
        JPanel leftPanel = new JPanel(new BorderLayout());
        categoryTree = new JTree();
        categoryTree.setToggleClickCount(0);
        populateCategoryTree();
        categoryTree.addTreeSelectionListener(_ -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) categoryTree.getLastSelectedPathComponent();
            if (selectedNode != null && !categoryTree.isRowSelected(0)) {
                currentCategory = (Category) selectedNode.getUserObject();
                displayCategory(currentCategory);
            }
        });
        JScrollPane treeScrollPane = new JScrollPane(categoryTree);
        leftPanel.add(treeScrollPane, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);

        // Center panel: Items table
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));


        filterField = new JTextField(26) {
            {
                setBorder(new LineBorder(Color.GRAY));
                setPreferredSize(new Dimension(300,20));
                getDocument().addDocumentListener(new DocumentListener() { // Calls textFilter() whenever there is an update in the filterField
                    public void insertUpdate(DocumentEvent e) {
                        textFilter();
                    }
                    public void removeUpdate(DocumentEvent e) {
                        textFilter();
                    }
                    public void changedUpdate(DocumentEvent e) {
                        textFilter();
                    }
                });
                setToolTipText("Search the name, description, or amount of a stock item");
            }
        };

        tableModel = new DefaultTableModel(new String[]{"Name", "Description", "Amount", "Custom Attributes"}, 0);
        itemsTable = new JTable(tableModel);
        itemsTable.getTableHeader().setReorderingAllowed(false);

        sorter = new TableRowSorter<>(tableModel);
        itemsTable.setRowSorter(sorter);

        JScrollPane tableScrollPane = new JScrollPane(itemsTable);
        filterPanel.add(new JLabel("Search:"));
        filterPanel.add(filterField, BorderLayout.CENTER);
        centerPanel.add(filterPanel, BorderLayout.NORTH);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel: Add Item Button, Delete Item Button, and Edit Item Button
        JPanel bottomPanel = getBottomPanel();

        add(bottomPanel, BorderLayout.SOUTH);

        // Populate table with root category items by default
        currentCategory = inventoryManager.getRootCategory();
        displayCategory(currentCategory);
    }

    private JPanel getBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));


        // Edit Item Button
        JButton editItemButton = new JButton("Edit Item");
        editItemButton.addActionListener(_ -> editSelectedItem());
        bottomPanel.add(editItemButton);

        // Add Item Button
        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(_ -> addItemToCurrentCategory());
        bottomPanel.add(addItemButton);

        // Delete Item Button
        JButton deleteItemButton = new JButton("Delete Item");
        deleteItemButton.addActionListener(_ -> deleteSelectedItem());
        bottomPanel.add(deleteItemButton);

        // Spacer
        bottomPanel.add(Box.createHorizontalGlue());

        // Add Category Button
        JButton addCategoryButton = new JButton("Add Category");
        addCategoryButton.addActionListener(_ -> addCategoryToCurrentCategory());
        bottomPanel.add(addCategoryButton);

        // Delete All Inventory Button
        JButton deleteAllInventoryButton = getAllInventoryButton();
        bottomPanel.add(deleteAllInventoryButton);
        return bottomPanel;
    }

    private JButton getAllInventoryButton() {
        JButton deleteAllInventoryButton = new JButton("Delete All Inventory");
        deleteAllInventoryButton.addActionListener(_ -> {
            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete all inventory? This action cannot be undone.",
                    "Confirm Delete All Inventory",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                deleteInventoryFile();  // Proceed to delete the file if confirmed
            }
        });
        return deleteAllInventoryButton;
    }

    private void textFilter() {
        try {
            textFilter = RowFilter.regexFilter("(?i)" + filterField.getText()); // "(?i)" makes the filter case-insensitive
        } catch (java.util.regex.PatternSyntaxException e) { // Exits the method if the exception is thrown
            return;
        }
        sorter.setRowFilter(textFilter);
    }

    private Item findItemByName(String name) {
        for (Item item : currentCategory.getItems()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    protected ImageIcon createImageIcon(String path,
                                        String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void editSelectedItem() {
        int selectedRow = itemsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String itemName = (String) tableModel.getValueAt(selectedRow, 0); // Get item name
        Item itemToEdit = findItemByName(itemName);

        if (itemToEdit != null) {
            // Open edit item window
            new EditItemWindow(this, itemToEdit);
        } else {
            JOptionPane.showMessageDialog(this, "Item not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    class EditItemWindow extends JDialog {
        private final Item itemToEdit;

        public EditItemWindow(JFrame parent, Item item) {
            super(parent, "Edit Item", true);
            this.itemToEdit = item;

            setSize(400, 300);
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);  // Add space between components
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JTextField nameField = new JTextField(item.getName());
            JTextField descriptionField = new JTextField(item.getDescription());
            JTextField amountField = new JTextField(String.valueOf(item.getAmount()));
            JTextArea customAttributesArea = new JTextArea(formatCustomAttributesForEdit(item.getCustomAttributes()));
            customAttributesArea.setLineWrap(true);
            customAttributesArea.setWrapStyleWord(true);
            JScrollPane customAttributesScrollPane = new JScrollPane(customAttributesArea);

            // Name field
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            add(new JLabel("Name:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            add(nameField, gbc);

            // Description field
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(new JLabel("Description:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            add(descriptionField, gbc);

            // Amount field
            gbc.gridx = 0;
            gbc.gridy = 2;
            add(new JLabel("Amount:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 2;
            add(amountField, gbc);

            // Custom attributes text area
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridheight = 2;
            add(new JLabel("Custom Attributes (key=value, one per line):"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 3;
            gbc.gridheight = 2;
            gbc.fill = GridBagConstraints.BOTH;
            add(customAttributesScrollPane, gbc);

            // Save button
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            JButton saveButton = new JButton("Save Changes");
            saveButton.addActionListener(_ -> {
                String name = nameField.getText().trim();
                String description = descriptionField.getText().trim();
                int amount;
                try {
                    amount = Integer.parseInt(amountField.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Amount must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Map<String, String> customAttributes = new HashMap<>();
                String[] lines = customAttributesArea.getText().split("\n");
                for (String line : lines) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        customAttributes.put(parts[0].trim(), parts[1].trim());
                    }
                }

                itemToEdit.setName(name);
                itemToEdit.setDescription(description);
                itemToEdit.setAmount(amount);
                itemToEdit.setCustomAttributes(customAttributes);

                // Save the updated inventory to the file
                try {
                    JsonIOManager.saveToFile(inventoryManager, "inventory.json");
                    ((MainWindow) parent).displayCategory(((MainWindow) parent).currentCategory); // Refresh the table
                    dispose(); // Close the edit window
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to save inventory to file.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });
            add(saveButton, gbc);

            setVisible(true);
        }

        private String formatCustomAttributesForEdit(Map<String, String> customAttributes) {
            StringBuilder formatted = new StringBuilder();
            if (customAttributes != null && !customAttributes.isEmpty()) {
                for (Map.Entry<String, String> entry : customAttributes.entrySet()) {
                    formatted.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
                }
            }
            return formatted.toString();
        }
    }

    private void populateCategoryTree() {
        Category rootCategory = inventoryManager.getRootCategory();
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Categories"); // Use a generic name for root node
        addSubcategoriesToTree(rootNode, rootCategory);  // Add only subcategories to the tree
        categoryTree.setModel(new DefaultTreeModel(rootNode));
        for (int i = 0; i < categoryTree.getRowCount(); i++) {
            categoryTree.expandRow(i);
        }
    }

    private void addSubcategoriesToTree(DefaultMutableTreeNode parentNode, Category parentCategory) {
        for (Category subCategory : parentCategory.getSubCategories()) {
            DefaultMutableTreeNode subCategoryNode = new DefaultMutableTreeNode(subCategory);
            parentNode.add(subCategoryNode);
            addSubcategoriesToTree(subCategoryNode, subCategory);
        }
    }

    private void displayCategory(Category category) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Item item : category.getItems()) {
            tableModel.addRow(new Object[] {
                item.getName(),
                item.getDescription(),
                item.getAmount(),
                formatCustomAttributes(item.getCustomAttributes())
            });
        }
    }

    private String formatCustomAttributes(Map<String, String> customAttributes) {
        if (customAttributes == null || customAttributes.isEmpty()) {
            return "None";
        }
        StringBuilder formatted = new StringBuilder();
        for (Map.Entry<String, String> entry : customAttributes.entrySet()) {
            formatted.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }
        return formatted.substring(0, formatted.length() - 2); // Remove trailing comma and space
    }

    private void addCategoryToCurrentCategory() {
        if (currentCategory == null) {
            JOptionPane.showMessageDialog(this, "Please select a category first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nameField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Add space between components

        // Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        // Description field
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(descriptionField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Category", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String description = descriptionField.getText().trim();

            // Add the new category to the current category
            Category newCategory = new Category(name, description);
            currentCategory.addSubCategory(newCategory);

            // Save the updated inventory to the file
            try {
                JsonIOManager.saveToFile(inventoryManager, "inventory.json");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Failed to save inventory to file.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

            // Refresh the category tree to show the new category
            populateCategoryTree();
        }
    }

    private void addItemToCurrentCategory() {
        if (currentCategory == null) {
            JOptionPane.showMessageDialog(this, "Please select a category first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nameField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);
        JTextField amountField = new JTextField(20);
        JTextArea customAttributesArea = new JTextArea(5, 20);
        customAttributesArea.setLineWrap(true);
        customAttributesArea.setWrapStyleWord(true);
        JScrollPane customAttributesScrollPane = new JScrollPane(customAttributesArea);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Add space between components

        // Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        // Description field
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(descriptionField, gbc);

        // Amount field
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Amount:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(amountField, gbc);

        // Custom attributes text area
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Custom Attributes (key=value, one per line):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(customAttributesScrollPane, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String description = descriptionField.getText().trim();
            int amount;
            try {
                amount = Integer.parseInt(amountField.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Amount must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Map<String, String> customAttributes = new HashMap<>();
            String[] lines = customAttributesArea.getText().split("\n");
            for (String line : lines) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    customAttributes.put(parts[0].trim(), parts[1].trim());
                }
            }

            // Add the item to the current category
            Item newItem = new Item(name, description, amount, customAttributes);
            currentCategory.addItem(newItem);

            // Save the updated inventory to the file
            try {
                JsonIOManager.saveToFile(inventoryManager, "inventory.json");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Failed to save inventory to file.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

            // Refresh the table to show the new item
            displayCategory(currentCategory);
        }
    }

    private void deleteSelectedItem() {
        int selectedRow = itemsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String itemName = (String) tableModel.getValueAt(selectedRow, 0); // Get item name
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the item: " + itemName + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            // Remove the item from the current category
            Item itemToDelete = null;
            for (Item item : currentCategory.getItems()) {
                if (item.getName().equals(itemName)) {
                    itemToDelete = item;
                    break;
                }
            }

            if (itemToDelete != null) {
                currentCategory.removeItem(itemToDelete);

                // Save the updated inventory to the file
                try {
                    JsonIOManager.saveToFile(inventoryManager, "inventory.json");
                    displayCategory(currentCategory); // Refresh the table
                    JOptionPane.showMessageDialog(this, "Item deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Failed to save inventory to file.", "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        }
    }

    // Deletes the inventory file and allows the user to start a new file
    private void deleteInventoryFile() {
        String fileName = "inventory.json";  // Or use the file name you're using
        File file = new File(fileName);

        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                JOptionPane.showMessageDialog(this, "Inventory file deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                returnToConfigurationScreen();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete the inventory file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No inventory file found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Used when deleting the entire inventory file to go back to the main configuration window
    private void returnToConfigurationScreen() {
        // Stop the current window
        this.dispose();

        // Return to the configuration screen
        SwingUtilities.invokeLater(() -> new ConfigurationScreen(new InventoryManager(), "inventory.json"));
    }
}
