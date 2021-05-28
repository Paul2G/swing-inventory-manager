package database;

import database.storage.Department;
import database.storage.Employee;
import database.storage.Item;
import database.storage.Move;
import extraPackage.OpeAndCond;

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

    /*
       -1 para fracaso movmiento mayor a existencia
        0 para fracaso movimiento mayor a deuda
        1 para exito al añadir cuando ya habia una deuda del mismo objeto
        2 para añadir nuevo objeto a la deuda
    */
    public int addMove(Move move)
    {
        int debtIndex = OpeAndCond.existThisIdIn(move.getItem().getId(), move.getEmployee().getDebt());

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
                move.getEmployee().addDebt(new Item(move.getItem().getId(),
                                                    move.getItem().getName(),
                                                    move.getQty(),
                                                    move.getItem().getCost()));

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
        this.items.remove(item);
    }

    public void delMove(Move move){
        if(move.getItem().getQty() < move.getQty()){
            //Mensaje de error
        } else {
            this.moves.remove(move);
            move.getItem().getMoves().remove(move);
            move.getEmployee().getMoves().remove(move);
        }
    }

    public void delEmployee(Employee employee){
        this.employees.remove(employee);
        try {
            employee.getDepartment().getEmployees().remove(employee);
        } catch (Exception e) {
            // E
        }
    }
}
