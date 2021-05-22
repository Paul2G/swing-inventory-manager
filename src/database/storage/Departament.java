package database.storage;

import java.util.ArrayList;
import java.util.List;

public class Departament {
    private int id;
    private String name;
    private List<Employee> employees = new ArrayList<Employee>();

    public Departament(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }
}