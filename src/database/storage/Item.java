package database.storage;

import org.w3c.dom.Entity;

import javax.naming.Name;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item extends Enty implements Serializable {
    private int qty;
    private int cost;
    private List<Move> moves = new ArrayList<Move>();

    //Constructor
    public Item(int id, String name, int qty, int cost)
    {
        super(id, name);
        this.qty = qty;
        this.cost = cost;
    }

    //getters
    public int getQty(){
        return qty;
    }

    public int getCost() {
        return cost;
    }

    //setters
    public void addQty(int qty){
        this.qty+=qty;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Move> getMoves(){
        return moves;
    }

    @Override
    public String toString() {
        return super.getId() + " - " + super.getName();
    }
}
