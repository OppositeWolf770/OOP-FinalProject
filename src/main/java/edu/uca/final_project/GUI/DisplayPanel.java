package edu.uca.final_project.GUI;

import edu.uca.final_project.XMLExample.TableModel;

import javax.swing.*;

public class DisplayPanel extends JPanel {
    private JButton addButton = new JButton("Add a column");
    private JButton printButton = new JButton("Print to Console");
    private JButton removeButton = new JButton("Remove a column");
    private JButton addAttributeButton = new JButton("Add an attribute");

    public DisplayPanel(TableModel model) {
        System.out.println("Inside DisplayPanel");
        add(new TablePanel(model));
        setVisible(true);
    }
}
