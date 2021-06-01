package database;

import database.storage.Department;
import database.storage.Employee;
import database.storage.Item;
import database.storage.Move;
import extraPackage.DBCheck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {
    private List<Item> items = new ArrayList<Item>();
    private List<Move> moves = new ArrayList<Move>();
    private List<Employee> employees = new ArrayList<Employee>();
    private List<Department> departments = new ArrayList<Department>();

    public static final int ERR_MOV$STOCK = -1;
    public static final int ERR_MOV$DEBT = 0;
    public static final int SUCCESS = 1;

    //Constructor
    public Database()
    {
        // A
    }

    //Getters
    public List<Item> getItems()
    {
        return items;
    }

    public List<Move> getMoves()
    {
        return moves;
    }

    public List<Employee> getEmployees()
    {
        return employees;
    }

    public List<Department> getDepartments(){
        return departments;
    }

    //Setters
    public void addItem(Item item)
    {
        this.items.add(item);
    }

    public int addMove(Move move)
    {
        int debtIndex = DBCheck.existThisIdIn(move.getItem().getId(), move.getEmployee().getDebt());

        if(move.getItem().getQty() < move.getQty())
            return ERR_MOV$STOCK;
        else if (debtIndex != -1){ //Si el empleado debe ese articulo
            if(move.getEmployee().getDebt().get(debtIndex).getQty() < -move.getQty()){ //Si el empleado quiere devolver mas de lo que debe
                return ERR_MOV$DEBT;
            } else {
                this.moves.add(move);
                move.getEmployee().getMoves().add(move);
                move.getItem().getMoves().add(move);
                move.getItem().addQty(-move.getQty());
                move.getEmployee().getDebt().get(debtIndex).addQty(move.getQty());

                return SUCCESS;
            }
        } else{ //Si el empelado nunca ha pedido ese material
            if(move.getQty() < 0){
                return ERR_MOV$DEBT;
            }else{
                this.moves.add(move);
                move.getEmployee().getMoves().add(move);
                move.getItem().getMoves().add(move);
                move.getItem().addQty(-move.getQty());
                move.getEmployee().addDebt(new Item(move.getItem().getId(), move.getQty()));

                return SUCCESS;
            }

        }

    }

    public void addEmployee(Employee employee)
    {
        this.employees.add(employee);
        try{
            employee.getDepartment().getEmployees().add(employee);
        } catch (Exception e) {
            // I
        }

    }

    public void addDepartment(Department department){
        this.departments.add(department);
    }

    public void delItem(Item item){
        int itemId = item.getId();

        //Borrando deuda de articulo de todos los empleados
        for(Employee emp : this.employees){
            for(Item item1 : emp.getDebt()){
                if(item1.getId() == itemId){
                    emp.getDebt().remove(item1);
                    break;
                }
            }
        }

        //Editando los registros
        for(Move move: this.moves){
            if(move.getItem() == item)
                move.setItem(new Item(-1, item.getName() + " [Eliminado]"));
        }

        //Eliminando articulo del inventario
        this.items.remove(item);
    }

    public void delMove(Move move){
        Item item = move.getItem();
        Employee emp = move.getEmployee();

        //Recuperando prestamo al inventario
        item.addQty(move.getQty());

        //Eliminando deuda del empleado sobre el articulo
        for(Item item0 : emp.getDebt()){
            if(item0.getId() == DBCheck.existThisIdIn(item.getId(), emp.getDebt())){
                item0.addQty(-move.getQty());
                break;
            }
        }

        //Eliminando de la lista de movmientos del empeleado
        emp.getMoves().remove(move);

        //Eliminando de la lista de movimientos del articulo
        item.getMoves().remove(move);

        //Eliminando movimiento de la base de datos
        moves.remove(move);
    }

    public void delEmployee(Employee employee){
        Department dep = employee.getDepartment();

        //Eliminando de los registros
        for (Move move : moves){
            if(employee == move.getEmployee())
                move.setEmployee(new Employee(-1, employee.getName() + " [Eliminado]", employee.getLastName()));
        }

        //Eliminando del departamento
        if(dep != null)
            dep.getEmployees().remove(employee);

        //Eliminando de la base de datos
        employees.remove(employee);
    }

    public void delDepartment(Department dep){
        //Eliminando empleados de departamento
        for(Employee emp : dep.getEmployees()){
            emp.setDepartament(null);
        }

        //Eliminando de partamento de base de datos
        departments.remove(dep);
    }
}
