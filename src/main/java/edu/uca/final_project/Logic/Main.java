package edu.uca.final_project.Logic;

import edu.uca.final_project.XMLExample.TableModel;

import javax.swing.table.TableColumn;
import java.io.File;

public class Main {
    public static File file;

    public static int location;
    public static String title;

    public static void main(String[] args) {
        // isFirstRun = bool? Tests if there is data to be loaded
        //      configureFirstRun() // If true, run method to set up data
                        // Brings up a UI for the user to insert specified categories
        // loadData() // The main method
        //


        TableModel model = new TableModel() { //To be replaced with a file reader method
            {
                addColumn("Corban Sucks", new Object[] {"Row1", "Row2", "Row3"});
                addColumn("Book", new Object[] {"Row4", "Row5", "Row6"});
                addColumn("Quantity", new Object[] {"Row1", "Row2", "Row3"});
                addColumn("Price", new Object[] {"Row4", "Row5", "Row6"});
            }
        };
        Display.display(model);
    }
}
