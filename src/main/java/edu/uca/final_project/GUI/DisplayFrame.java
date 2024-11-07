package edu.uca.final_project.GUI;

import javax.swing.*;
import java.awt.*;

public class DisplayFrame extends JFrame {
    public DisplayFrame() {
        setTitle("Data Frame");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
