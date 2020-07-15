package UserInterface;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import MainSystem.Employee;
import MainSystem.IT_Personnel;

public class AddEmployeeButton extends JButton {
    public AddEmployeeButton(String name) {
        super(name);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ArrayList<String> employeeFields = EmployeeListPanel.getNewEmployeeFields();
                for (int i = 0; i < employeeFields.size(); i++) {
                    if(employeeFields.get(i).isEmpty()) {
                        EmployeeListPanel.showErrorMessage();
                        App.frame.refreshEmployeePanel();
                        return;
                    }
                }
                IT_Personnel newEmployee = new Employee(Integer.parseInt(employeeFields.get(0)),employeeFields.get(1),employeeFields.get(2),employeeFields.get(3),Integer.parseInt(employeeFields.get(4)),Integer.parseInt(employeeFields.get(5)),Double.parseDouble(employeeFields.get(6)));
                Employee.addITToFile(newEmployee, "IT_Management/database/employee.txt");
                App.frame.refreshEmployeePanel();
            }
        });
    }
}