package UserInterface;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import MainSystem.Employee;
import MainSystem.Task;
import MainSystem.Ticket;
import MainSystem.Task.Statuses;

public class TasksByStatusPanel extends JPanel {
    public TasksByStatusPanel() {

        setLayout(new GridBagLayout());
        // setBackground(new Color(50, 50, 75));

        GridBagConstraints gc = new GridBagConstraints();

        JPanel upcomingPanel = new JPanel();
        JPanel blockedPanel = new JPanel();
        JPanel inProgressPanel = new JPanel();
        JPanel completedPanel = new JPanel();
        upcomingPanel.setBorder(BorderFactory.createTitledBorder("Upcoming Tasks"));
        blockedPanel.setBorder(BorderFactory.createTitledBorder("Blocked Tasks"));
        inProgressPanel.setBorder(BorderFactory.createTitledBorder("In Progress Tasks"));
        completedPanel.setBorder(BorderFactory.createTitledBorder("Completed Tasks"));
        upcomingPanel.setPreferredSize(new Dimension(150, 400));
        blockedPanel.setPreferredSize(new Dimension(150, 400));
        inProgressPanel.setPreferredSize(new Dimension(150, 400));
        completedPanel.setPreferredSize(new Dimension(150, 400));

        if (MainFrame.currentRole < 3) {
            ArrayList<String> ids = new ArrayList<String>();
            ArrayList<String> startDates = new ArrayList<String>();
            ArrayList<String> statuses = new ArrayList<String>();
            File f = new File("../IT_Management/database/task.txt");
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(f));
                String line;
                String id;
                String startDate;
                String status;
                while ((line = br.readLine()) != null) {
                    String[] words = line.split(" ");
                    if (words.length > 4) {
                        id = words[0];
                        ids.add(id);
                        startDate = words[4];
                        startDates.add(startDate);
                        status = words[7];
                        statuses.add(status);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int a = 0; a < ids.size(); a++) {
                TaskButton tButton = new TaskButton("Task: " + ids.get(a), Integer.parseInt(startDates.get(a)),
                        Integer.parseInt(ids.get(a)));

                if (MainFrame.selectedEmployee > 0) {
                    try {
                        Task currentTask = Task.getTask(Integer.parseInt(ids.get(a)));
                        ArrayList<Employee> e = currentTask.getEmployees();
                        for (int k = 0; k < e.size(); k++) {
                            if (MainFrame.selectedEmployee == e.get(k).getIdentifier()) {
                                tButton.setForeground(new Color(0x34e02b));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                switch (statuses.get(a)) {
                case "UPCOMING":
                    upcomingPanel.add(tButton);
                    break;
                case "INPROGRESS":
                    inProgressPanel.add(tButton);
                    break;
                case "BLOCKED":
                    blockedPanel.add(tButton);
                    break;
                case "COMPLETED":
                    completedPanel.add(tButton);
                    break;

                }
            }
        }

        // gc.weightx = .25;
        // gc.gridx = 0;
        // add(upcomingPanel, gc);
        // gc.gridx = 1;
        // add(blockedPanel, gc);
        // gc.gridx = 2;
        // add(inProgressPanel, gc);
        // gc.gridx = 3;
        // add(completedPanel, gc);

        ArrayList<String> ticketIds = new ArrayList<String>();
        ArrayList<String> datesCreated = new ArrayList<String>();
        ArrayList<String> ticketStatuses = new ArrayList<String>();
        ArrayList<String> businesses = new ArrayList<String>();
        File f = new File("../IT_Management/database/ticket.txt");
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(f));
            String line;
            String ticketId;
            String dateCreated;
            String status;
            String business;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                if (words.length > 8) {
                    ticketId = words[0];
                    ticketIds.add(ticketId);
                    dateCreated = words[4];
                    datesCreated.add(dateCreated);
                    business = words[7];
                    businesses.add(business);
                    status = words[9];
                    ticketStatuses.add(status);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int a = 0; a < ticketIds.size(); a++) {
            TicketButton tButton = new TicketButton("<html>" + businesses.get(a) + "<br>Ticket: " + ticketIds.get(a) + "</html>",
                    Integer.parseInt(ticketIds.get(a)));

            if (MainFrame.selectedEmployee > 0) {
                try {
                    Ticket currentTicket = Ticket.getTicket(Integer.parseInt(ticketIds.get(a)));
                    ArrayList<Employee> e = currentTicket.getEmployees();
                    for (int k = 0; k < e.size(); k++) {
                        if (MainFrame.selectedEmployee == e.get(k).getIdentifier()) {
                            tButton.setForeground(new Color(0x34e02b));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            switch (ticketStatuses.get(a)) {
            case "UPCOMING":
                upcomingPanel.add(tButton);
                break;
            case "INPROGRESS":
                inProgressPanel.add(tButton);
                break;
            case "BLOCKED":
                blockedPanel.add(tButton);
                break;
            case "COMPLETED":
                completedPanel.add(tButton);
                break;

            }
        }

        gc.weightx = .25;
        gc.gridx = 0;
        add(upcomingPanel, gc);
        gc.gridx = 1;
        add(blockedPanel, gc);
        gc.gridx = 2;
        add(inProgressPanel, gc);
        gc.gridx = 3;
        add(completedPanel, gc);
    }
}