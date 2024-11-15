package edu.uca.final_project.XMLExample;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.lang.reflect.Array;

public class TableModel extends DefaultTableModel{

    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        for(int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                string.append(this.getValueAt(i, j));
                string.append("\t");
            }
            string.append("\n");
        }
        return string.toString();
    }

    public TableColumn getColumn(int column) { //Idk if this works, haven't tested
        return new TableColumn(column);
    }

    public String[] getColumnData(int column) { //Returns an array containing the values of the specified column
        String[] array = new String[this.getRowCount()];
        for(int i = 0; i < this.getRowCount(); i++) {
            array[i] = this.getValueAt(column, i).toString();
        }

        return array;
    }


    // Removes a column from the table model
    public void removeColumn(int column) {
        // Early return if out of bounds
        if (column < 0 || column >= this.getColumnCount()) {
            return;
        }

        // Gets the row and column counts
        int rowCount = getRowCount();
        int columnCount = getColumnCount();

        // Allocates space for the new model data
        Object[][] newData = new Object[rowCount][columnCount - 1];

        // Allocates space for the new column names
        Object[] newColumnNames = new Object[columnCount - 1];

        // Loops through the table data and sets the new data accordingly
        for (int j = 0; j < rowCount; j++) {
            for (int i = 0; i < columnCount; i++) {
                if ( i < column) {
                    newData[j][i] = this.getValueAt(j, i);
                } else if (i > column) {
                    newData[j][i - 1] = getValueAt(j, i);
                }
            }
        }

        // Sets the new column names
        for (int i = 0, j = 0; i < columnCount; i++) {
            if (i != column) {
                newColumnNames[j++] = getColumnName(i);
            }
        }

        // Sets the table model with the new data
        setDataVector(newData, newColumnNames);
    }
}
