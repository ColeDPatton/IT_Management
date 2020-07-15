package UserInterface;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {
    public static MainFrame frame;
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                frame = new MainFrame("IT Management");
                frame.setSize(1000, 500);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}