package UserInterface;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import MainSystem.Customer;
import MainSystem.Employee;
import MainSystem.Project;
import MainSystem.Task;
import MainSystem.Ticket;

public class CalendarPanel extends JPanel {
    public CalendarPanel() {

        setLayout(new GridBagLayout());
        // setBackground(new Color(50, 50, 75));

        GridBagConstraints gc = new GridBagConstraints();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                gc.gridx = j;
                gc.gridy = i + 1;
                gc.weightx = .1;
                gc.weighty = .25;
                gc.anchor = GridBagConstraints.NORTHWEST;
                gc.insets = new Insets(0, 5, 0, 0);

                JLabel date = new JLabel(Integer.toString((i * 7) + (j + 1)));
                add(date, gc);

                if (MainFrame.currentRole <= 1) {
                    gc.insets = new Insets(15, 0, 0, 0);
                    TaskButton button = new TaskButton("Add task", (i * 7) + (j + 1), 0);
                    add(button, gc);
                }
                if (MainFrame.currentRole == 3) {
                    gc.insets = new Insets(15, 0, 0, 0);
                    TicketButton button = new TicketButton("Create Ticket", 0);
                    add(button, gc);
                }
            }
        }

        JLabel month = new JLabel("October");
        gc.weighty = .05;
        gc.weightx = 0.0;
        gc.gridwidth = 7;
        gc.gridy = 0;
        gc.gridx = 3;
        add(month, gc);

        if (MainFrame.currentRole < 3) {
            ArrayList<String> ids = new ArrayList<String>();
            ArrayList<String> projectIds = new ArrayList<String>();
            ArrayList<String> startDates = new ArrayList<String>();
            ArrayList<String> endDates = new ArrayList<String>();
            File f = new File("../IT_Management/database/task.txt");
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(f));
                String line;
                String id;
                String startDate;
                String endDate;
                Project selectedProject;
                if (MainFrame.selectedProject > 0) {
                    selectedProject = Project.getProjectById(MainFrame.selectedProject);
                    for (Task task : Project.getTaskToProjects(selectedProject)) {
                        projectIds.add(Integer.toString(task.getTid()));
                    }
                }
                while ((line = br.readLine()) != null) {
                    String[] words = line.split(" ");
                    if (words.length > 4) {
                        if (!projectIds.isEmpty() && !projectIds.contains(words[0])) {
                            continue;
                        }
                        id = words[0];
                        ids.add(id);
                        startDate = words[4];
                        startDates.add(startDate);
                        endDate = words[5];
                        endDates.add(endDate);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int a = 0; a < ids.size(); a++) {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 7; j++) {
                        TaskButton tButton = new TaskButton("Task: " + ids.get(a), (i * 7) + (j + 1),
                                Integer.parseInt(ids.get(a)));
                        gc.gridx = j;
                        gc.gridy = i + 1;
                        gc.insets = new Insets(40, 0, 0, 0);
                        if (Integer.parseInt(startDates.get(a)) == (i * 7) + (j + 1)) {
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
                            add(tButton, gc);
                        }
                        if (Integer.parseInt(endDates.get(a)) == (i * 7) + (j + 1)) {
                            JLabel tEnd = new JLabel("Task: " + ids.get(a) + " End Date");
                            add(tEnd, gc);
                        }
                    }
                }
            }
        }
        String business = "";
        ArrayList<String> ticketIds = new ArrayList<String>();
        ArrayList<String> createdDates = new ArrayList<String>();
        ArrayList<String> businesses = new ArrayList<String>();
        File f = new File("../IT_Management/database/ticket.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            String id;
            String createdDate;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                if (words.length > 7) {
                    if (MainFrame.currentRole == 3) {
                        business = Customer.getCustomer(MainFrame.currentUser).getBusiness();
                        if (!business.equals(words[7]))
                            continue;
                    } else {
                        business = words[7];
                    }
                    id = words[0];
                    ticketIds.add(id);
                    createdDate = words[4];
                    createdDates.add(createdDate);
                    business = words[7];
                    businesses.add(business);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (MainFrame.selectedProject == 0) {
            for (int a = 0; a < ticketIds.size(); a++) {
                TicketButton ticketButton = new TicketButton(
                        "<html>" + businesses.get(a) + "<br>Ticket: " + ticketIds.get(a) + "</html>",
                        Integer.parseInt(ticketIds.get(a)));
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 7; j++) {
                        gc.gridx = j;
                        gc.gridy = i + 1;
                        gc.insets = new Insets(40, 0, 0, 0);
                        if (Integer.parseInt(createdDates.get(a)) == (i * 7) + (j + 1)) {
                            if (MainFrame.selectedEmployee > 0) {
                                try {
                                    Ticket ticket = Ticket.getTicket(Integer.parseInt(ticketIds.get(a)));
                                    ArrayList<Employee> e = ticket.getEmployees();
                                    for (int k = 0; k < e.size(); k++) {
                                        if (MainFrame.selectedEmployee == e.get(k).getIdentifier()) {
                                            ticketButton.setForeground(new Color(0x34e02b));
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            add(ticketButton, gc);
                        }
                    }
                }
            }
        }
    }
}
