package UserInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class CommentButton extends JButton {
    public CommentButton(String name, int Tid) {
        super(name);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                MainFrame.selectedEmployee = 0;
                App.frame.refreshMainPanel();
                App.frame.showCommentInfoPanel(Tid);
            }
        });
    }
}