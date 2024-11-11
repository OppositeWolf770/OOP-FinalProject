package edu.uca.final_project.GUI;

import edu.uca.final_project.XMLExample.TableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class TablePanel extends JPanel {
    JTable table;
    JPanel filterPanel = new JPanel();
    TableModel model;
    JScrollPane scrollPane;
    TableRowSorter<TableModel> sorter;
    JTextField filterField;
    JComboBox<Object> filterComboBox;
    JButton addButton;

    public TablePanel(TableModel model) {
        System.out.println("Inside TablePanel");
        this.model = model;
//        setPreferredSize(new Dimension(1000, 400));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


        sorter = new TableRowSorter<>(model);
        table = new JTable();
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowSorter(sorter);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getSelectionModel().addListSelectionListener(new RowListener());

        filterField = new JTextField();
        filterField.setPreferredSize(new Dimension(300,20));
        filterField.getDocument().addDocumentListener(new DocumentListener() {
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

        filterComboBox = new JComboBox<>(new Object[]{"GDP","GDP growth","GDP per capita","Inflation GDP deflator","Inflation consumer prices"});
        filterComboBox.setPreferredSize(new Dimension(300,20));
        filterComboBox.setSelectedIndex(0);
        filterComboBox.addActionListener(_ -> updateFilter());

        filterPanel.add(new JLabel("Country Filter:"));
        filterPanel.add(filterField);
        filterPanel.add(new JLabel("Series Filter:"));
        filterPanel.add(filterComboBox);
        filterPanel.add(new JLabel("     Click on a row to update Graph"));

        addButton = new JButton(){
            {
                setText("Add");
            }
        };

        addButton.addActionListener(event -> model.addColumn(event.getActionCommand(), new Object[] {"Row1", "Row2", "Row3"}));
//        removeButton.addActionListener(_ -> model.removeColumn(1));
//        addAttributeButton.addActionListener(_-> {
//            AttributeModal modal = new AttributeModal(model);
//            System.out.println(title);
//            System.out.println(location);
//        });

        filterPanel.add(addButton);

        scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(780, 400));

        add(scrollPane, BorderLayout.CENTER);
        add(filterPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void textFilter() {
        RowFilter<javax.swing.table.TableModel, Integer> rf;
        try {
            rf = RowFilter.regexFilter(filterField.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    protected void updateFilter() {
        RowFilter<javax.swing.table.TableModel, Integer> rf = switch (filterComboBox.getSelectedIndex()) {
            case 0 -> RowFilter.regexFilter("Gross", 1);
            case 1 -> RowFilter.regexFilter("GDP growth", 1);
            case 2 -> RowFilter.regexFilter("GDP per", 1);
            case 3 -> RowFilter.regexFilter("Inflation GDP", 1);
            case 4 -> RowFilter.regexFilter("Inflation consumer prices", 1);
            case 5 -> RowFilter.regexFilter("Inflation GDP growth", 1);
            default -> null;
        };
        sorter.setRowFilter(rf);
    }

    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            for (int c : table.getSelectedRows()) {
                int modelRow = table.convertRowIndexToModel(c);
            }
        }
    }
}
