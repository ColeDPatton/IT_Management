package UserInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import MainSystem.Notification;
import RemoteDesktop.Client.SharingScreenPanel;
import RemoteDesktop.Server.RemoteClientViewPanel;

public class MainFrame extends JFrame {
    final Container c = getContentPane();

    static BusinessInfoPanel businessInfoPanel;
    static BusinessListPanel businessListPanel;
    static CalendarPanel calendarPanel;
    static CommentInfoPanel commentInfoPanel;
    static EmployeeInfoPanel employeeInfoPanel;
    static EmployeeListPanel employeeListPanel;
    static LoginPanel loginPanel = new LoginPanel();
    static JPanel notificationPanel;
    static OptionsPanel optionsPanel;
    static PayPanel payPanel = new PayPanel();
    static ProjectInfoPanel projectInfoPanel;
    static ProjectListPanel projectListPanel;
    static RemoteClientViewPanel remoteClientViewPanel;
    static SharingScreenPanel sharingScreenPanel;
    static TasksByStatusPanel tasksByStatusPanel = new TasksByStatusPanel();
    static TaskInfoPanel taskInfoPanel;
    static TicketInfoPanel ticketInfoPanel;

    static int selectedEmployee, currentUser, currentRole = 0;
    static int selectedProject = 0;
    // Roles: 1 = manager, 2 = employee, 3 = customer

    public MainFrame(String title) {
        super(title);

        setLayout(new BorderLayout());

        c.add(loginPanel, BorderLayout.WEST);
    }

    public void login(int role, int id) {
        currentUser = id;
        currentRole = role;

        calendarPanel = new CalendarPanel();
        employeeListPanel = new EmployeeListPanel();
        optionsPanel = new OptionsPanel();

        c.remove(loginPanel);
        c.remove(payPanel);
        c.revalidate();
        c.repaint();

        c.add(employeeListPanel, BorderLayout.WEST);
        c.add(calendarPanel, BorderLayout.CENTER);
        c.add(optionsPanel, BorderLayout.NORTH);
    }

    public void refreshOptionsPanel() {
        c.remove(optionsPanel);
        c.revalidate();
        c.repaint();
        optionsPanel = new OptionsPanel();
        c.add(optionsPanel, BorderLayout.NORTH);
    }

    public void refreshEmployeePanel() {
        c.remove(employeeListPanel);
        c.revalidate();
        c.repaint();
        employeeListPanel = new EmployeeListPanel();
        c.add(employeeListPanel, BorderLayout.WEST);
    }

    public void refreshMainPanel() {
        if (calendarPanel != null) {
            c.remove(calendarPanel);
            calendarPanel = new CalendarPanel();
            c.add(calendarPanel);
        } else {
            c.remove(tasksByStatusPanel);
            tasksByStatusPanel = new TasksByStatusPanel();
            c.add(tasksByStatusPanel);
        }
        c.revalidate();
        c.repaint();
    }

    public void switchMainPanel() {
        selectedEmployee = 0;
        hideBusinessInfoPanel();
        hideEmployeeInfoPanel();
        hideTaskInfoPanel();
        hideTicketInfoPanel();
        hideCommentInfoPanel();
        if (sharingScreenPanel != null && sharingScreenPanel.isShowing()) {
            c.remove(sharingScreenPanel);
            calendarPanel = new CalendarPanel();
            c.add(calendarPanel);
        } else if (remoteClientViewPanel != null && remoteClientViewPanel.isShowing()) {
            c.remove(remoteClientViewPanel);
            calendarPanel = new CalendarPanel();
            c.add(calendarPanel);
        } else if (calendarPanel != null) {
            c.remove(calendarPanel);
            calendarPanel = null;
            tasksByStatusPanel = new TasksByStatusPanel();
            c.add(tasksByStatusPanel);
        } else if (tasksByStatusPanel != null) {
            c.remove(tasksByStatusPanel);
            tasksByStatusPanel = null;
            if (currentRole == 1) {
                payPanel = new PayPanel();
                c.add(payPanel);
            } else if (sharingScreenPanel != null) {
                c.add(sharingScreenPanel);
            } else if (remoteClientViewPanel != null) {
                c.add(remoteClientViewPanel);
            } else {
                calendarPanel = new CalendarPanel();
                c.add(calendarPanel);
            }
        } else {
            if (payPanel != null) {
                c.remove(payPanel);
                payPanel = null;
            }
            if (sharingScreenPanel != null) {
                c.add(sharingScreenPanel);
            } else if (remoteClientViewPanel != null) {
                c.add(remoteClientViewPanel);
            } else {
                calendarPanel = new CalendarPanel();
                c.add(calendarPanel);
            }
        }
        c.revalidate();
        c.repaint();
    }

    public void switchWestPanel() {
        if (employeeListPanel != null) {
            c.remove(employeeListPanel);
            employeeListPanel = null;
            businessListPanel = new BusinessListPanel();
            c.add(businessListPanel, BorderLayout.WEST);
        } else if (businessListPanel != null) {
            c.remove(businessListPanel);
            businessListPanel = null;
            if (currentRole < 3) {
                projectListPanel = new ProjectListPanel();
                c.add(projectListPanel, BorderLayout.WEST);
            } else {
                employeeListPanel = new EmployeeListPanel();
                c.add(employeeListPanel, BorderLayout.WEST);
            }
        } else {
            c.remove(projectListPanel);
            projectListPanel = null;
            employeeListPanel = new EmployeeListPanel();
            c.add(employeeListPanel, BorderLayout.WEST);
        }
        c.revalidate();
        c.repaint();
    }

    public void showEmployeeListPanel() {
        if (employeeListPanel != null) {
            c.remove(employeeListPanel);
            employeeListPanel = null;
        } else {
            c.remove(businessListPanel);
            businessListPanel = null;
        }
        employeeListPanel = new EmployeeListPanel();
        c.add(employeeListPanel, BorderLayout.WEST);
        c.revalidate();
        c.repaint();
    }

    public void showBusinessListPanel() {
        if (employeeListPanel != null) {
            c.remove(employeeListPanel);
            employeeListPanel = null;
        } else {
            c.remove(businessListPanel);
            businessListPanel = null;
        }
        businessListPanel = new BusinessListPanel();
        c.add(businessListPanel, BorderLayout.WEST);
        c.revalidate();
        c.repaint();
    }

    public void refreshTaskInfoPanel(int Tid) {
        c.remove(taskInfoPanel);
        c.revalidate();
        c.repaint();
        taskInfoPanel = new TaskInfoPanel(Tid);
        c.add(taskInfoPanel, BorderLayout.EAST);
    }

    public void refreshCommentInfoPanel(int Tid) {
        c.remove(commentInfoPanel);
        c.revalidate();
        c.repaint();
        commentInfoPanel = new CommentInfoPanel(Tid);
        c.add(commentInfoPanel, BorderLayout.EAST);
    }

    public void refreshProjectListPanel() {
        c.remove(projectListPanel);
        c.revalidate();
        c.repaint();
        projectListPanel = new ProjectListPanel();
        c.add(projectListPanel, BorderLayout.WEST);
    }

    public void showEmployeeInfoPanel(int Eid) {
        hideEastPanel();
        selectedEmployee = Eid;
        employeeInfoPanel = new EmployeeInfoPanel(Eid);
        c.add(employeeInfoPanel, BorderLayout.EAST);
        c.revalidate();
        c.repaint();
    }

    public void showTaskInfoPanel(int Tid) {
        hideEastPanel();
        taskInfoPanel = new TaskInfoPanel(Tid);
        c.add(taskInfoPanel, BorderLayout.EAST);
        c.revalidate();
        c.repaint();
    }

    public void showBusinessInfoPanel(String business) {
        hideEastPanel();
        businessInfoPanel = new BusinessInfoPanel(business);
        c.add(businessInfoPanel, BorderLayout.EAST);
        c.revalidate();
        c.repaint();
    }

    public void showTicketInfoPanel(int Tiid) {
        hideEastPanel();
        ticketInfoPanel = new TicketInfoPanel(Tiid);
        c.add(ticketInfoPanel, BorderLayout.EAST);
        c.revalidate();
        c.repaint();
    }

    public void showCommentInfoPanel(int Tid) {
        hideEastPanel();
        commentInfoPanel = new CommentInfoPanel(Tid);
        c.add(commentInfoPanel, BorderLayout.EAST);
        c.revalidate();
        c.repaint();
    }

    public void showProjectInfoPanel(int Prid) {
        hideEastPanel();
        selectedProject = Prid;
        projectInfoPanel = new ProjectInfoPanel(Prid);
        c.add(projectInfoPanel, BorderLayout.EAST);
        c.revalidate();
        c.repaint();
    }

    public void hideEmployeeInfoPanel() {
        if (employeeInfoPanel != null)
            c.remove(employeeInfoPanel);
        employeeInfoPanel = null;
        c.revalidate();
        c.repaint();
    }

    public void hideTaskInfoPanel() {
        if (taskInfoPanel != null)
            c.remove(taskInfoPanel);
        taskInfoPanel = null;
        c.revalidate();
        c.repaint();
    }

    public void hideBusinessInfoPanel() {
        if (businessInfoPanel != null)
            c.remove(businessInfoPanel);
        businessInfoPanel = null;
        c.revalidate();
        c.repaint();
    }

    public void hideTicketInfoPanel() {
        if (ticketInfoPanel != null)
            c.remove(ticketInfoPanel);
        ticketInfoPanel = null;
        c.revalidate();
        c.repaint();
    }

    public void hideCommentInfoPanel() {
        if (commentInfoPanel != null)
            c.remove(commentInfoPanel);
        commentInfoPanel = null;
        c.revalidate();
        c.repaint();
    }

    public void hideProjectInfoPanel() {
        if (projectInfoPanel != null)
            c.remove(projectInfoPanel);
        projectInfoPanel = null;
        c.revalidate();
        c.repaint();
    }

    public void viewNotifications() {
        GridBagConstraints gc = new GridBagConstraints();
        if (notificationPanel != null) {
            c.remove(notificationPanel);
            notificationPanel = null;
        }
        notificationPanel = new JPanel();
        notificationPanel.setLayout(new GridBagLayout());

        try {
            ArrayList<Notification> notifications = Notification.getAllNotificationsByUser(currentUser);
            int i = 0;
            for (Notification notification : notifications) {
                gc.gridx = 0;
                gc.gridy = i;
                JLabel note = new JLabel(notification.getMessage());
                /*
                 * if (notification.getRead()) { note.setForeground(Color.gray); }
                 */
                notificationPanel.add(note, gc);
                gc.gridx = 1;
                String buttonText = notification.getRead() ? "Mark as Unread" : "Mark as Read";
                JButton markRead = new JButton(buttonText);
                markRead.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Notification.toggleRead(notification);
                        if (notification.getRead())
                            markRead.setText("Mark as Unread");
                        else
                            markRead.setText("Mark as Read");
                        refreshOptionsPanel();
                    }
                });
                notificationPanel.add(markRead, gc);
                gc.gridx = 2;
                JButton deleteNotification = new JButton("Delete");
                deleteNotification.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Notification.deleteNotificationInFile(notification);
                        viewNotifications();
                        refreshOptionsPanel();
                    }
                });
                notificationPanel.add(deleteNotification, gc);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        c.add(notificationPanel, BorderLayout.SOUTH);
        c.revalidate();
        c.repaint();
    }

    public void hideNotifications() {
        if (notificationPanel != null) {
            c.remove(notificationPanel);
            notificationPanel = null;
        }
        c.revalidate();
        c.repaint();
    }

    public void hideEastPanel() {
        selectedEmployee = 0;
        selectedProject = 0;
        if (ticketInfoPanel != null) {
            hideTicketInfoPanel();
        }
        if (taskInfoPanel != null) {
            hideTaskInfoPanel();
        }
        if (employeeInfoPanel != null) {
            hideEmployeeInfoPanel();
        }
        if (businessInfoPanel != null) {
            hideBusinessInfoPanel();
        }
        if (commentInfoPanel != null) {
            hideCommentInfoPanel();
        }
        if (projectInfoPanel != null) {
            hideProjectInfoPanel();
        }
    }

    public void viewClientDesktop() {
        selectedEmployee = 0;
        hideBusinessInfoPanel();
        hideEmployeeInfoPanel();
        hideTaskInfoPanel();
        hideTicketInfoPanel();
        hideCommentInfoPanel();
        if (calendarPanel != null) {
            c.remove(calendarPanel);
            calendarPanel = null;
        } else if (tasksByStatusPanel != null) {
            c.remove(tasksByStatusPanel);
            tasksByStatusPanel = null;
        } else {
            c.remove(payPanel);
            payPanel = null;
        }
        remoteClientViewPanel = new RemoteClientViewPanel();
        c.add(remoteClientViewPanel, BorderLayout.CENTER);
        c.revalidate();
        c.repaint();
    }

    public void requestAssistance() {
        selectedEmployee = 0;
        hideBusinessInfoPanel();
        hideEmployeeInfoPanel();
        hideTaskInfoPanel();
        hideTicketInfoPanel();
        hideCommentInfoPanel();
        if (calendarPanel != null) {
            c.remove(calendarPanel);
            calendarPanel = null;
        } else if (tasksByStatusPanel != null) {
            c.remove(tasksByStatusPanel);
            tasksByStatusPanel = null;
        } else {
            c.remove(payPanel);
            payPanel = null;
        }
        sharingScreenPanel = new SharingScreenPanel();
        c.add(sharingScreenPanel, BorderLayout.CENTER);
        c.revalidate();
        c.repaint();
    }

    public void endClientDesktopViewing() {
        if (remoteClientViewPanel != null) {
            remoteClientViewPanel.stopRemoteViewing();
            c.remove(remoteClientViewPanel);
            remoteClientViewPanel = null;
            calendarPanel = new CalendarPanel();
            c.add(calendarPanel, BorderLayout.CENTER);
        }
        c.revalidate();
        c.repaint();
    }

    public void endDesktopSharing() {
        if (sharingScreenPanel != null) {
            sharingScreenPanel.stopSharing();
            ;
            c.remove(sharingScreenPanel);
            sharingScreenPanel = null;
            calendarPanel = new CalendarPanel();
            c.add(calendarPanel, BorderLayout.CENTER);
        }
        c.revalidate();
        c.repaint();
    }
}