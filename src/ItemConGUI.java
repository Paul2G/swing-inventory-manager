import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.List;

import database.storage.Item;
import database.storage.Move;
import extraPackage.NewTableModel;

public class ItemConGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTable itemMovesTable;

    private JLabel itemNameLabel;
    private JLabel itemDebtLabel;
    private JLabel itemStockLabel;
    private JLabel itemTotalLabel;
    private JLabel itemCostLabel;
    private JLabel itemRiskLabel;
    private JLabel itemIDLabel;

    private NewTableModel regTM = new NewTableModel();

    private Item item;

    public ItemConGUI(Item item) {
        this.item = item;

        /*BIEN INCIALIZADO DESDE AQUI*/
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //Inicializacion de elementos relativos al articulo
        refreshRegTable();
        itemIDLabel.setText(item.getId() + "");
        itemNameLabel.setText(item.getName());
        itemStockLabel.setText(item.getQty() + "");
        itemDebtLabel.setText((item.getTotalQty() - item.getQty()) + "");
        itemTotalLabel.setText(item.getTotalQty() + "");
        itemCostLabel.setText(item.getCost() + "");

        if(item.getQty() == 0){
            itemRiskLabel.setText("Sin stock");
            itemRiskLabel.setForeground(new Color(137, 0, 0));
        }else if(item.getQty() < ((float) item.getTotalQty()) / 3){
            itemRiskLabel.setText("Stock muy bajo");
            itemRiskLabel.setForeground(new Color(170, 82, 0));
        } else if (item.getQty() < 2 * ((float) item.getTotalQty()) / 3){
            itemRiskLabel.setText("Stock moderado");
            itemRiskLabel.setForeground(new Color(206, 160, 0));
        } else {
            itemRiskLabel.setText("Stock alto");
            itemRiskLabel.setForeground(new Color(0, 144, 37));
        }

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // call onOK() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        // call onOK() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void refreshRegTable ()
    {
        List<Move> moveList = item.getMoves();
        String columnsName[] = {"Folio", "Fecha", "Empleado", "Cantidad"};
        Object[] newRow;

        Collections.reverse(moveList);

        //Limpiando para actualizar
        clearTable(itemMovesTable);

        //Reincializando tabla
        regTM = new NewTableModel();
        regTM.setColumnIdentifiers(columnsName);
        itemMovesTable.setModel(regTM);
        itemMovesTable.getTableHeader().setReorderingAllowed(false);

        //Insertando los datos
        for(Move mov : moveList ){
            if(mov.getQty() < 0){
                newRow = new Object[]{mov.getId(), mov.getDate(), mov.getEmployee().getFullName(), "[Devolucion]  " + (-mov.getQty())};
            } else {
                newRow = new Object[]{mov.getId(), mov.getDate(), mov.getEmployee().getFullName(), "[Prestamo]     " + mov.getQty()};
            }
            regTM.addRow(newRow);
        }

        Collections.reverse(moveList);
    }

    private void clearTable(JTable table) {
        int rows = table.getRowCount();
        DefaultTableModel model;

        for (int i = 0 ; i < rows ; i++){
            model = (DefaultTableModel) table.getModel();
            model.removeRow(0);
        }
    }
}
