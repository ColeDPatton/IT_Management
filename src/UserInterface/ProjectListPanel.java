package UserInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import MainSystem.Business;
import MainSystem.Project;

public class ProjectListPanel extends JPanel {
    static GridBagConstraints gc = new GridBagConstraints();
    static JTextField id = new JTextField(5);
    static JTextField title = new JTextField(10);
    static JTextField description = new JTextField(10);
    static JTextField category = new JTextField(10);
    static JTextField startDate = new JTextField(10);
    static JTextField endDate = new JTextField(10);
    static JTextField budget = new JTextField(10);
    static boolean showError = false;
    
    public ProjectListPanel() {
        if (!showError) {
            id = new JTextField(5);
            title = new JTextField(10);
            description = new JTextField(10);
            category = new JTextField(10);
            startDate = new JTextField(10);
            endDate = new JTextField(10);
            budget = new JTextField(10);
        }
        setBorder(BorderFactory.createTitledBorder("Projects"));

        setLayout(new GridBagLayout());

        ArrayList<Project> projects = Project.GetAllProjects();
        int row = 1;
        for (int i = 0; i < projects.size(); i++) {
            gc.weightx = 1;
            gc.gridwidth = 2;
            gc.gridx = 0;
            gc.gridy = row;
            gc.anchor = GridBagConstraints.FIRST_LINE_START;
            add(new ProjectButton(projects.get(i)), gc);
            row++;
        }

        if (MainFrame.currentRole == 1) {
            gc.gridx = 0;
            gc.gridy = row;
            gc.anchor = GridBagConstraints.CENTER;
            gc.weighty = (16 - row);
            gc.gridwidth = 2;
            add(new AddProjectButton("Create New Project"), gc);

            JLabel idLabel = new JLabel("ID: ");
            JLabel titleLabel = new JLabel("Title: ");
            JLabel descriptionLabel = new JLabel("Description: ");
            JLabel categoryLabel = new JLabel("Category: ");
            JLabel startDateLabel = new JLabel("Start Date: ");
            JLabel endDateLabel = new JLabel("End Date: ");
            JLabel budgetLabel = new JLabel("Budget: ");

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
            add(titleLabel, gc);
            gc.gridx = 1;
            add(title, gc);

            gc.gridy = 19;
            gc.gridx = 0;
            add(descriptionLabel, gc);
            gc.gridx = 1;
            add(description, gc);

            gc.gridy = 20;
            gc.gridx = 0;
            add(categoryLabel, gc);
            gc.gridx = 1;
            add(category, gc);

            gc.gridy = 21;
            gc.gridx = 0;
            add(startDateLabel, gc);
            gc.gridx = 1;
            add(startDate, gc);

            gc.gridy = 22;
            gc.gridx = 0;
            add(endDateLabel, gc);
            gc.gridx = 1;
            add(endDate, gc);

            gc.gridy = 23;
            gc.gridx = 0;
            add(budgetLabel, gc);
            gc.gridx = 1;
            add(budget, gc);
        }
    }
    public static ArrayList<String> getNewProjectFields() {
        ArrayList<String> newEmployeeFields = new ArrayList<String>();
        newEmployeeFields.add(id.getText());
        newEmployeeFields.add(title.getText());
        newEmployeeFields.add(description.getText());
        newEmployeeFields.add(category.getText());
        newEmployeeFields.add(startDate.getText());
        newEmployeeFields.add(endDate.getText());
        newEmployeeFields.add(budget.getText());
        return newEmployeeFields;
    }

    public static void showErrorMessage() {
        showError = true;
    }

    public static void hideErrorMessage() {
        showError = false;
    }

}