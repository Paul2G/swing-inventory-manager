package database.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Employee extends Enty implements Serializable {
    private String RFC;
    private String lastName;
    private Department department;
    private List<Move> moves = new ArrayList<Move>();
    private List<Item> debt = new ArrayList<Item>();

    //Constructores
    public Employee(int id, String rfc, String lastName, String name)
    {
        super(id, name);
        this.RFC = rfc;
        this.lastName = lastName;
    }

    public Employee(int id, String rfc, String lastName, String name, Department department)
    {
        super(id, name);
        this.RFC = rfc;
        this.lastName = lastName;
        this.department = department;
    }

    public Employee (int id, String name, String lastName){
        super(id, name);
        this.lastName = lastName;
    }

    //Getters
    public String getRFC() {
        return RFC;
    }

    public String getLastName(){
        return lastName;
    }

    public String getFullName(){
        return lastName + " " + super.getName();
    }

    public Department getDepartment() {
        return department;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public List<Item> getDebt(){
        return  debt;
    }

    //Setters
    public void setFullName(String lastName, String name){
        this.lastName = lastName;
        super.setName(name);
    }

    public void setRFC(String rfc)
    {
        this.RFC = rfc;
    }

    public void setDepartament(Department department) {
        this.department = department;
    }

    public void addDebt (Item item){
        debt.add(item);
    }

    @Override
    public String toString() {
        return "[ID: " + super.getId() +
                "] " +
                getFullName();
    }
}
