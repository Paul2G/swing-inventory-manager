import database.Database;

import javax.swing.*;

public class Start {
    public static void main(String[] args) {
        DatabaseGUI frame = new DatabaseGUI();
        frame.setIconImage(new ImageIcon("src/resources/icon.png").getImage());
        frame.setTitle("Inventario - Electronica");
        frame.setSize(900, 600);
        frame.setVisible(true);
    }
}
