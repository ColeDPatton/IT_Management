package UserInterface;

import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import MainSystem.BusinessDatabase;

public class DatabaseConnectionButton extends JButton {
    public DatabaseConnectionButton(BusinessDatabase db) {
        super(db.getDBName());

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                boolean success = db.testConnection();
                if (success) {
                    setBackground(Color.green);
                    setOpaque(true);
                } else {
                    setBackground(Color.red);
                    setOpaque(true);
                }
            }
        });
    }
}