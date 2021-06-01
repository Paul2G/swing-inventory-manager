package database.storage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Move extends Enty implements Serializable {
    private Employee employee;
    private Item item;
    private int qty;
    private Date date;

    //Constuctores
    public Move(int id, Employee employee, Item item, int qty)
    {
        super(id);
        this.employee = employee;
        this.item = item;
        this.qty = qty;
        this.date = new Date();
    }

    public Move(int id, Employee employee, Item item, int qty, Date date)
    {
        super(id);
        this.employee = employee;
        this.item = item;
        this.qty = qty;
        this.date = date;
    }

    //Getters
    public Employee getEmployee() {
        return employee;
    }

    public Item getItem() {
        return item;
    }

    public int getQty(){
        return qty;
    }

    public String getDate() {
        String strDateFormat = "dd/MMMM/yyyy HH:mm:ss";
        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
        return objSDF.format(date);
    }

    //Setters
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setQty(int qty){
        this.qty = qty;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Move{" +
                "employee=" + employee +
                ", item=" + item +
                ", qty=" + qty +
                ", date=" + date +
                '}';
    }
}
