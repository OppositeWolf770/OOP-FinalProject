package edu.uca.final_project.GUI;

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {
    private JTextArea detailsTextArea;
    private JScrollPane scrollPane;

    public DetailsPanel() {
        // Create a new JTextArea with a larger initial size for better visibility
        detailsTextArea = new JTextArea(10, 30);  // Set rows and columns for size
        detailsTextArea.setEditable(false);  // Make it non-editable
        detailsTextArea.setWrapStyleWord(true);  // Allow word wrapping for better readability
        detailsTextArea.setLineWrap(true);  // Wrap text at word boundaries

        scrollPane = new JScrollPane(detailsTextArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // Always show vertical scrollbar
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrollbar
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);  // Add the scroll pane to the center of the panel
    }

    // Method to update the details in the text area
    public void updateDetails(String details) {
        detailsTextArea.setText(details);  // Set the new details into the JTextArea
    }
}
