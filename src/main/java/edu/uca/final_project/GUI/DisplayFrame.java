package edu.uca.final_project.GUI;

import edu.uca.final_project.XMLExample.TableModel;

import javax.swing.*;
import java.awt.*;

public class DisplayFrame extends JFrame {
    public DisplayFrame(TableModel model) {
        System.out.println("Inside DisplayFrame");
        setTitle("Data Frame");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new DisplayPanel(model));

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
