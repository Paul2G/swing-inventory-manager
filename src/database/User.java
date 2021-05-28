package database;

import database.storage.Employee;

public class User extends Employee {
    private int level; //Nivel de privilegio
    private String password;

    public User(int id, String rfc, String lastName, String name, int level, String password) {
        super(id, rfc, lastName, name);
        this.level = level;
        this.password = password;
    }

    //Getters
    public int getLevel() {
        return level;
    }

    public String getPassword() {
        return password;
    }

    //Setters
    public void setLevel(int level) {
        this.level = level;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
