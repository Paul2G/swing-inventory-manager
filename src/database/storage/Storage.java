package database.storage;

import java.util.ArrayList;
import java.util.EnumMap;
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
    }

    public void addEmployee(Employee employee)
    {
        this.employees.add(employee);
    }

    public void delItem(Item item){
        this.items.remove(item);
    }

    public void delMove(Move move){
        this.moves.remove(move);
    }

    public void delEmployee(Employee employee){
        this.employees.remove(employee);
    }
}
