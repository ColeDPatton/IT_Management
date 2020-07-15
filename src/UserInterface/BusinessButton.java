package UserInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class BusinessButton extends JButton {
    public BusinessButton(String business) {
        super(business);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                App.frame.showBusinessInfoPanel(business);
                App.frame.refreshMainPanel();
            }
        });
    }
}