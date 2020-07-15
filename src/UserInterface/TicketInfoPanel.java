package UserInterface;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import MainSystem.Customer;
import MainSystem.Employee;
import MainSystem.Ticket;

import java.awt.*;

public class TicketInfoPanel extends JPanel {

    static GridBagConstraints gc = new GridBagConstraints();
    static JTextField idField = new JTextField(10);
    static JTextField titleField = new JTextField(10);
    static JTextField descriptionField = new JTextField(10);
    static JTextField dateCreatedField = new JTextField(10);
    static JTextField businessField = new JTextField(10);
    static JTextField addEmployeeToTicketField = new JTextField(10);
    static StatusDropDownList statusField = new StatusDropDownList();
    JLabel title = new JLabel("Title: ");
    JLabel description = new JLabel("Description: ");
    JLabel dateCreated = new JLabel("Date Created: ");
    JLabel company = new JLabel("Company: ");
    JLabel status = new JLabel("Status: ");
    public static String companyString = " ";
    static int tiid;
    public static int cid;

    public TicketInfoPanel(int Tiid) {
        setLayout(new GridBagLayout());
        tiid = Tiid;
        try {
            ArrayList<Employee> employeesAssigned;
            Ticket currentTicket;
            gc.weighty = .1;
            if (Tiid != 0) {
                currentTicket = Ticket.getTicket(tiid);
                companyString = currentTicket.business;
                titleField = new JTextField(currentTicket.getTitle(), 12);
                descriptionField = new JTextField(currentTicket.getDescription(), 12);
                dateCreatedField = new JTextField(currentTicket.getStartDate(), 12);
                employeesAssigned = currentTicket.getEmployees();
                company = new JLabel("Company:  " + currentTicket.business);
                Customer creator = Customer.getCustomer(currentTicket.createdById);
                cid = creator.getIdentifier();

                if (MainFrame.currentRole < 3) {
                    statusField.setSelectedItem(currentTicket.getStatus());
                    title = new JLabel("Title:  " + currentTicket.getTitle());
                    description = new JLabel("Description:  " + currentTicket.getDescription());
                    dateCreated = new JLabel("Date Created: " + currentTicket.getStartDate());
                    gc.gridwidth = 1;
                    gc.gridx = 0;
                    gc.gridy = 11;
                    add(new JLabel("Employees Assigned: "), gc);
                    int i;
                    for (i = 0; i < employeesAssigned.size(); i++) {
                        gc.gridx = 1;
                        gc.gridy = 11 + i;
                        String fullName = employeesAssigned.get(i).getFirstName() + " "
                                + employeesAssigned.get(i).getLastName();
                        add(new JLabel(fullName), gc);
                    }
                    if (MainFrame.currentRole == 1) {
                        gc.gridx = 0;
                        gc.gridy = 12 + i;
                        JLabel addEmployeeToTicketLabel = new JLabel("Employee Id: ");
                        add(addEmployeeToTicketLabel, gc);
                        gc.gridx = 1;
                        addEmployeeToTicketField = new JTextField(12);
                        add(addEmployeeToTicketField, gc);

                        gc.gridwidth = 2;
                        gc.gridx = 0;
                        gc.gridy = 13 + i;
                        add(new AddEmployeeToTaskButton(Tiid, false), gc);
                    }
                } else {
                    company = new JLabel("Company:  " + Customer.getCustomer(MainFrame.currentUser).getBusiness());
                    status.setText(status.getText() + " " + currentTicket.getStatus());

                    gc.gridwidth = 1;
                    gc.gridx = 0;
                    gc.gridy = 11;
                    add(new JLabel("Created by: "), gc);
                    gc.gridx = 1;
                    String fullNameOfCreator = creator.getFirstName() + " " + creator.getLastName();
                    add(new JLabel(fullNameOfCreator), gc);
                }
            } else {
                idField = new JTextField(12);
                titleField = new JTextField(12);
                descriptionField = new JTextField(12);
                dateCreatedField = new JTextField(12);
                companyString = Customer.getCustomer(MainFrame.currentUser).getBusiness();
                cid = MainFrame.currentUser;
            }
            JLabel id = new JLabel(tiid == 0 ? "Id: " : "Id: " + tiid);

            if (MainFrame.currentRole == 3)
                gc.gridwidth = 1;
            else
                gc.gridwidth = 2;
            gc.gridx = 0;
            gc.gridy = 0;
            add(id, gc);
            gc.gridy = 1;
            add(title, gc);
            gc.gridy = 2;
            add(description, gc);
            gc.gridy = 3;
            add(dateCreated, gc);

            gc.gridx = 1;
            if (tiid == 0) {
                gc.gridy = 0;
                add(idField, gc);
            }
            if (MainFrame.currentRole == 3) {
                gc.gridy = 1;
                add(titleField, gc);
                gc.gridy = 2;
                add(descriptionField, gc);
                gc.gridy = 3;
                add(dateCreatedField, gc);
                gc.gridx = 0;
                gc.gridy = 4;
                gc.gridwidth = 2;
                add(status, gc);
            } else {
                gc.gridwidth = 1;
                gc.gridx = 0;
                gc.gridy = 4;
                add(status, gc);
                gc.gridx = 1;
                add(statusField, gc);
            }

            gc.gridx = 0;
            gc.gridy = 5;
            gc.gridwidth = 2;
            add(company, gc);

            gc.gridx = 0;
            gc.gridy = 8;
            add(new SaveTicketButton("Save Ticket", tiid == 0), gc);

        } catch (IOException e) {
            e.printStackTrace();
        }

        setBorder(BorderFactory.createTitledBorder("Ticket"));
    }

    public static ArrayList<String> getNewTicketFields() {
        ArrayList<String> ticketFields = new ArrayList<String>();
        ticketFields.add(tiid == 0 ? idField.getText() : Integer.toString(tiid));
        ticketFields.add(titleField.getText());
        ticketFields.add(descriptionField.getText());
        ticketFields.add("_");
        ticketFields.add(dateCreatedField.getText());
        ticketFields.add("_");
        ticketFields.add("_");
        ticketFields.add(companyString);
        ticketFields.add(Integer.toString(cid));
        ticketFields.add(TicketInfoPanel.statusField.getSelectedItem().toString());
        return ticketFields;
    }

    public static int employeeIdToAddToTicket() {
        return Integer.parseInt(addEmployeeToTicketField.getText());
    }
}