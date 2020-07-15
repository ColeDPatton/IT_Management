package UserInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class TicketButton extends JButton {
    public TicketButton(String name, int Tiid) {
        super(name);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                MainFrame.selectedEmployee = 0;
                App.frame.refreshMainPanel();
                App.frame.showTicketInfoPanel(Tiid);
            }
        });
    }
}