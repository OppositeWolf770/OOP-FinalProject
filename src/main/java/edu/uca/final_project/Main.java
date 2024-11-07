package edu.uca.final_project;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import edu.uca.final_project.GUI.ConfigurationPanel;
import edu.uca.final_project.GUI.DisplayFrame;
import edu.uca.final_project.XMLExample.Library;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {
    public static XmlMapper xmlMapper = new XmlMapper();
    public static File file;

    public static int location;
    public static String title;

    public static void main(String[] args) {
        setLookAndFeel();

        var frame = new DisplayFrame();

        // Load the file from the src
        file = new File("test.xml");
        if (!file.exists()) {
            var configurationPanel = new ConfigurationPanel();

            frame.add(configurationPanel.getContentPane());
            frame.revalidate();
            frame.repaint();
        }

//        // Attempt to
//        try {
//            items = loadXml(file);
//        } catch (IOException e) {
//            items = createNewFile();
//
//            return;
//        }
//
//        TableModel model = new TableModel() {
//            {
//                addColumn("Corban Sucks", new Object[] {"Row1", "Row2", "Row3"});
//                addColumn("Book", new Object[] {"Row4", "Row5", "Row6"});
//            }
//        };
//
//        JTable testtable = new JTable(model);
//
//        JScrollPane scrollPane = new JScrollPane(testtable);
//        testtable.setAutoCreateColumnsFromModel(true);
//
//        sorter = new TableRowSorter<>(model);
//        testtable.setRowSorter(sorter);
//
//        filterField = new JTextField();
//        filterField.setPreferredSize(new Dimension(300,20));
//        filterField.getDocument().addDocumentListener(new DocumentListener() {
//            public void insertUpdate(DocumentEvent e) {
//                textFilter();
//            }
//            public void removeUpdate(DocumentEvent e) {
//                textFilter();
//            }
//            public void changedUpdate(DocumentEvent e) {
//                textFilter();
//            }
//        });
//
//        frame.add(scrollPane);

//        frame.add(addButton);
//        frame.add(removeButton);
//        frame.add(printbutton);
//        frame.add(addAttributeButton);
//        frame.add(filterField);
//
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//        addButton.addActionListener(event -> model.addColumn(event.getActionCommand(), new Object[] {"Row1", "Row2", "Row3"}));
//        printbutton.addActionListener(_ -> System.out.println(model));
//        removeButton.addActionListener(_ -> model.removeColumn(1));
//        addAttributeButton.addActionListener(_-> {
//            AttributeModal modal = new AttributeModal(model);
//            System.out.println(title);
//            System.out.println(location);
//        });
    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    // Returns the filename of an XML file if one is found. returns null otherwise
    public static String checkForXML(Path stockroomsPath, String extension) throws IOException {
        FileVisitor<Path> fileVisitor = new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.getFileName().toString().endsWith(extension)) {

                }
                return FileVisitResult.CONTINUE;
            }
        };

        Files.walkFileTree(stockroomsPath, fileVisitor);

        return null;
    }


//    // Read data from an XML file
//    public static StockItems loadXml(File file) throws IOException {
//        try {
//            return xmlMapper.readValue(file, StockItems.class);
//        } catch (IOException e) {
//            return new StockItems();
//        }
//    }

    public static void saveXml(File file, Library library) {
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            xmlMapper.writeValue(file, library);
        } catch (IOException e) {
            System.out.println("Unable to write to file " + file + ".");
        }
    }

//    private static void textFilter() {
//        RowFilter<javax.swing.table.TableModel, Integer> rf;
//        try {
//            rf = RowFilter.regexFilter(filterField.getText(), 0);
//        } catch (java.util.regex.PatternSyntaxException e) {
//            return;
//        }
//        sorter.setRowFilter(rf);
//    }
}
