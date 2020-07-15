package UserInterface;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import MainSystem.Employee;
import MainSystem.IT_Personnel;
import MainSystem.Project;

public class AddProjectButton extends JButton {
    public AddProjectButton(String name) {
        super(name);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ArrayList<String> projectFields = ProjectListPanel.getNewProjectFields();
                for (int i = 0; i < projectFields.size(); i++) {
                    if(projectFields.get(i).isEmpty()) {
                        ProjectListPanel.showErrorMessage();
                        App.frame.refreshProjectListPanel();
                        return;
                    }
                }
                Project newproject = new Project(Integer.parseInt(projectFields.get(0)),projectFields.get(1),projectFields.get(2),projectFields.get(3),projectFields.get(4),projectFields.get(5),Integer.parseInt(projectFields.get(6)));
                Project.addToDoToFile(newproject, "IT_Management/database/project.txt");
                App.frame.refreshProjectListPanel();
            }
        });
    }
}