package edu.uca.final_project.GUI;

import edu.uca.final_project.XMLExample.TableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Objects;

public class TablePanel extends JPanel {
    JTable table;
    JPanel filterPanel = new JPanel();
    TableModel model;
    JScrollPane scrollPane;
    TableRowSorter<TableModel> sorter;
    JTextField filterField;
    JComboBox<Object> filterComboBox;
    JButton addRowButton;
    JButton addColumnButton;
    DetailsPanel detailsPanel;

    public TablePanel(TableModel model, DetailsPanel detailsPanel) {
        System.out.println("Inside TablePanel");
        this.model = model;
        this.detailsPanel = detailsPanel;
//        setPreferredSize(new Dimension(1000, 400));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


        table = new JTable(){
            {
                setModel(model);
                getTableHeader().setReorderingAllowed(false);
                setRowSorter(sorter = new TableRowSorter<>(model));
                setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                getSelectionModel().addListSelectionListener(new RowListener());
            }
        };

        filterField = new JTextField(){
            {
                setPreferredSize(new Dimension(300,20));
                getDocument().addDocumentListener(new DocumentListener() {
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
            }
        };

        filterComboBox = new JComboBox<>(model.getColumnData(1))
        {
            {
                setPreferredSize(new Dimension(300,20));
                setSelectedIndex(0);
                addActionListener(_ -> updateFilter());
            }
        };

        filterPanel.add(new JLabel("Country Filter:"));
        filterPanel.add(filterField);
        filterPanel.add(new JLabel("Series Filter:"));
        filterPanel.add(filterComboBox);
        filterPanel.add(new JLabel("     Click on a row to update Graph"));

        addRowButton = new JButton() {
                {
                    setText("Add Row");
                }
            };

            addColumnButton = new JButton() {
                {
                    setText("Add Column");
                }
            };

//        addButton.addActionListener(event -> model.addColumn(event.getActionCommand(), new Object[] {"Row1", "Row2", "Row3"}));
//        removeButton.addActionListener(_ -> model.removeColumn(1));
//        addAttributeButton.addActionListener(_-> {
//            AttributeModal modal = new AttributeModal(model);
//            System.out.println(title);
//            System.out.println(location);
//        });

        addColumnButton.addActionListener(event -> {
            // Add a new column to the table model with default data
            model.addColumn("New Column", new Object[]{"0", "0", "0", "0"});
            });

        addRowButton.addActionListener(event -> {
            // Add a new row to the table model with default data
            model.addRow(new Object[]{"0", "0", "0", "0"});
            });

        filterPanel.add(addColumnButton);
        filterPanel.add(addRowButton);

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
//        RowFilter<javax.swing.table.TableModel, Integer> rf = switch (filterComboBox.getSelectedIndex()) {
//            case 0 -> RowFilter.regexFilter("Gross", 1);
//            case 1 -> RowFilter.regexFilter("GDP growth", 1);
//            case 2 -> RowFilter.regexFilter("GDP per", 1);
//            case 3 -> RowFilter.regexFilter("Inflation GDP", 1);
//            case 4 -> RowFilter.regexFilter("Inflation consumer prices", 1);
//            case 5 -> RowFilter.regexFilter("Inflation GDP growth", 1);
//            default -> null;
//        };
        String[] array = model.getColumnData(1);
        RowFilter<javax.swing.table.TableModel, Integer> rf = RowFilter.regexFilter(Objects.requireNonNull(filterComboBox.getSelectedItem()).toString(), 1);
        sorter.setRowFilter(rf);
    }

    private void updateDetailsPanel(int modelRow) {
            String details = getRowDetails(modelRow);
           detailsPanel.updateDetails(details);  // Use the passed DetailsPanel to update details
        }

        private String getRowDetails(int modelRow) {
            StringBuilder details = new StringBuilder();

            // Iterate through all columns for the selected row
            for (int col = 0; col < model.getColumnCount(); col++) {
                // Get column name and corresponding value in the selected row
                Object columnName = model.getColumnName(col);  // Get the name of the column
                Object value = model.getValueAt(modelRow, col);  // Get the value at the specific row and column
                details.append(columnName).append(": ").append(value).append("\n");
            }

            // Return the built details string
            return details.toString();
        }


        private class RowListener implements ListSelectionListener {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (event.getValueIsAdjusting()) return;
                // When a row is selected, update the details panel
                int[] selectedRows = table.getSelectedRows();
                for (int row : selectedRows) {
                    int modelRow = table.convertRowIndexToModel(row);
                    if (modelRow >= 0 && modelRow < model.getRowCount()) { // Prevent out-of-bounds exception
                        updateDetailsPanel(modelRow);  // Pass the model row to the details panel
                    }
                }
            }
        }
}
