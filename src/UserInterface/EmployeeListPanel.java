package UserInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import MainSystem.Customer;

import java.awt.*;

public class EmployeeListPanel extends JPanel {
    static JTextField id = new JTextField(5);;
    static JTextField firstname = new JTextField(10);;
    static JTextField lastname = new JTextField(10);;
    static JTextField email = new JTextField(10);;
    static GridBagConstraints gc = new GridBagConstraints();
    static boolean showError = false;

    public EmployeeListPanel() {
        if (!showError) {
            id = new JTextField(5);
            firstname = new JTextField(10);
            lastname = new JTextField(10);
            email = new JTextField(10);
        }

        setBorder(BorderFactory.createTitledBorder("Employees"));

        /*
         * Reads employee names from the text file into a "names" arraylist
         */
        int row = 1;
        if (MainFrame.currentRole < 3) {
            ArrayList<String> names = new ArrayList<String>();
            ArrayList<String> ids = new ArrayList<String>();
            File f = new File("../IT_Management/database/employee.txt");
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(f));
                String line;
                String id;
                String name;
                while ((line = br.readLine()) != null) {
                    String[] words = line.split(" ");
                    id = words[0];
                    ids.add(id);
                    name = words[1] + " " + words[2];
                    names.add(name);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
             * Iterates through "names" and adds a button for each employee to the panel
             */
            setLayout(new GridBagLayout());

            for (int i = 0; i < names.size(); i++) {
                gc.weightx = 1;
                gc.gridwidth = 2;
                gc.gridx = 0;
                gc.gridy = row;
                gc.anchor = GridBagConstraints.FIRST_LINE_START;
                add(new EmployeeButton(names.get(i), Integer.parseInt(ids.get(i))), gc);
                row++;
            }
        } else {
            setPreferredSize(new Dimension(100, 400));
            String business = "";
            ArrayList<String> names = new ArrayList<String>();
            ArrayList<String> ids = new ArrayList<String>();
            File f = new File("../IT_Management/database/customer.txt");
            BufferedReader br;
            try {
                business = Customer.getCustomer(MainFrame.currentUser).getBusiness();
                br = new BufferedReader(new FileReader(f));
                String line;
                String id;
                String name;
                while ((line = br.readLine()) != null) {
                    String[] words = line.split(" ");
                    if (words[4].equals(business)) {
                        id = words[0];
                        ids.add(id);
                        name = words[1] + " " + words[2];
                        names.add(name);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
             * Iterates through "names" and adds a button for each employee to the panel
             */
            setLayout(new GridBagLayout());

            for (int i = 0; i < names.size(); i++) {
                gc.weightx = 1;
                gc.gridwidth = 2;
                gc.gridx = 0;
                gc.gridy = row;
                gc.anchor = GridBagConstraints.FIRST_LINE_START;
                add(new JLabel(names.get(i)), gc);
                row++;
            }
        }

        if (MainFrame.currentRole == 1) {
            gc.weightx = 1;
            gc.gridx = 0;
            gc.gridy = row;
            gc.anchor = GridBagConstraints.CENTER;
            gc.weighty = (16 - row);
            gc.gridwidth = 2;
            add(new AddEmployeeButton("Add Employee"), gc);

            JLabel idLabel = new JLabel("ID: ");
            JLabel firstnameLabel = new JLabel("First Name: ");
            JLabel lastnameLabel = new JLabel("Last Name: ");
            JLabel emailLabel = new JLabel("Email: ");

            gc.anchor = GridBagConstraints.WEST;
            gc.weighty = 1;

            gc.gridy = 16;
            gc.gridx = 0;
            if (showError) {
                JLabel errorMessage = new JLabel("One or more fields are empty");
                errorMessage.setForeground(Color.RED);
                add(errorMessage, gc);
            }
            showError = false;

            gc.gridwidth = 1;
            gc.gridy = 17;
            gc.gridx = 0;
            add(idLabel, gc);
            gc.gridx = 1;
            add(id, gc);

            gc.gridy = 18;
            gc.gridx = 0;
            add(firstnameLabel, gc);
            gc.gridx = 1;
            add(firstname, gc);

            gc.gridy = 19;
            gc.gridx = 0;
            add(lastnameLabel, gc);
            gc.gridx = 1;
            add(lastname, gc);

            gc.gridy = 20;
            gc.gridx = 0;
            add(emailLabel, gc);
            gc.gridx = 1;
            add(email, gc);
        }
    }

    public static ArrayList<String> getNewEmployeeFields() {
        ArrayList<String> newEmployeeFields = new ArrayList<String>();
        newEmployeeFields.add(id.getText());
        newEmployeeFields.add(firstname.getText());
        newEmployeeFields.add(lastname.getText());
        newEmployeeFields.add(email.getText());
        newEmployeeFields.add("0");
        newEmployeeFields.add("0");
        newEmployeeFields.add("0");
        return newEmployeeFields;
    }

    public static void showErrorMessage() {
        showError = true;
    }

    public static void hideErrorMessage() {
        showError = false;
    }

}