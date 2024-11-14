package edu.uca.final_project.Logic;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import edu.uca.final_project.XMLExample.Library;
import edu.uca.final_project.XMLExample.TableModel;

import javax.swing.*;
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
//        setLookAndFeel();

//        var frame = new DisplayFrame();

//        // Load the file from the src
//        file = new File("test.xml");
//        if (!file.exists()) {
//            var configurationPanel = new ConfigurationPanel();
//
//            frame.add(configurationPanel.getContentPane());
//            frame.revalidate();
//            frame.repaint();
//        }



//        // Attempt to
//        try {
//            items = loadXml(file);
//        } catch (IOException e) {
//            items = createNewFile();
//
//            return;
//        }
//
        DetailsPanel detailsPanel = new DetailsPanel();

        TableModel model = new TableModel() {
            {
                addColumn("Corban Sucks", new Object[] {"Row1", "Row2", "Row3"});
                addColumn("Book", new Object[] {"Row4", "Row5", "Row6"});
                addColumn("Quantity", new Object[] {"Row1", "Row2", "Row3"});
                addColumn("Price", new Object[] {"Row4", "Row5", "Row6"});
            }
        };
        Display.display(model);

        TablePanel tablePanel = new TablePanel(model, detailsPanel);
        DisplayPanel displayPanel = new DisplayPanel(model);
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
}
