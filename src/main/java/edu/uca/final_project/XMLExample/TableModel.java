package edu.uca.final_project.XMLExample;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel implements TableModelListener {
    @Override
    public void tableChanged(TableModelEvent e) {
        System.out.println("Daniel sucks");
    }
}
