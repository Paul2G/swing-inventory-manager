import database.Database;
import database.storage.*;
import extraPackage.InAndOut;
import extraPackage.TextPrompt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.List;


public class DatabaseGUI extends JFrame {
    private JPanel contentPane;

    private JTable inventoryTable;
    private JTextField invSearchBar;
    private JButton invSearchButton;
    private JButton invNewButton;
    private JButton invUpdateButton;
    private JButton invDeleteButton;

    private JTable registerTable;
    private JButton regNewButton;
    private JButton regUpdateButton;
    private JButton regDeleteButton;

    private JTable employeesTable;
    private JTextField empSearchBar;
    private JButton empSearchButton;
    private JButton empNewButton;
    private JButton empDeleteButton;
    private JButton empUpdateButton;

    private JTable departmentsTable;
    private JTextField depSearchBar;
    private JButton depSearchButton;
    private JButton depNewButton;
    private JButton depUpdateButton;
    private JButton depDeleteButton;

    private NewTableModel invTM = new NewTableModel();
    private NewTableModel regTM = new NewTableModel();
    private NewTableModel empTM = new NewTableModel();
    private NewTableModel depTM = new NewTableModel();

    public DatabaseGUI(){
        /*INICIALIZACION DE LA VENTANA Y TABLAS*/
        //Configuracion de la ventana
        setContentPane(contentPane);
        pack();
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        //Insertando diseño
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Throwable e) {
            //e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);

        //Para cerrar la ventana
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e){
                int i = JOptionPane.showConfirmDialog(null, "\u00BFSeguro que desea salir?", "Saliendo", JOptionPane.YES_NO_OPTION);
                if(i==0) {
                    //Guardando cambios y saliendo
                    InAndOut.serialize(DatabaseUpdate.database);
                    System.exit(0);
                }
            }
        });

        DatabaseUpdate.initDatabase();

        refreshInvTable();
        refreshRegTable();
        refreshEmpTable();
        refreshDepTable();

        /*RELACIONADO A DISEÑO Y ACCIONES*/
        //TextHolders para barras de busqueda
        TextPrompt placeHInv = new TextPrompt("Nombre del articulo...", invSearchBar);
        placeHInv.changeAlpha(0.75f);
        placeHInv.changeStyle(Font.ITALIC);

        TextPrompt placeHEmp = new TextPrompt("Nombre del empelado...", empSearchBar);
        placeHEmp.changeAlpha(0.75f);
        placeHEmp.changeStyle(Font.ITALIC);

        TextPrompt placeHDep = new TextPrompt("Nombre de departamento...", depSearchBar);
        placeHDep.changeAlpha(0.75f);
        placeHDep.changeStyle(Font.ITALIC);

        //Aqui empeiza el garabato de botones jajaja
        invNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseUpdate.onAddItem();
                refreshInvTable();
            }
        });
        regNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseUpdate.onAddReg();
                refreshRegTable();
                refreshInvTable();
            }
        });
        empNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseUpdate.onAddEmp();
                refreshEmpTable();
                refreshDepTable();
            }
        });
        depNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseUpdate.onAddDep();
                refreshDepTable();
            }
        });
    }

    private void refreshInvTable ()
    {
        //Item item;
        String columnsName[] = {"Codigo", "Nombre", "Stock"};

        //Limpiando para actualizar
        clearTable(inventoryTable);

        //Reincializando tabla
        invTM = new NewTableModel();
        invTM.setColumnIdentifiers(columnsName);
        inventoryTable.setModel(invTM);
        inventoryTable.getTableHeader().setReorderingAllowed(false);

        //Insertando los datos
        for(Item item: DatabaseUpdate.database.getItems()){
            Object[] newRow = {item.getId(), item.getName(), item.getQty()};
            invTM.addRow(newRow);
        }
    }

    private void refreshRegTable ()
    {
        List<Move> moveList = DatabaseUpdate.database.getMoves();
        Collections.reverse(moveList);
        String columnsName[] = {"Folio", "Fecha", "Articulo", "Empleado", "Cantidad"};

        //Limpiando para actualizar
        clearTable(registerTable);

        //Reincializando tabla
        regTM = new NewTableModel();
        regTM.setColumnIdentifiers(columnsName);
        registerTable.setModel(regTM);
        registerTable.getTableHeader().setReorderingAllowed(false);

        //Insertando los datos
        for(Move mov : moveList ){
            Object[] newRow = {mov.getId(), mov.getDate(), mov.getItem().getName(), mov.getEmployee().getFullName(), mov.getQty()};
            regTM.addRow(newRow);
        }

        Collections.reverse(moveList);
    }

    public void refreshEmpTable ()
    {
        String columnsName[] = {"Id", "RFC", "Nombre", "Departamento"};

        //Limpiando para actualizar
        clearTable(employeesTable);

        //Reincializando tabla
        empTM = new NewTableModel();
        empTM.setColumnIdentifiers(columnsName);
        employeesTable.setModel(empTM);
        employeesTable.getTableHeader().setReorderingAllowed(false);

        //Insertando los datos
        for(Employee emp: DatabaseUpdate.database.getEmployees()){
            Object[] newRow = {emp.getId(), emp.getRFC(), emp.getFullName(), emp.getDepartment()};
            empTM.addRow(newRow);
        }
    }

    public void refreshDepTable ()
    {
        String columnsName[] = {"Numero", "Nombre", "Cant de empleados"};

        //Limpiando para actualizar
        clearTable(departmentsTable);

        //Reincializando tabla
        depTM = new NewTableModel();
        depTM.setColumnIdentifiers(columnsName);
        departmentsTable.setModel(depTM);
        departmentsTable.getTableHeader().setReorderingAllowed(false);

        //Insertando los datos
        for(Department dep: DatabaseUpdate.database.getDepartments()){
            Object[] newRow = {dep.getId(), dep.getName(), dep.getEmployees().size()};
            depTM.addRow(newRow);
        }
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

//Clase hecha para evitar celdas editables en tablas
class NewTableModel extends DefaultTableModel
{
    public boolean isCellEditable (int row, int column)
    {
        return false;
    }
}
