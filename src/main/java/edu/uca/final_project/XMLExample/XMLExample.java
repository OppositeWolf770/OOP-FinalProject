package edu.uca.final_project.XMLExample;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class XMLExample {
static XmlMapper mapper = new XmlMapper();

    public static void main(String[] args) {
        File file = new File("test.xml");

        Library library = loadXml(file);

        library.displayBooks();

        library.addBook(
                "Nonfiction",
                "The Story of My Life",
                "Dr. Doolittle",
                2003,
                23.99
        );

        library.displayBooks();

        library.removeBook("The Great Gatsby");

        library.displayBooks();

        saveXml(file, library);
    }


    // Read data from an XML file
    public static Library loadXml(File file) {
        try {
            return mapper.readValue(file, Library.class);
        } catch (IOException e) {
            return new Library();
        }
    }

    public static void saveXml(File file, Library library) {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            mapper.writeValue(file, library);
        } catch (IOException e) {
            System.out.println("Unable to write to file " + file + ".");
        }
    }
}
