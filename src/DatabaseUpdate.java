import database.Database;
import database.storage.*;
import extraPackage.*;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.*;

public class DatabaseUpdate {
    static Database database;

    static void initDatabase(){
        database = InAndOut.deserialize();
        if (database == null)
            database = new Database();
    }

    static boolean saveDatabase(){
        return InAndOut.serialize(database);
    }

    static void onAddItem(){
        int selection;

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

        //Añadiendo elemtnos al panel
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
        selection = JOptionPane.showConfirmDialog(null, p, "Nuevo articulo ", JOptionPane.OK_CANCEL_OPTION);
        if(selection == OK_OPTION){
            if(t.getText().equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Favor de asignar nombre al articulo",
                        "Error al añadir nuevo articulo",
                        JOptionPane.WARNING_MESSAGE
                );
                onAddItem();
            } else if(DBCheck.existThisIdIn((int) sid.getValue(), database.getItems()) != -1 || (int) sid.getValue() < 1) {
                JOptionPane.showMessageDialog(
                        null,
                        "Codigo ya existente o menor a 1 \nFavor de asignar otro codigo",
                        "Error al añadir nuevo articulo",
                        JOptionPane.WARNING_MESSAGE
                );
                onAddItem();
            }else if ((int)sqty.getValue() < 1){
                JOptionPane.showMessageDialog(
                        null,
                        "Stock incial no puede ser menor a 1 \nFavor de reingresar stock",
                        "Error al añadir nuevo articulo",
                        JOptionPane.WARNING_MESSAGE
                );
                onAddItem();
            }else if ((int)scost.getValue() < 0){
                JOptionPane.showMessageDialog(
                        null,
                        "Costo no puede ser negativo \nFavor de reingresar costo",
                        "Error al añadir nuevo articulo",
                        JOptionPane.WARNING_MESSAGE
                );
                onAddItem();
            }else  {
                database.addItem(new Item((int)sid.getValue(), t.getText(), (int)sqty.getValue(),(int) scost.getValue()));
                JOptionPane.showMessageDialog(
                        null,
                        "El articulo ha sido añadido al inventario con exito",
                        "Exito al añadir nuevo articulo",
                        INFORMATION_MESSAGE
                );
            }
        }
    }

    static void onAddEmp (){
        int selection;

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
        selection = JOptionPane.showConfirmDialog(null, p, "Nuevo empleado ", JOptionPane.OK_CANCEL_OPTION);
        if(selection == OK_OPTION){
            if (trfc.getText().equals("") || tln.getText().equals("") || tn.getText().equals("") || cd.getSelectedItem().toString().equals("Seleccione...")){
                JOptionPane.showMessageDialog(
                        null,
                        "Favor de llenar todos los campos",
                        "Error al dar de alta empleado",
                        JOptionPane.WARNING_MESSAGE
                );
                onAddEmp();
            } else if(trfc.getText().length() != 13){
                JOptionPane.showMessageDialog(
                        null,
                        "RFC debe contener 13 catacteres",
                        "Error al dar de alta empleado",
                        JOptionPane.WARNING_MESSAGE
                );
                onAddEmp();
            }else {
                if(!cd.getSelectedItem().toString().equals("Sin departamento")){
                    database.addEmployee(new Employee(DBCheck.maxId(database.getEmployees()) + 1,
                            trfc.getText().toUpperCase(),
                            tln.getText(),
                            tn.getText(),
                            (Department) cd.getSelectedItem()));
                } else {
                    database.addEmployee(new Employee(DBCheck.maxId(database.getEmployees()) + 1,
                            trfc.getText().toUpperCase(),
                            tln.getText(),
                            tn.getText()));
                }
                JOptionPane.showMessageDialog(
                        null,
                        "El empleado ha sido dado de alta con exito",
                        "Exito al dar de alta empleado",
                        INFORMATION_MESSAGE
                );
            }
        }
    }

    static void onAddRegPres(){
        int state, selection;

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
        p.add(new JLabel("Solcita: "));
        p.add(sc);
        p.add(new JLabel("Unidades de: "));
        p.add(ca);

        /*DESPLEGANDO FORMULARIO*/
        selection = JOptionPane.showConfirmDialog(null, p, "Nuevo prestamo ", JOptionPane.OK_CANCEL_OPTION);
        if(selection == OK_OPTION){
            if(ce.getSelectedItem().toString().equals("Seleccione...") || ca.getSelectedItem().toString().equals("Seleccione...")){
                JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos", "Campo vacio", JOptionPane.WARNING_MESSAGE);
                onAddRegPres();
            }else {
                if ((int) sc.getValue() < 1) {
                    JOptionPane.showMessageDialog(null, "Solo numeros positivos mayores a 0", "Cantidad invalida", JOptionPane.WARNING_MESSAGE);
                    onAddRegPres();
                } else {
                    state = database.addMove(new Move((DBCheck.maxId(database.getMoves()) + 1),
                            (Employee) ce.getSelectedItem(),
                            (Item) ca.getSelectedItem(),
                            (int) sc.getValue()));
                    if (state == Database.ERR_MOV$STOCK) {
                        JOptionPane.showMessageDialog(
                                null,
                                "El retiro de unidades no puede exceder inventario del producto",
                                "Error al capturar registro",
                                JOptionPane.WARNING_MESSAGE
                        );
                        onAddRegPres();
                    } else if (state == Database.ERR_MOV$DEBT) {
                        JOptionPane.showMessageDialog(
                                null,
                                "El retorno de unidades no puede exceder la deuda del empleado",
                                "Error al capturar registro",
                                JOptionPane.WARNING_MESSAGE);
                        onAddRegPres();
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "El registro nuevo ha sido capturado en el historial de movimientos",
                                "Exito al capturar registro",
                                INFORMATION_MESSAGE
                        );
                    }
                }
            }
        }
    }

    static void onAddRegDev(){
        int state, selection;

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
        p.add(new JLabel("Devuelve: "));
        p.add(sc);
        p.add(new JLabel("Unidades de: "));
        p.add(ca);

        /*DESPLEGANDO FORMULARIO*/
        selection = JOptionPane.showConfirmDialog(null, p, "Devolucion de articulo", JOptionPane.OK_CANCEL_OPTION);
        if(selection == OK_OPTION){
            if(ce.getSelectedItem().toString().equals("Seleccione...") || ca.getSelectedItem().toString().equals("Seleccione...")){
                JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos", "Campo vacio", JOptionPane.WARNING_MESSAGE);
                onAddRegDev();
            }else {
                if ((int) sc.getValue() < 0) {
                    JOptionPane.showMessageDialog(null, "No puedes devolver unidades negativas", "Cantidad invalida", JOptionPane.WARNING_MESSAGE);
                    onAddRegDev();
                } else {
                    state = database.addMove(new Move((DBCheck.maxId(database.getMoves()) + 1),
                            (Employee) ce.getSelectedItem(),
                            (Item) ca.getSelectedItem(),
                             - (int) sc.getValue()));
                    if (state == Database.ERR_MOV$STOCK) {
                        JOptionPane.showMessageDialog(
                                null,
                                "El retiro de unidades no puede exceder inventario del producto",
                                "Error al capturar registro",
                                JOptionPane.WARNING_MESSAGE
                        );
                        onAddRegDev();
                    } else if (state == Database.ERR_MOV$DEBT) {
                        JOptionPane.showMessageDialog(
                                null,
                                "El retorno de unidades no puede exceder la deuda del empleado",
                                "Error al capturar registro",
                                JOptionPane.WARNING_MESSAGE);
                        onAddRegDev();
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "El registro nuevo ha sido capturado en el historial de movimientos",
                                "Exito al capturar registro",
                                INFORMATION_MESSAGE
                        );
                    }
                }
            }
        }
    }

    static void onAddDep(){
        int selection;

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
        selection = JOptionPane.showConfirmDialog(null, p, "Nuevo departamento ", JOptionPane.OK_CANCEL_OPTION);
        if(selection == OK_OPTION){
            if(t.getText().equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Favor de asignar nombre al departamento",
                        "Error al dar de alta departamento",
                        JOptionPane.WARNING_MESSAGE
                );
                onAddDep();
            } else if (DBCheck.existThisIdIn((int) s.getValue(), database.getDepartments()) != -1 || (int) s.getValue() < 1) {
                JOptionPane.showMessageDialog(
                        null,
                        "Numero ya existente o menor a 1 \nFavor de asignar otro numero",
                        "Error al dar de alta departamento",
                        JOptionPane.WARNING_MESSAGE
                );
                onAddDep();
            }else {
                database.addDepartment(new Department((int)s.getValue(), t.getText()));
                JOptionPane.showMessageDialog(
                        null,
                        "El departamento ha sido añadido con exito",
                        "Exito al dar de alta departamento",
                        INFORMATION_MESSAGE
                );
            }
        }
    }

    static void onUpItem(int itemId){
        int selection;
        Item item = database.getItems().get(DBCheck.existThisIdIn(itemId, database.getItems()));

        /*INICIALIZANDO PANEL*/
        JPanel p = new JPanel();
        JTextField tname = new JTextField();
        JSpinner sqty = new JSpinner();
        JSpinner scost = new JSpinner();

        //Inicializando elementos del panel con valores actuales
        tname.setColumns(15);
        TextPrompt placeHName = new TextPrompt("Nombre...", tname);
        placeHName.changeAlpha(0.75f);
        placeHName.changeStyle(Font.ITALIC);

        tname.setText(item.getName());
        sqty.setValue(item.getQty());
        scost.setValue(item.getCost());

        p.setLayout(new GridLayout (6,1));
        p.add(new JLabel("Nombre: "));
        p.add(tname);
        p.add(new JLabel("Stock: "));
        p.add(sqty);
        p.add(new JLabel("Costo: "));
        p.add(scost);

        /*Modificando base de datos*/
        selection = JOptionPane.showConfirmDialog(null, p, "Editando articulo ", JOptionPane.OK_CANCEL_OPTION);
        if(selection == OK_OPTION){
            if(tname.getText().equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Favor de asignar nombre al articulo",
                        "Error al actualizar articulo",
                        JOptionPane.WARNING_MESSAGE
                );
                onUpItem(itemId);
            } else if ((int)sqty.getValue() < 0){
                JOptionPane.showMessageDialog(
                        null,
                        "El stock no puede ser negativo \nFavor de reingresar stock",
                        "Error al actualizar articulo",
                        JOptionPane.WARNING_MESSAGE
                );
                onUpItem(itemId);
            }else if ((int)scost.getValue() < 0){
                JOptionPane.showMessageDialog(
                        null,
                        "Costo no puede ser negativo \nFavor de reingresar costo",
                        "Error al actualizar articulo",
                        JOptionPane.WARNING_MESSAGE
                );
                onUpItem(itemId);
            }else  {
                int dif = (int) sqty.getValue() - item.getQty();

                item.setName(tname.getText());
                item.setCost((int) scost.getValue());
                item.addQty(dif);
                item.setTotalQty(item.getTotalQty() + dif);

                JOptionPane.showMessageDialog(
                        null,
                        "La informacion del articulo ha sido actualizada con exito",
                        "Exito al actualizar articulo",
                        INFORMATION_MESSAGE
                );
            }
        }
    }

    static void onUpReg(int regId){
        Move move = database.getMoves().get(DBCheck.existThisIdIn(regId, database.getMoves()));
        Move tempMove;
        int state, selection;

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

        ca.setSelectedItem(move.getItem());
        ce.setSelectedItem(move.getEmployee());
        sc.setValue(move.getQty());

        //Inicializando panel
        p.setLayout(new GridLayout (6,1));
        p.add(new JLabel("Empleado:  "));
        p.add(ce);
        p.add(new JLabel("Solcita (Negativo implica devolucion): "));
        p.add(sc);
        p.add(new JLabel("Unidades de: "));
        p.add(ca);

        /*DESPLEGANDO FORMULARIO*/
        selection = JOptionPane.showConfirmDialog(null, p, "Editar prestamo ", JOptionPane.OK_CANCEL_OPTION);
        if(selection == OK_OPTION){
            if(ce.getSelectedItem().toString().equals("Seleccione...") || ca.getSelectedItem().toString().equals("Seleccione...")){
                JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos", "Campo vacio", JOptionPane.WARNING_MESSAGE);
                onUpReg(regId);
            }else {
                if ((int) sc.getValue() == 0) {
                    JOptionPane.showMessageDialog(null, "No puedes ingresar 0 unidades", "Cantidad invalida", JOptionPane.WARNING_MESSAGE);
                    onUpReg(regId);
                } else {
                    tempMove = move;
                    database.delMove(move);
                    state = database.addMove(
                            new Move(
                                (DBCheck.maxId(database.getMoves()) + 1),
                                (Employee) ce.getSelectedItem(),
                                (Item) ca.getSelectedItem(),
                                (int) sc.getValue())
                    );
                    if (state == Database.ERR_MOV$STOCK) {
                        JOptionPane.showMessageDialog(
                                null,
                                "El retiro de unidades no puede exceder inventario del producto",
                                "Error al actualizar registro",
                                JOptionPane.WARNING_MESSAGE
                        );
                        database.addMove(tempMove);
                        onUpReg(regId);
                    } else if (state == Database.ERR_MOV$DEBT) {
                        JOptionPane.showMessageDialog(
                                null,
                                "El retorno de unidades no puede exceder la deuda del empleado",
                                "Error al actualizar registro",
                                JOptionPane.WARNING_MESSAGE);
                        database.addMove(tempMove);
                        onUpReg(regId);
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Los parametros del registro han sido actualizados con exito",
                                "Exito al actualizar registro",
                                INFORMATION_MESSAGE
                        );
                    }
                }
            }
        }
    }

    static void onUpEmp(int empId){
        int selection;
        Employee emp = database.getEmployees().get(DBCheck.existThisIdIn(empId, database.getEmployees()));

        /*INICIALIZANDO PANEL*/
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

        //Inicializando cajas de texto
        trfc.setText(emp.getRFC());
        tn.setText(emp.getName());
        tln.setText(emp.getLastName());

        if(emp.getDepartment()==null)
            cd.setSelectedItem("Sin departamento");
        else
            cd.setSelectedItem(emp.getDepartment());

        //Añadiendo elementos al panel
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
        selection = JOptionPane.showConfirmDialog(null, p, "Editar empleado ", JOptionPane.OK_CANCEL_OPTION);
        if(selection == OK_OPTION){
            if (trfc.getText().equals("") || tln.getText().equals("") || tn.getText().equals("") || cd.getSelectedItem().toString().equals("Seleccione...")){
                JOptionPane.showMessageDialog(
                        null,
                        "Favor de llenar todos los campos",
                        "Error al actualizar datos del empleado",
                        JOptionPane.WARNING_MESSAGE
                );
                onUpEmp(empId);
            } else if(trfc.getText().length() != 13){
                JOptionPane.showMessageDialog(
                        null,
                        "RFC debe contener 13 catacteres",
                        "Error al actualizar datos de empleado",
                        JOptionPane.WARNING_MESSAGE
                );
                onUpEmp(empId);
            }else {
                emp.setRFC(trfc.getText());
                emp.setFullName(tln.getText(), tn.getText());

                if(emp.getDepartment() != null){
                    emp.getDepartment().getEmployees().remove(emp);
                }

                if(!cd.getSelectedItem().toString().equals("Sin departamento")){
                    emp.setDepartament((Department) cd.getSelectedItem());
                    emp.getDepartment().getEmployees().add(emp);
                } else {
                    emp.setDepartament(null);

                }

                JOptionPane.showMessageDialog(
                        null,
                        "La informacion del empleado ha sido actualizada con exito",
                        "Exito al actualizar datos de empleado",
                        INFORMATION_MESSAGE
                );
            }
        }
    }

    static void onUpDep(int depId){
        int selection;
        Department dep = database.getDepartments().get(DBCheck.existThisIdIn(depId, database.getDepartments()));

        /*INCIALIZANDO FORMULARIO*/
        JPanel p = new JPanel();
        JTextField tname = new JTextField();

        tname.setColumns(15);
        TextPrompt placeHName = new TextPrompt("Nombre...", tname);
        placeHName.changeAlpha(0.75f);
        placeHName.changeStyle(Font.ITALIC);

        tname.setText(dep.getName());

        //Añadiendo elementos al panel
        p.setLayout(new GridLayout (2,1));
        p.add(new JLabel("Nombre:"));
        p.add(tname);

        selection = JOptionPane.showConfirmDialog(null, p, "Nuevo departamento ", JOptionPane.OK_CANCEL_OPTION);
        if(selection == OK_OPTION){
            if(tname.getText().equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Favor de asignar nombre al departamento",
                        "Departamento sin nombre",
                        JOptionPane.WARNING_MESSAGE
                );
                onUpDep(depId);
            } else  {
                dep.setName(tname.getText());

                JOptionPane.showMessageDialog(
                        null,
                        "El nombre del departamento ha sido actualizado con exito",
                        "Exito al actualizar nombre del departamento",
                        INFORMATION_MESSAGE
                );
            }
        }

    }

    static void onDelItem(int itemId){
        int selection;
        Item item = database.getItems().get(DBCheck.existThisIdIn(itemId, database.getItems()));

        selection = JOptionPane.showConfirmDialog(
                null,
                "Esto eliminara la deuda a cualquier empelado sobre este articulo\n\n" +
                        "\u00BFSeguro que desea eliminar el articulo...?\n" +
                        item.toString(),
                "Eliminando articulo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE
        );

        if(selection == YES_OPTION){
            database.delItem(item);
            JOptionPane.showMessageDialog(
                    null,
                    "El articulo ha sido retirado del inventario con exito",
                    "Exito al retirar articulo",
                    INFORMATION_MESSAGE
            );
        }
    }

    static void onDelReg(int regId){
        int selection;
        Move move = database.getMoves().get(DBCheck.existThisIdIn(regId, database.getMoves()));

        selection = JOptionPane.showConfirmDialog(
                null,
                "Esto revertira los efectos del registro\n\n" +
                        "\u00BFSeguro que desea eliminar el registro " + move.getId() + "?\n",
                "Eliminando articulo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE
        );

        if(selection == YES_OPTION){
            database.delMove(move);

            JOptionPane.showMessageDialog(
                    null,
                    "La entrada del historial ha sido eliminada con exito",
                    "Exito al eliminar registro",
                    INFORMATION_MESSAGE
            );
        }
    }

    static void onDelEmp(int empId){
        int selection;
        Employee emp = database.getEmployees().get(DBCheck.existThisIdIn(empId, database.getEmployees()));

        selection = JOptionPane.showConfirmDialog(
                null,
                "Esto NO eliminara los registros en los que aparece el empleado\nEl empleado sera eliminado del departamento\n\n" +
                        "\u00BFSeguro que desea eliminar empleado...?\n" +
                        emp.toString(),
                "Eliminando articulo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE
        );

        if(selection == YES_OPTION){
            database.delEmployee(emp);
            JOptionPane.showMessageDialog(
                    null,
                    "El empleado ha sido de baja con exito",
                    "Exito dar de baja empleado",
                    INFORMATION_MESSAGE
            );
        }
    }

    static void onDelDep(int depId){
        int selection;
        Department dep = database.getDepartments().get(DBCheck.existThisIdIn(depId, database.getDepartments()));

        selection = JOptionPane.showConfirmDialog(
                null,
                "Los empleados del departamento pasaran a no tener uno asignado\n\n" +
                        "\u00BFSeguro que desea eliminar el departamento...?\n" +
                        dep.toString(),
                "Eliminando articulo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE
        );

        if(selection == YES_OPTION){
            database.delDepartment(dep);
            JOptionPane.showMessageDialog(
                    null,
                    "El departamento ha sido dado de baja con exito",
                    "Exito al dar de baja departamento",
                    INFORMATION_MESSAGE
            );
        }
    }
}
