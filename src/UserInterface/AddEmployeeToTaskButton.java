package UserInterface;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import MainSystem.Employee;
import MainSystem.IT_Personnel;
import MainSystem.Task;
import MainSystem.Ticket;

public class AddEmployeeToTaskButton extends JButton {
    public AddEmployeeToTaskButton(int Tid, boolean task) {
        super("Add Employee By Id");

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    if (task) {
                        Task currentTask = Task.getTask(Tid);
                        currentTask.addSingleEmployee(Employee.getEmployee(TaskInfoPanel.employeeIdToAddToTask()));
                        App.frame.refreshTaskInfoPanel(Tid);
                    } else {
                        Ticket currentTicket = Ticket.getTicket(Tid);
                        currentTicket.addSingleEmployee(Employee.getEmployee(TicketInfoPanel.employeeIdToAddToTicket()));
                        App.frame.showTicketInfoPanel(Tid);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}