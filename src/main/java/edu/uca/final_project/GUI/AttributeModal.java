package edu.uca.final_project.GUI;

import edu.uca.final_project.XMLExample.TableModel;
import edu.uca.final_project.Logic.Main;

import javax.swing.*;
import java.awt.*;

public class AttributeModal{
    JDialog dialog;
    JTextField title;
    JComboBox location;
    JButton confirm;

    public AttributeModal(TableModel model){
        dialog = new JDialog();
        title = new JTextField("",10);
        confirm = new JButton("Confirm");

        String[] locations = new String[model.getColumnCount()];

        for (int i = 0; i < model.getColumnCount(); i++){
            locations[i] = Integer.toString(i);
        }

        for (int i = 0; i < locations.length; i++){
            System.out.println(locations[i]);
        }


        location = new JComboBox(locations);

        dialog.setModal(true);
        dialog.setTitle("Attribute Modal");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());


        dialog.add(new JLabel("Set Attribute Title: "));
        dialog.add(title);
        dialog.add(new JLabel("Set Attribute Location: "));
        dialog.add(location);
        dialog.add(confirm);

        confirm.addActionListener(_ -> {
            Main.title = title.getText();
            Main.location = 2;
            dialog.dispose();
        });

        dialog.setPreferredSize(new Dimension(450, 105));
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
