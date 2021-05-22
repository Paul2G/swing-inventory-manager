import javax.swing.*;

public class InventoryGUI extends JFrame {
    private JPanel contentPane;
    private JTabbedPane pestanas;
    private JTable table1;

    public InventoryGUI(){
        setContentPane(contentPane);
    }

    public static void main(String[] args) {
        InventoryGUI frame = new InventoryGUI();
        frame.pack();
        frame.setIconImage(new ImageIcon("src/resources/icon.png").getImage());
        frame.setTitle("Inventario - Electronica");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);


        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Throwable e) {
            //e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(frame);

        frame.setVisible(true);
    }
}
