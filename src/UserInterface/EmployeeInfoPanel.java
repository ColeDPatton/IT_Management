package UserInterface;

import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import MainSystem.Employee;

import java.awt.*;

public class EmployeeInfoPanel extends JPanel {

    static GridBagConstraints gc = new GridBagConstraints();
    static Employee currentEmployee;
    static JTextField inputDaysWorked;
    static JTextField inputHoursWorked;
    static JTextField inputWage;

    public EmployeeInfoPanel(int Eid) {
        setLayout(new GridBagLayout());

        try {
            currentEmployee = Employee.getEmployee(Eid);
            setBorder(BorderFactory.createTitledBorder(currentEmployee.getFirstName()));

            double twoWeekPay = 2 * currentEmployee.getDaysWorked() * currentEmployee.getHoursWorked()
                    * currentEmployee.getWage();
            JLabel lName = new JLabel("Last Name: " + currentEmployee.getLastName());
            JLabel fName = new JLabel("First Name: " + currentEmployee.getFirstName());
            JLabel id = new JLabel("Employee Id: " + currentEmployee.getIdentifier());
            JLabel email = new JLabel("Email: " + currentEmployee.getEmail());
            JLabel daysWorked = new JLabel("Days Worked Per Week: ");
            JLabel hoursPerDay = new JLabel("Hours Worked Per Day: ");
            JLabel wage = new JLabel("Hourly Wage: " + currentEmployee.getWage());
            JLabel payPeriod = new JLabel("Biweekly Pay: " + twoWeekPay);

            inputDaysWorked = new JTextField(Integer.toString(currentEmployee.getDaysWorked()), 5);
            inputHoursWorked = new JTextField(Integer.toString(currentEmployee.getHoursWorked()), 5);
            inputWage = new JTextField(Double.toString(currentEmployee.getWage()), 5);

            gc.gridx = 0;
            gc.gridy = 0;
            add(fName, gc);
            gc.gridy = 1;
            add(lName, gc);
            gc.gridy = 2;
            add(id, gc);
            gc.gridy = 3;
            add(email, gc);
            gc.gridy = 4;
            add(daysWorked, gc);
            gc.gridx = 1;
            add(inputDaysWorked, gc);
            gc.gridx = 0;
            gc.gridy = 5;
            add(hoursPerDay, gc);
            gc.gridx = 1;
            add(inputHoursWorked, gc);
            gc.gridx = 0;
            gc.gridy = 6;
            add(wage, gc);
            gc.gridx = 1;
            add(inputWage, gc);
            gc.gridx = 0;
            gc.gridy = 7;
            add(payPeriod, gc);
            gc.gridy = 8;
            add(new SaveEmployeeButton("Update Employee"), gc);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static Employee getUpdatedEmployee() {
        return new Employee(currentEmployee.getIdentifier(), currentEmployee.getFirstName(),
                currentEmployee.getLastName(), currentEmployee.getEmail(), Integer.parseInt(inputDaysWorked.getText()),
                Integer.parseInt(inputHoursWorked.getText()), Double.parseDouble(inputWage.getText()));
    }
}