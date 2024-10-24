package edu.uca.final_project;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import edu.uca.final_project.XMLExample.Library;
import edu.uca.final_project.XMLExample.TableModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {
    public static XmlMapper xmlMapper = new XmlMapper();
    public static StockItems items;
    public static File file;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JButton button = new JButton("Test") {
            {
                setSize(new Dimension(10, 10));
            }
        };
        JButton printbutton = new JButton("Print to Console") {
            {
                setSize(new Dimension(20, 10));
            }
        };

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setLocationRelativeTo(null);
        frame.setSize(new Dimension(800, 600));



        // Load the file from the src
        file = new File("test.xml");

        // Attempt to
        try {
            items = loadXml(file);
        } catch (IOException e) {
            items = createNewFile();

            return;
        }

        TableModel model = new TableModel() {
            {
                addColumn("Corban Sucks", new Object[] {"Row1", "Row2", "Row3"});
                addColumn("Book", new Object[] {"Row4", "Row5", "Row6"});
            }
        };

        JTable testtable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(testtable);
        testtable.setAutoCreateColumnsFromModel(true);

        frame.add(scrollPane);

        frame.add(button);
        frame.add(printbutton);
        frame.setVisible(true);


        button.addActionListener(event -> {
            model.addColumn(event.getActionCommand(), new Object[] {"Row1", "Row2", "Row3"});
        });
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

    // Creates a new StockItems file if one is not found
    private static StockItems createNewFile() {
        return new StockItems();
    }


    // Read data from an XML file
    public static StockItems loadXml(File file) throws IOException {
        try {
            return xmlMapper.readValue(file, StockItems.class);
        } catch (IOException e) {
            return new StockItems();
        }
    }

    public static void saveXml(File file, Library library) {
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            xmlMapper.writeValue(file, library);
        } catch (IOException e) {
            System.out.println("Unable to write to file " + file + ".");
        }
    }
}
