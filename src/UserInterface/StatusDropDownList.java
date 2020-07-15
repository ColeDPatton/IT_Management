package UserInterface;

import java.awt.event.*;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import MainSystem.Task.Statuses;

public class StatusDropDownList extends JComboBox<Statuses>{
    public StatusDropDownList() {
        super(new Statuses[]{Statuses.UPCOMING, Statuses.INPROGRESS, Statuses.BLOCKED, Statuses.COMPLETED});
    }
}