package UserInterface;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import MainSystem.Comment;
import MainSystem.Employee;

public class AddCommentButton extends JButton {
    public AddCommentButton(String name, int Tid) {
        super(name);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    Comment newComment = CommentInfoPanel.getNewComment();
                    if(!newComment.getMessage().isEmpty()){
                        Comment.addCommentToFile(newComment);
                        App.frame.refreshOptionsPanel();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                App.frame.refreshCommentInfoPanel(Tid);
            }
        });
    }
}