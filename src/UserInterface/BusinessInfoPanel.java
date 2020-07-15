package UserInterface;

import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import MainSystem.Business;
import MainSystem.BusinessDatabase;
import MainSystem.BusinessWebsite;

import java.awt.*;

public class BusinessInfoPanel extends JPanel {

    static GridBagConstraints gc = new GridBagConstraints();
    static Business currentBusiness;
    JLabel name;
    JLabel ticketsMade;
    JLabel income;
    public BusinessInfoPanel(String business) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder(business));
        gc.weighty = .1;

        try {
            currentBusiness = Business.getBusiness(business);
            gc.gridx = 0;
            gc.gridy = 0;
            name = new JLabel("Company: " + currentBusiness.getName());
            add(name, gc);
            gc.gridy = 1;
            ticketsMade = new JLabel("Tickets Made: " + currentBusiness.getNumberOfTicketsMade());
            add(ticketsMade, gc);
            gc.gridy = 2;
            income = new JLabel("Income from customer: $" + currentBusiness.calculateThisMonthPay());
            add(income, gc);
            
            gc.gridy = 3;
            add(new JLabel("Check Database Health:"), gc);

            int row = 4;
            for (BusinessDatabase db : BusinessDatabase.getDatabasesByBusiness(business)) {
                gc.gridy = row;
                JButton button = new DatabaseConnectionButton(db);
                add(button, gc);
                row++;
            } 
            
            gc.gridy = row;
            add(new JLabel("Check Website Health:"), gc);

            row++;
            for (BusinessWebsite site : BusinessWebsite.getWebsitesByBusiness(business)) {
                gc.gridy = row;
                JButton button = new WebsiteConnectionButton(site);
                add(button, gc);
                row++;
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}