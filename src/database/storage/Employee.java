package database.storage;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int id;
    private String name;
    private String lastName;
    private Departament departament;
    private List<Move> moves = new ArrayList<Move>();

    //Constructores
    public Employee(int id, String lastName, String name)
    {
        this.id = id;
        this.lastName = lastName;
        this.name = name;
    }

    public Employee(int id, String lastName, String name, Departament departament)
    {
        this.id = id;
        this.lastName = lastName;
        this.name = name;
        this.departament = departament;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getLastName(){
        return lastName;
    }

    public String getFullName(){
        return lastName + " " + name;
    }

    public Departament getDepartament() {
        return departament;
    }

    public List<Move> getMoves() {
        return moves;
    }

    //Setters
    public void setFullName(String lastName, String name){
        this.lastName = lastName;
        this.name = name;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    public void addMove(Move move){
        this.moves.add(move);
    }

    public void delMove(Move move){
        this.moves.remove(move);
    }
}
