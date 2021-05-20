package database.storage;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private int id;
    private String name;
    private int qty;
    private List<Move> moves = new ArrayList<Move>();

    //Constructores
    public Item(int id, String name, int init_qty)
    {
        this.id = id;
        this.name = name;
        this.qty = init_qty;
    }

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

    public void addMove(Move move){
        this.moves.add(move);
    }

    public void delMove(Move move){
        this.moves.remove(move);
    }
}
