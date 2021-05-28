package database.storage;

import java.io.Serializable;

public class Enty implements Serializable {
    private int id;
    private String name;

    public Enty(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Enty(int id){
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
