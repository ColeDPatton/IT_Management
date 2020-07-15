package UserInterface;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import MainSystem.Comment;
import MainSystem.Employee;

public class AddReplyButton extends JButton {
    public AddReplyButton(String name, int Tid, int replySpot, int replyId) {
        super(name);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    Comment newReply = CommentInfoPanel.getNewReply(replySpot, replyId);
                    if (!newReply.getMessage().isEmpty())
                        Comment.addCommentToFile(newReply);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                App.frame.refreshCommentInfoPanel(Tid);
            }
        });
    }
}