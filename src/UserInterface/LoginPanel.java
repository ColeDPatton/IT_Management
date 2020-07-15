package UserInterface;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import MainSystem.Customer;
import MainSystem.Employee;
import MainSystem.ProjectManager;
import UserInterface.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    static GridBagConstraints gc = new GridBagConstraints();

    public LoginPanel() {
        setLayout(new GridBagLayout());

        setBorder(BorderFactory.createTitledBorder("Login"));

        JLabel userTypeLabel = new JLabel("User: ");
        JComboBox<String> userType = new JComboBox<String>(new String[] { "Manager", "Employee", "Customer" });

        JLabel idLabel = new JLabel("Id: ");
        JTextField id = new JTextField(10);

        gc.gridx = 0;
        gc.gridy = 0;
        add(userTypeLabel, gc);
        gc.gridx = 1;
        add(userType, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        add(idLabel, gc);
        gc.gridx = 1;
        add(id, gc);

        JButton loginButton = new JButton("Log In");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userType.getSelectedItem().toString();
                switch (user) {
                case "Manager":
                    ArrayList<ProjectManager> projectManagers;
                    try {
                        projectManagers = ProjectManager.getAllProjectManagers();
                        for (ProjectManager projectManager : projectManagers) {
                            if (Integer.toString(projectManager.getIdentifier()).equals(id.getText())) {
                                App.frame.login(1, projectManager.getIdentifier());
                            }
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "Employee":
                    ArrayList<Employee> employees = Employee.getAllEmployees();
                    for (Employee employee : employees) {
                        if (Integer.toString(employee.getIdentifier()).equals(id.getText())) {
                            App.frame.login(2, employee.getIdentifier());
                        }
                    }
                    break;
                case "Customer":
                    ArrayList<Customer> customers = Customer.getAllCustomers();
                    for (Customer customer : customers) {
                        if (Integer.toString(customer.getIdentifier()).equals(id.getText())) {
                            App.frame.login(3, customer.getIdentifier());
                        }
                    }
                    break;
                }

            }
        });
        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridwidth = 2;
        add(loginButton, gc);
    }
}