package extraPackage;

import javax.swing.table.DefaultTableModel;

public class NewTableModel extends DefaultTableModel
{
    public boolean isCellEditable (int row, int column)
    {
        return false;
    }
}