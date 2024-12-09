package edu.uca.final_project.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigurationPanel {
    private JPanel contentPane;
    private JTextField addCategoryTextField;
    private JButton addCategoryButton;
    private JTable categoriesTable;
    private JScrollPane tableScrollPane;
    private JList list1;

    public ConfigurationPanel() {
        DefaultTableModel tableModel = new DefaultTableModel(new Object[] {"Category", "Subcategories"}, 0);
        categoriesTable.setModel(tableModel);

        addCategoryButton.addActionListener(_ -> {
            String category = addCategoryTextField.getText();
            if (!category.isEmpty()) {
                tableModel.addRow(new Object[] { category });
                addCategoryTextField.setText("");

                categoriesTable.revalidate();
                categoriesTable.repaint();
            }
        });
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
