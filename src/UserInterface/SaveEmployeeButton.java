package UserInterface;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import MainSystem.Employee;

public class SaveEmployeeButton extends JButton {
    public SaveEmployeeButton(String name) {
        super(name);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Employee updatedEmployee = EmployeeInfoPanel.getUpdatedEmployee();
                try {
                    Employee.updateEmployeeInFile(updatedEmployee);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                App.frame.showEmployeeInfoPanel(updatedEmployee.getIdentifier());
            }
        });
    }
}