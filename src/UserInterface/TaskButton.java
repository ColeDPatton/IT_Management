package UserInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class TaskButton extends JButton {
    public TaskButton(String name, int date, int Tid) {
        super(name);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                MainFrame.selectedEmployee = 0;
                App.frame.refreshMainPanel();
                App.frame.showTaskInfoPanel(Tid);
            }
        });
    }
}