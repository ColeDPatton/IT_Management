package UserInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

import MainSystem.Project;

public class SaveProjectButton extends JButton {
    public SaveProjectButton(String name) {
        super(name);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Project updatedProject = ProjectInfoPanel.getUpdatedProject();
                try {
                    Project.updateProjectInFile(updatedProject);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                App.frame.showProjectInfoPanel(updatedProject.getTid());
            }
        });
    }
}