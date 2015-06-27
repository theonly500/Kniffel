import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TableRenderer extends DefaultTableCellRenderer
{
    public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int column)
    {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Object valueAt = table.getModel().getValueAt(row, column);
        String s = "";
        if (valueAt != null) {
            s = valueAt.toString();
        }

        if(s.equalsIgnoreCase("colorin")){
            c.setBackground(new Color(255,0,0,90));
        }
        else if(s.equalsIgnoreCase("decolor")&&c.getBackground()==new Color(255,0,0,90)){
            c.setBackground(new Color(255,255,255,255));
        }
        else if(c.getBackground()==new Color(255,0,0,90)){
            c.setBackground(new Color(255,0,0,90));
        }
        else {
            c.setBackground(new Color(255,255,255,255));
        }
        return c;
    }

}
