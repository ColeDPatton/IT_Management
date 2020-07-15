package UserInterface;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import MainSystem.Employee;
import MainSystem.IT_Personnel;
import MainSystem.Task;
import MainSystem.Task.Statuses;

public class SaveTaskButton extends JButton {
    public SaveTaskButton(String name, Boolean newTask) {
        super(name);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ArrayList<String> taskFields = TaskInfoPanel.getNewTaskFields();
                if (newTask) {
                    Task newTask = new Task(Integer.parseInt(taskFields.get(0)), taskFields.get(1), taskFields.get(2),
                            taskFields.get(3), taskFields.get(4), taskFields.get(5),
                            Integer.parseInt(taskFields.get(6)), Task.Statuses.UPCOMING);
                    newTask.setStatus((Statuses) TaskInfoPanel.statusField.getSelectedItem());
                    Task.addTaskToFile(newTask);
                } else {
                    try {
                        Task.updateTaskInFile(new Task(Integer.parseInt(taskFields.get(0)), taskFields.get(1),
                                taskFields.get(2), taskFields.get(3), taskFields.get(4), taskFields.get(5),
                                Integer.parseInt(taskFields.get(6)),
                                (Statuses) TaskInfoPanel.statusField.getSelectedItem()));
                        App.frame.refreshOptionsPanel();
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