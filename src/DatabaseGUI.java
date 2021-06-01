import database.Database;
import database.storage.*;
import extraPackage.DBCheck;
import extraPackage.InAndOut;
import extraPackage.NewTableModel;
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
    private JButton regPresButton;
    private JButton regDevoButton;
    private JButton regUpdateButton;
    private JButton regDeleteButton;
    private JButton invConButton;

    private JTable employeesTable;
    private JTextField empSearchBar;
    private JButton empSearchButton;
    private JButton empNewButton;
    private JButton empDeleteButton;
    private JButton empUpdateButton;
    private JButton empConButton;

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
        regPresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseUpdate.onAddRegPres();
                refreshRegTable();
                refreshInvTable();
            }
        });
        regDevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseUpdate.onAddRegDev();
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

        invUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int itemId = -1;

                if(inventoryTable.getSelectedRow() != -1){
                    itemId = (int) invTM.getValueAt(inventoryTable.getSelectedRow(), 0);
                    DatabaseUpdate.onUpItem(itemId);

                    refreshInvTable();
                    refreshRegTable();
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Debe seleccionar un articulo para editar su informacion",
                            "Seleccion invalida",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });
        regUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int regId = -1;

                try{
                    regId = (int) regTM.getValueAt(registerTable.getSelectedRow(), 0);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(
                            null,
                            "Solo se puede modificar el ultimo registro",
                            "Seleccion invalida",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

                if(regId == DBCheck.maxId(DatabaseUpdate.database.getMoves())){
                    DatabaseUpdate.onUpReg(regId);

                    refreshRegTable();
                    refreshInvTable();
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Solo se puede modificar el ultimo registro",
                            "Seleccion invalida",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });
        empUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int empId;

                if(employeesTable.getSelectedRow() != -1){
                    empId = (int) empTM.getValueAt(employeesTable.getSelectedRow(), 0);
                    DatabaseUpdate.onUpEmp(empId);

                    refreshEmpTable();
                    refreshRegTable();
                    refreshDepTable();
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Debe seleccinoar un empleado para editar su informacion",
                            "Seleccion invalida",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });
        depUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int depId;

                if(departmentsTable.getSelectedRow() != -1){
                    depId =(int) depTM.getValueAt(departmentsTable.getSelectedRow(), 0);
                    DatabaseUpdate.onUpDep(depId);
                    refreshDepTable();
                    refreshEmpTable();
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Debe seleccionar un departamento para editar su informacion",
                            "Seleccion invalida",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        invDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int itemId;

                if(inventoryTable.getSelectedRow() != -1){
                    itemId = (int) invTM.getValueAt(inventoryTable.getSelectedRow(), 0);

                    DatabaseUpdate.onDelItem(itemId);

                    refreshInvTable();
                    refreshRegTable();
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Debe seleccionar un articulo para darlo de baja",
                            "Seleccion invalida",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });
        regDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int regId = -1;

                try{
                    regId = (int) regTM.getValueAt(registerTable.getSelectedRow(), 0);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(
                            null,
                            "Solo puede eliminar el ultimo registro, seleccionelo",
                            "Seleccion invalida",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

                if(regId == DBCheck.maxId(DatabaseUpdate.database.getMoves())){
                    DatabaseUpdate.onDelReg(regId);

                    refreshRegTable();
                    refreshInvTable();
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Solo puede eliminar el ultimo registro, seleccionelo",
                            "Seleccion invalida",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });
        empDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int empId;

                if(employeesTable.getSelectedRow() != -1){
                    empId = (int) regTM.getValueAt(employeesTable.getSelectedRow(), 0);

                    DatabaseUpdate.onDelEmp(empId);

                    refreshEmpTable();
                    refreshDepTable();
                    refreshRegTable();
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Debe seleccionar un empelado para darlo de baja",
                            "Seleccion invalida",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });
        depDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int depId;

                if(departmentsTable.getSelectedRow() != -1){
                    depId = (int) depTM.getValueAt(departmentsTable.getSelectedRow(), 0);

                    DatabaseUpdate.onDelDep(depId);

                    refreshDepTable();
                    refreshEmpTable();
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Debe seleccionar un departamento para darlo de baja",
                            "Seleccion invalida",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        invConButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int itemId;
                Item item;

                if(inventoryTable.getSelectedRow() != -1){
                    itemId = (int) invTM.getValueAt(inventoryTable.getSelectedRow(), 0);
                    item = DatabaseUpdate.database.getItems().get(DBCheck.existThisIdIn(itemId, DatabaseUpdate.database.getItems()));

                    ItemConGUI dialog = new ItemConGUI(item);

                    dialog.pack();
                    dialog.setIconImage(new ImageIcon("src/resources/icon.png").getImage());
                    dialog.setTitle("Consulta articulo: " + "[" + item.getId() + "] " + item.getName());
                    dialog.setSize(500, 600);
                    dialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Debe seleccionar un articulo para consultar sus movimientos",
                            "Seleccion invalida",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });
        empConButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int empId;
                Employee emp;

                if(employeesTable.getSelectedRow() != -1){
                    empId = (int) empTM.getValueAt(employeesTable.getSelectedRow(), 0);
                    emp = DatabaseUpdate.database.getEmployees().get(DBCheck.existThisIdIn(empId, DatabaseUpdate.database.getEmployees()));

                    EmpConGUI dialog = new EmpConGUI(emp);

                    dialog.pack();
                    dialog.setIconImage(new ImageIcon("src/resources/icon.png").getImage());
                    dialog.setTitle("Consulta empleado: " + " [" + emp.getId() + "] " + emp.getFullName());
                    dialog.setSize(600, 700);
                    dialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Debe seleccionar un empleado para consultar sus movimientos",
                            "Seleccion invalida",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        invSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        empSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        depSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void refreshInvTable ()
    {
        //Item item;
        String columnsName[] = {"Codigo", "Nombre", "Inventario"};

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
        String columnsName[] = {"Folio", "Fecha", "Articulo", "Empleado", "Cantidad"};
        Object[] newRow;

        Collections.reverse(moveList);

        //Limpiando para actualizar
        clearTable(registerTable);

        //Reincializando tabla
        regTM = new NewTableModel();
        regTM.setColumnIdentifiers(columnsName);
        registerTable.setModel(regTM);
        registerTable.getTableHeader().setReorderingAllowed(false);

        //Insertando los datos
        for(Move mov : moveList ){
            if(mov.getQty() < 0){
                newRow = new Object[]{mov.getId(), mov.getDate(), mov.getItem().getName(), mov.getEmployee().getFullName(), -mov.getQty() + " [Devolucion]"};
            } else {
                newRow = new Object[]{mov.getId(), mov.getDate(), mov.getItem().getName(), mov.getEmployee().getFullName(), mov.getQty() + " [Prestamo]"};
            }
            regTM.addRow(newRow);
        }

        Collections.reverse(moveList);
    }

    public void refreshEmpTable ()
    {
        String columnsName[] = {"Id", "RFC", "Nombre", "Departamento"};
        Object[] newRow;

        //Limpiando para actualizar
        clearTable(employeesTable);

        //Reincializando tabla
        empTM = new NewTableModel();
        empTM.setColumnIdentifiers(columnsName);
        employeesTable.setModel(empTM);
        employeesTable.getTableHeader().setReorderingAllowed(false);

        //Insertando los datos
        for(Employee emp: DatabaseUpdate.database.getEmployees()){
            try{
                newRow = new Object[]{emp.getId(), emp.getRFC(), emp.getFullName(), emp.getDepartment().getName()};
                empTM.addRow(newRow);
            } catch (Exception ex){
                newRow = new Object[]{emp.getId(), emp.getRFC(), emp.getFullName(), "Sin departamento"};
                empTM.addRow(newRow);
            }
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