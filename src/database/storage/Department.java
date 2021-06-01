package database.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Department extends Enty implements Serializable {
    private List<Employee> employees = new ArrayList<Employee>();

    public Department(int id, String name) {
        super(id, name);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public String toString() {
        return "[Num: " + super.getId() +
                "] " +
                super.getName();
    }
}