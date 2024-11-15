package edu.uca.final_project.GUI;

import edu.uca.final_project.XMLExample.TableModel;

import javax.swing.*;

public class DisplayPanel extends JPanel {

    public DisplayPanel(TableModel model) {
        System.out.println("Inside DisplayPanel");

        DetailsPanel detailsPanel = new DetailsPanel();
        TablePanel tablePanel = new TablePanel(model, detailsPanel);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //Places the detailsPanel below the table
        add(tablePanel); // Add TablePanel to DisplayPanel
        add(detailsPanel); // Add DetailsPanel to DisplayPanel
//        add(new TablePanel(model, detailsPanel));
//        setVisible(true);
    }
}
