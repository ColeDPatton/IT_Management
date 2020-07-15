package UserInterface;

import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import MainSystem.Comment;
import MainSystem.Employee;
import MainSystem.IT_Personnel;
import MainSystem.Notification;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class CommentInfoPanel extends JPanel {

    static GridBagConstraints gc = new GridBagConstraints();
    static ArrayList<Comment> currentComments;

    static JTextField replyMessage;
    static JTextField message;
    static int randomNum;
    static int replySpot;
    static int replyId;
    static int tid;

    public CommentInfoPanel(int Tid) {
        tid = Tid;
        setLayout(new GridBagLayout());
        gc.weightx = .3;
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.WEST;
        for (int i = 0; i < 3; i++) {
            gc.gridx = i;
            add(new JLabel(), gc);
        }
        try {
            currentComments = Comment.getAllCommentsByTask(Tid);

            int count = 0;
            for (Comment comment : currentComments) {
                JLabel messageL = new JLabel("Message: " + comment.getMessage());
                JLabel personsName = new JLabel("User: " + Employee.getEmployee(comment.getUserid()).getFirstName());
                gc.gridwidth = 3;
                gc.gridx = 0;
                
                if (comment.getReplySpot() == 1) {
                    gc.gridwidth = 2;
                    gc.gridx = 1;
                    messageL.setForeground(Color.gray);
                    personsName.setForeground(Color.gray);
                } else if (comment.getReplySpot() >= 2) {
                    gc.gridwidth = 1;
                    gc.gridx = 2;
                    messageL.setForeground(Color.red);
                    personsName.setForeground(Color.red);
                }
                gc.gridy = count;
                add(messageL, gc);
                gc.gridy += 1;
                add(personsName, gc);

                replySpot = comment.getReplySpot() + 1;
                replyId = comment.getCid();

                gc.gridy += 1;
                if (MainFrame.currentRole < 3) {
                    add(new AddReplyButton("Reply To Comment", Tid, replySpot, replyId), gc);
                }
                count = gc.gridy +1;
            }

            gc.anchor = GridBagConstraints.CENTER;
            gc.gridwidth = 3;
            gc.gridx = 0;
            gc.gridy += 1;
            if (MainFrame.currentRole < 3) {
                message = new JTextField(12);
                add(message, gc);

                gc.gridy += 1;
                add(new AddCommentButton("Add Comment", Tid), gc);
            }
            gc.gridy += 1;
            add(new TaskButton("View task", 0, Tid), gc);


        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public static Comment getNewReply(int replySpot, int replyId) throws IOException {
        Random rand = new Random();

        randomNum = rand.nextInt(10000);
        while (Comment.checkUnique(randomNum) != -1) {
            randomNum = rand.nextInt(10000);
        }

        return new Comment(randomNum, tid, MainFrame.currentUser, message.getText(), replySpot, replyId);
    }

    public static Comment getNewComment() throws IOException {
        Random rand = new Random();

        randomNum = rand.nextInt(10000);
        while (Comment.checkUnique(randomNum) != -1) {
            randomNum = rand.nextInt(10000);
        }

        return new Comment(randomNum, tid, MainFrame.currentUser, message.getText(), 0, -1);
    }
}