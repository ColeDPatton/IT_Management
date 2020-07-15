package UserInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import MainSystem.Customer;
import MainSystem.Employee;
import MainSystem.Notification;
import MainSystem.ProjectManager;

public class OptionsPanel extends JPanel {

    static GridBagConstraints gc = new GridBagConstraints();

    public OptionsPanel() {
        setLayout(new GridBagLayout());

        String name = "";
        try {
            if (MainFrame.currentRole == 1) {
                name = ProjectManager.getProjectManager(MainFrame.currentUser).getFirstName() + " "
                        + ProjectManager.getProjectManager(MainFrame.currentUser).getLastName();
            } else if (MainFrame.currentRole == 2) {
                name = Employee.getEmployee(MainFrame.currentUser).getFirstName() + " "
                        + Employee.getEmployee(MainFrame.currentUser).getLastName();
            } else {
                name = Customer.getCustomer(MainFrame.currentUser).getFirstName() + " "
                        + Customer.getCustomer(MainFrame.currentUser).getLastName();
            }
        } catch (Exception e) {
        }
        JLabel currentUser = new JLabel("Logged in as " + name);
        JButton switchView = new JButton("Switch View");
        switchView.setSize(50, 10);
        switchView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.frame.switchMainPanel();
            }
        });
        JButton showEmployeeButton = new JButton("Show Client List");
        showEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.frame.switchWestPanel();
                if (MainFrame.employeeListPanel != null)
                    showEmployeeButton.setText("Show Client List");
                else if (MainFrame.businessListPanel != null)
                    showEmployeeButton.setText("Show Project List");
                else
                    showEmployeeButton.setText("Show Employee List");
            }
        });

        // Used by IT personnel
        JButton viewClientDesktop = new JButton("Request Remote Access");
        viewClientDesktop.setSize(50, 10);
        viewClientDesktop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainFrame.remoteClientViewPanel == null) {
                    App.frame.viewClientDesktop();
                    viewClientDesktop.setText("Stop Remote Access");
                } else {
                    App.frame.endClientDesktopViewing();
                    viewClientDesktop.setText("Request Remote Access");
                }
            }
        });

        // Used by client
        JButton requestAssistance = new JButton("Request Assistance");
        requestAssistance.setSize(50, 10);
        requestAssistance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainFrame.sharingScreenPanel == null) {
                    App.frame.requestAssistance();
                    requestAssistance.setText("Stop Sharing Screen");
                } else {
                    App.frame.endDesktopSharing();
                    requestAssistance.setText("Request Assistance");
                }
            }
        });

        int numNotifications = 0;
        try {
            numNotifications = Notification.getUnreadCount(MainFrame.currentUser);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        JButton viewNotificationsButton = new JButton(numNotifications + " Unread Notifications");
        viewNotificationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainFrame.notificationPanel != null) {
                    App.frame.hideNotifications();
                    int numNots = 0;
                    try {
                        numNots = Notification.getUnreadCount(MainFrame.currentUser);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    String buttonText = numNots == 0 ? "No New " : numNots + " Unread ";
                    viewNotificationsButton.setText(buttonText + "Notifications");
                } else {
                    App.frame.viewNotifications();
                    viewNotificationsButton.setText("Hide Notifications");
                }
            }
        });


        gc.weightx = .25;
        gc.gridy = 0;
        gc.gridx = 1;
        add(currentUser, gc);
        if (MainFrame.currentRole < 3) {
            gc.gridx = 2;
            add(showEmployeeButton, gc);
        }
        gc.gridx = 3;
        add(switchView, gc);
        gc.gridx = 4;
        if (MainFrame.currentRole < 3) {
            add(viewClientDesktop, gc);
        } else {
            add(requestAssistance, gc);
        }
        gc.gridx = 5;
        add(viewNotificationsButton, gc);
        gc.gridx = 6;
    }
}