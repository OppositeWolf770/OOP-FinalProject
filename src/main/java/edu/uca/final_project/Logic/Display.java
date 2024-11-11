package edu.uca.final_project.Logic;

import edu.uca.final_project.GUI.DisplayFrame;
import edu.uca.final_project.GUI.DisplayPanel;
import edu.uca.final_project.XMLExample.TableModel;

public final class Display {
    static DisplayFrame instance;

    public static void display(TableModel model){
        System.out.println("Inside Display");
        instance = new DisplayFrame(model);
    }
}
