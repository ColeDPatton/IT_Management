package UserInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import MainSystem.Project;

public class ProjectButton extends JButton {
    public ProjectButton(Project project) {
        super(project.getTitle());

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (MainFrame.selectedProject == project.getTid()) {
                    App.frame.hideEastPanel();
                } else {
                    App.frame.showProjectInfoPanel(project.getTid());
                }
                App.frame.refreshMainPanel();
            }
        });
    }
}