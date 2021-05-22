package database.storage;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private int id;
    private String name;
    private int qty;
    private List<Move> moves = new ArrayList<Move>();

    //Constructor
    public Item(int id, String name)
    {
        this.id = id;
        this.name = name;
        this.qty = 0;
    }

    //Getters
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getQty(){
        return qty;
    }

    //Setters
    public void setName(String name){
        this.name = name;
    }

    public void addQty(int qty){
        this.qty+=qty;
    }

    public List<Move> getMoves(){
        return moves;
    }
}
