package edu.uca.final_project.persistence;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import edu.uca.final_project.model.InventoryManager;
import java.io.File;
import java.io.IOException;

public class XMLPersistence {

    public static void saveToFile(InventoryManager manager, String fileName) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.writeValue(new File(fileName), manager);
    }

    public static InventoryManager loadFromFile(String fileName) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(new File(fileName), InventoryManager.class);
    }
}
