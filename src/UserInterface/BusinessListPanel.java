package UserInterface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import MainSystem.Business;

public class BusinessListPanel extends JPanel {
    static GridBagConstraints gc = new GridBagConstraints();

    public BusinessListPanel() {

        setBorder(BorderFactory.createTitledBorder("Client Businesses"));

        setLayout(new GridBagLayout());

        this.setPreferredSize(new Dimension(200, 400));
        try {
            ArrayList<String> businesses = Business.getAllBusinesses();
            int row = 1;
            for (int i = 0; i < businesses.size(); i++) {
                gc.weightx = 1;
                gc.gridwidth = 2;
                gc.gridx = 0;
                gc.gridy = row;
                gc.anchor = GridBagConstraints.FIRST_LINE_START;
                add(new BusinessButton(businesses.get(i)), gc);
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}