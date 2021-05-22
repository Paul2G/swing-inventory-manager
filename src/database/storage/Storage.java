package database.storage;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private List<Item> items = new ArrayList<Item>();
    private List<Move> moves = new ArrayList<Move>();
    private List<Employee> employees = new ArrayList<Employee>();

    //Constructor
    public Storage ()
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

    //Setters
    public void addItem(Item item)
    {
        this.items.add(item);
    }

    public void addMove(Move move)
    {
        this.moves.add(move);
        move.getEmployee().getMoves().add(move);
        move.getItem().getMoves().add(move);
    }

    public void addEmployee(Employee employee)
    {
        this.employees.add(employee);
        try{
            employee.getDepartament().getEmployees().add(employee);
        } catch (Exception e) {
            // I
        }

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
            employee.getDepartament().getEmployees().remove(employee);
        } catch (Exception e) {
            // E
        }
    }
}
