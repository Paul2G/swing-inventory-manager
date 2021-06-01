package database.storage;

import org.w3c.dom.Entity;

import javax.naming.Name;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item extends Enty implements Serializable {
    private int qty;
    private int totalQty;
    private int cost;
    private List<Move> moves = new ArrayList<Move>();

    //Constructor
    public Item(int id, String name, int qty, int cost)
    {
        super(id, name);
        this.qty = qty;
        this.totalQty = qty;
        this.cost = cost;
    }

    public Item(int id, String name){
        super(id, name);
    }

    public Item (int id, int qty){
        super(id);
        this.totalQty = qty;
        this.qty = qty;
    }

    //getters
    public int getQty(){
        return qty;
    }

    public int getCost() {
        return cost;
    }

    public int getTotalQty(){
        return this.totalQty;
    }

    //setters
    public void addQty(int qty){
        this.qty+=qty;
    }

    public void setTotalQty(int totalQty){
        this.totalQty = totalQty;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Move> getMoves(){
        return moves;
    }

    @Override
    public String toString() {
        return "[Cod: " + super.getId() + "] " + super.getName();
    }
}
