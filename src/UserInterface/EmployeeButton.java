package UserInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class EmployeeButton extends JButton {
    public EmployeeButton(String name, int Eid) {
        super(name);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (MainFrame.selectedEmployee == Eid) {
                    App.frame.hideEastPanel();
                } else
                    App.frame.showEmployeeInfoPanel(Eid);
                App.frame.refreshMainPanel();
            }
        });
    }
}