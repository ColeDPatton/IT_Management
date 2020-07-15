package UserInterface;

import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import MainSystem.BusinessWebsite;

public class WebsiteConnectionButton extends JButton {
    public WebsiteConnectionButton(BusinessWebsite site) {
        super(site.getUrl());

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int response = site.testConnection();
                if (response == 200) {
                    setBackground(Color.green);
                    setOpaque(true);
                } else {
                    setBackground(Color.red);
                    setOpaque(true);
                }
            }
        });
    }
}