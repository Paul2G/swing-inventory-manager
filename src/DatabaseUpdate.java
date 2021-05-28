import database.Database;
import database.storage.*;
import extraPackage.*;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;

import static javax.swing.JOptionPane.OK_OPTION;

public class DatabaseUpdate {
    public static Database database;

    public static void initDatabase(){
        database = InAndOut.deserialize();
        if (database == null)
            database = new Database();
    }

    public static void onAddItem(){
        /*CREACION DEL FORMULARIO*/
        JPanel p = new JPanel();
        JTextField t = new JTextField();
        JSpinner sid = new JSpinner( );
        JSpinner scost= new JSpinner();
        JSpinner sqty= new JSpinner();

        t.setColumns(15);
        TextPrompt placeHName = new TextPrompt("Nombre...", t);
        placeHName.changeAlpha(0.75f);
        placeHName.changeStyle(Font.ITALIC);


        p.setLayout(new GridLayout (8,1));
        p.add(new JLabel("ID: "));
        p.add(sid);
        p.add(new JLabel("Nombre: "));
        p.add(t);
        p.add(new JLabel("Stock inicial: "));
        p.add(sqty);
        p.add(new JLabel("Costo: "));
        p.add(scost);

        /*AÑADIENDO A BASE DE DATOS*/
        if(OK_OPTION==JOptionPane.showConfirmDialog(null, p, "Nuevo articulo ", JOptionPane.OK_CANCEL_OPTION)){
            if(t.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Favor de asignar nombre al articulo", "Articulo sin nombre", JOptionPane.WARNING_MESSAGE);
                onAddItem();
            } else if ((int)sqty.getValue() < 1){
                JOptionPane.showMessageDialog(null, "Stock incial no puede ser menor a 1 \nFavor de reingresar stock", "Existencia invalida", JOptionPane.WARNING_MESSAGE);
                onAddItem();
            }else if ((int)scost.getValue() < 0){
                JOptionPane.showMessageDialog(null, "Costo no puede ser negativo \nFavor de reingresar costo", "Costo invalido", JOptionPane.WARNING_MESSAGE);
                onAddItem();
            }else if (OpeAndCond.existThisIdIn((int) sid.getValue(), database.getItems()) != -1 || (int) sid.getValue() < 1) {
                JOptionPane.showMessageDialog(null, "Codigo ya existente o menor a 1 \nFavor de asignar otro codigo", "Codigo de articulo invalido", JOptionPane.WARNING_MESSAGE);
                onAddItem();
            }else {
                database.addItem(new Item((int)sid.getValue(), t.getText(), (int)sqty.getValue(),(int) scost.getValue()));
            }
        }
    }

    public static void onAddEmp (){
        /*CREACION DEL FORMULARIO*/
        JPanel p = new JPanel();
        JTextField trfc = new JTextField(); //Para RFC
        JTextField tn = new JTextField(); //Para nombre
        JTextField tln = new JTextField(); //Para apellido
        JComboBox cd = new JComboBox(); //Para departamento

        //Inicializando cajas de texto
        trfc.setColumns(15);
        TextPrompt placeHRFC = new TextPrompt("RFC...", trfc);
        placeHRFC.changeAlpha(0.75f);
        placeHRFC.changeStyle(Font.ITALIC);

        tn.setColumns(15);
        TextPrompt placeHName = new TextPrompt("Nombre...", tn);
        placeHName.changeAlpha(0.75f);
        placeHName.changeStyle(Font.ITALIC);

        tln.setColumns(15);
        TextPrompt placeHLastName = new TextPrompt("Apellidos...", tln);
        placeHLastName.changeAlpha(0.75f);
        placeHLastName.changeStyle(Font.ITALIC);

        //Incializando combobox
        cd.addItem("Seleccione...");
        cd.addItem("Sin departamento");
        for(Department dep: database.getDepartments()){
            cd.addItem(dep);
        }

        p.setLayout(new GridLayout (8,1));
        p.add(new JLabel("RFC: "));
        p.add(trfc);
        p.add(new JLabel("Apellidos: "));
        p.add(tln);
        p.add(new JLabel("Nombre: "));
        p.add(tn);
        p.add(new JLabel("Departamento: "));
        p.add(cd);

        /*AÑADIENDO A BASE DE DATOS*/
        //Lista de validaciones y mensajes
        if(OK_OPTION==JOptionPane.showConfirmDialog(null, p, "Nuevo empleado ", JOptionPane.OK_CANCEL_OPTION)){
            if (trfc.getText().equals("") || tln.getText().equals("") || tn.getText().equals("") || cd.getSelectedItem().toString().equals("Seleccione...")){
                JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos", "Campo vacio", JOptionPane.WARNING_MESSAGE);
                onAddEmp();
            } else if(trfc.getText().length() != 18){
                JOptionPane.showMessageDialog(null, "RFC debe contener 18 catacteres", "RFC invalida", JOptionPane.WARNING_MESSAGE);
                onAddEmp();
            }else {
                if(!cd.getSelectedItem().toString().equals("Sin departamento")){
                    database.addEmployee(new Employee(OpeAndCond.maxId(database.getEmployees()) + 1,
                            trfc.getText().toUpperCase(),
                            tln.getText(),
                            tn.getText(),
                            (Department) cd.getSelectedItem()));
                } else {
                    database.addEmployee(new Employee(OpeAndCond.maxId(database.getEmployees()) + 1,
                            trfc.getText().toUpperCase(),
                            tln.getText(),
                            tn.getText()));
                }
            }
        }
    }

    public static void onAddReg(){
        int state;

        /*INCIALIZANDO FORMULARIO*/
        JPanel p = new JPanel();
        JComboBox ca = new JComboBox();
        JComboBox ce = new JComboBox();
        JSpinner sc = new JSpinner( );

        //Incializando combobox
        ca.addItem("Seleccione...");
        for(Item item: database.getItems()){
            ca.addItem(item);
        }

        ce.addItem("Seleccione...");
        for(Employee emp: database.getEmployees()){
            ce.addItem(emp);
        }

        //Inicializando panel
        p.setLayout(new GridLayout (6,1));
        p.add(new JLabel("Empleado:  "));
        p.add(ce);
        p.add(new JLabel("Solcita (Negativo implica devolucion): "));
        p.add(sc);
        p.add(new JLabel("Unidades de: "));
        p.add(ca);

        /*DESPLEGANDO FORMULARIO*/
        if(OK_OPTION==JOptionPane.showConfirmDialog(null, p, "Nuevo prestamo ", JOptionPane.OK_CANCEL_OPTION)){
            if(ce.getSelectedItem().toString().equals("Seleccione...") || ca.getSelectedItem().toString().equals("Seleccione...")){
                JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos", "Campo vacio", JOptionPane.WARNING_MESSAGE);
                onAddReg();
            }else
                if ((int)sc.getValue() == 0){
                    JOptionPane.showMessageDialog(null, "No puedes ingresar 0 unidades", "Cantidad invalida", JOptionPane.WARNING_MESSAGE);
                    onAddReg();
                } else{
                    state = database.addMove(new Move((OpeAndCond.maxId(database.getMoves())+1),
                            (Employee) ce.getSelectedItem(),
                            (Item) ca.getSelectedItem(),
                            (int) sc.getValue()));
                    if ( state == Database.ERR_MOV$STOCK){
                        JOptionPane.showMessageDialog(null, "El retiro de unidades no puede exceder la existencia del producto", "Registro no permitido", JOptionPane.WARNING_MESSAGE);
                        onAddReg();
                    } else if (state == Database.ERR_MOV$DEBT){
                        JOptionPane.showMessageDialog(null, "El retorno de unidades no puede exceder la deuda del empleado", "Registro no permitido", JOptionPane.WARNING_MESSAGE);
                        onAddReg();
                    }
                }
        }
    }

    public static void onAddDep(){
        /*CREACION DEL FORMULARIO*/
        JPanel p = new JPanel();
        JTextField t = new JTextField();
        JSpinner s = new JSpinner( );

        t.setColumns(15);
        TextPrompt placeHName = new TextPrompt("Nombre...", t);
        placeHName.changeAlpha(0.75f);
        placeHName.changeStyle(Font.ITALIC);

        p.setLayout(new GridLayout (4,1));
        p.add(new JLabel("Numero: "));
        p.add(s);
        p.add(new JLabel("Nombre del departamento: "));
        p.add(t);

        /*AÑADIENDO A BASE DE DATOS*/
        if(OK_OPTION==JOptionPane.showConfirmDialog(null, p, "Nuevo departamento ", JOptionPane.OK_CANCEL_OPTION)){
            if(t.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Favor de asignar nombre al departamento", "Departamento sin nombre", JOptionPane.WARNING_MESSAGE);
                onAddDep();
            } else if (OpeAndCond.existThisIdIn((int) s.getValue(), database.getDepartments()) != -1 || (int) s.getValue() < 1) {
                JOptionPane.showMessageDialog(null, "Numero ya existente o menor a 1 \nFavor de asignar otro numero", "Numero de departamento invalido", JOptionPane.WARNING_MESSAGE);
                onAddDep();
            }else {
                database.addDepartment(new Department((int)s.getValue(), t.getText()));
            }
        }
    }
}
