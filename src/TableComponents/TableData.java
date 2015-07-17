package TableComponents;

import Exceptions.IntegerFoundException;
import Exceptions.IntegerNotFoundException;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;

public class TableData
{
    private JTable table;
    private DefaultTableModel resultTableModel;
    private ArrayList<String> names;

    public TableData(int playerCount,ArrayList<String> names2) {
        names=names2;
        setupTable(playerCount);
    }

    public JTable returnTable(){
        return table;
    }

    public String returnValueAt(int y,int x){
        if(resultTableModel.getValueAt(y,x)!=null){
            return resultTableModel.getValueAt(y,x).toString();
        }
        return null;
    }

    public void setValueAt(Object value,int y, int x){
        resultTableModel.setValueAt(value,y,x);
    }

    public void preSetValueAt(int y,int x) throws IntegerFoundException, IntegerNotFoundException{
        try {
            Integer.parseInt(returnValueAt(y,x).toString());
            throw new IntegerFoundException();
        }
        catch (IntegerFoundException ifEx){

        }
        catch (Exception e){
            throw new IntegerNotFoundException();
        }
    }

    private void setupTable(int playerCount){
        resultTableModel = new DefaultTableModel(18,playerCount+1)
        {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };
        table=new JTable(resultTableModel);
        TableRenderer tableRenderer=new TableRenderer();
        table.setDefaultRenderer(String.class, tableRenderer);
        setValues(playerCount);
        TableCellEditor tableCellEditor=new TableCellEditor()
        {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return null;
            }

            @Override
            public Object getCellEditorValue() {
                return null;
            }

            @Override
            public boolean isCellEditable(EventObject anEvent) {
                return false;
            }

            @Override
            public boolean shouldSelectCell(EventObject anEvent) {
                return false;
            }

            @Override
            public boolean stopCellEditing() {
                return false;
            }

            @Override
            public void cancelCellEditing() {

            }

            @Override
            public void addCellEditorListener(CellEditorListener l) {

            }

            @Override
            public void removeCellEditorListener(CellEditorListener l) {

            }
        };
        table.setDefaultEditor(String.class,tableCellEditor);
    }

    private void setValues(int playerCount) {
        resultTableModel.setValueAt("Players", 0, 0);
        resultTableModel.setValueAt("Ones", 1, 0);
        resultTableModel.setValueAt("Twos", 2, 0);
        resultTableModel.setValueAt("Threes", 3, 0);
        resultTableModel.setValueAt("Fours", 4, 0);
        resultTableModel.setValueAt("Fives", 5, 0);
        resultTableModel.setValueAt("Sixes", 6, 0);
        resultTableModel.setValueAt("Sum top", 7, 0);
        resultTableModel.setValueAt("Bonus", 8, 0);
        resultTableModel.setValueAt("Three of a kind", 9, 0);
        resultTableModel.setValueAt("Four of a kind", 10, 0);
        resultTableModel.setValueAt("Full House", 11, 0);
        resultTableModel.setValueAt("Small Straight", 12, 0);
        resultTableModel.setValueAt("Large Straight", 13, 0);
        resultTableModel.setValueAt("Yahtzee", 14, 0);
        resultTableModel.setValueAt("Chance", 15, 0);
        resultTableModel.setValueAt("Sum bottom", 16, 0);
        resultTableModel.setValueAt("Total", 17, 0);
        for(int i=0; i<playerCount; i++)
        {
            resultTableModel.setValueAt(""
                    +names.get(i)+
                    //+i+
                    "", 0, i+1);
        }
    }
}
