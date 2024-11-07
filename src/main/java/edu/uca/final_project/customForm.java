package edu.uca.final_project;

import javax.swing.*;
import java.awt.*;

public class customForm extends JFrame {
    private JPanel contentPane;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JCheckBox checkBox1;
    private JButton button1;

    public customForm() {
        setTitle("Custom Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
