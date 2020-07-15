package UserInterface;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import MainSystem.Business;
import MainSystem.Employee;
import MainSystem.IT_Personnel;
import MainSystem.Ticket;
import MainSystem.Task.Statuses;

public class SaveTicketButton extends JButton {
    public SaveTicketButton(String name, Boolean newTicket) {
        super(name);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ArrayList<String> TicketFields = TicketInfoPanel.getNewTicketFields();
                if (newTicket) {
                    Ticket newTicket = new Ticket(Integer.parseInt(TicketFields.get(0)), TicketFields.get(1),
                            TicketFields.get(2), TicketFields.get(3), TicketFields.get(4), TicketFields.get(5), 0,
                            TicketFields.get(7), Integer.parseInt(TicketFields.get(8)), Statuses.stringToStatus(TicketFields.get(9)));
                    Ticket.addTicketToFile(newTicket);
                    try {
                        Business.getBusiness(TicketFields.get(7)).incrementTickets();
                    } catch (IOException e) {
                        System.out.println("Problem incrementing ticket");
                    }
                } else {
                    try {
                        Ticket.updateTicketInFile(new Ticket(Integer.parseInt(TicketFields.get(0)), TicketFields.get(1),
                                TicketFields.get(2), TicketFields.get(3), TicketFields.get(4), TicketFields.get(5), 0,
                                TicketFields.get(7), Integer.parseInt(TicketFields.get(8)), Statuses.stringToStatus(TicketFields.get(9))));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                App.frame.refreshMainPanel();
            }
        });
    }
}