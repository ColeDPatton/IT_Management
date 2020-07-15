package UserInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import MainSystem.Project;
import MainSystem.Task;

public class ProjectInfoPanel extends JPanel {

    static GridBagConstraints gc = new GridBagConstraints();
    static JTextField idField = new JTextField(10);
    static JTextField titleField = new JTextField(10);
    static JTextField descriptionField = new JTextField(10);
    static JTextField categoryField = new JTextField(10);
    static JTextField startDateField = new JTextField(10);
    static JTextField endDateField = new JTextField(10);
    static JTextField budgetField = new JTextField(10);
    static JTextField addTaskToProjectField = new JTextField(10);

    static int prid;

    public ProjectInfoPanel(int Prid) {
        setLayout(new GridBagLayout());
        prid = Prid;
        Project currentProject = Project.getProjectById(Prid);
        titleField = new JTextField(currentProject.getTitle(), 12);
        descriptionField = new JTextField(currentProject.getDescription(), 12);
        categoryField = new JTextField(currentProject.getCategory(), 12);
        startDateField = new JTextField(currentProject.getStartDate(), 12);
        endDateField = new JTextField(currentProject.getEndDate(), 12);
        budgetField = new JTextField(Integer.toString(currentProject.getBudget()), 12);

        gc.gridwidth = 1;

        if (MainFrame.currentRole == 1) {
            gc.gridx = 0;
            gc.gridy = 10;
            JLabel addTaskToProjectLabel = new JLabel("Task Id: ");
            add(addTaskToProjectLabel, gc);
            gc.gridx = 1;
            addTaskToProjectField = new JTextField(12);
            add(addTaskToProjectField, gc);

            gc.gridy = 11;
            JButton addTaskToProject = new JButton("Add Task to Project");
            addTaskToProject.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!addTaskToProjectField.getText().isEmpty()) {
                        ArrayList<Task> task = new ArrayList<Task>();
                        try {
                            task.add(Task.getTask(Integer.parseInt(addTaskToProjectField.getText())));
                        } catch (NumberFormatException | IOException e1) {
                            e1.printStackTrace();
                        }
                        if (!task.isEmpty())
                            Project.addTaskToProjects(task, currentProject);
                    }
                }

            });
            add(addTaskToProject, gc);
        }
        JLabel id = new JLabel(prid == 0 ? "Id: " : "Id: " + prid);
        JLabel title = new JLabel("Title: ");
        JLabel description = new JLabel("Description: ");
        JLabel category = new JLabel("Category: ");
        JLabel startDate = new JLabel("Start Date: ");
        JLabel endDate = new JLabel("End Date: ");
        JLabel budget = new JLabel("Budget: ");

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        add(id, gc);
        gc.gridx = 0;
        gc.gridy = 1;
        add(title, gc);
        gc.gridx = 0;
        gc.gridy = 2;
        add(description, gc);
        gc.gridx = 0;
        gc.gridy = 3;
        add(category, gc);
        gc.gridx = 0;
        gc.gridy = 4;
        add(startDate, gc);
        gc.gridx = 0;
        gc.gridy = 5;
        add(endDate, gc);
        gc.gridx = 0;
        gc.gridy = 6;
        add(budget, gc);

        gc.gridx = 1;
        if (prid == 0) {
            gc.gridy = 0;
            add(idField, gc);
        }

        gc.gridy = 1;
        add(titleField, gc);
        gc.gridy = 2;
        add(descriptionField, gc);
        gc.gridy = 3;
        add(categoryField, gc);
        gc.gridy = 4;
        add(startDateField, gc);
        gc.gridy = 5;
        add(endDateField, gc);
        gc.gridy = 6;
        add(budgetField, gc);

        gc.gridx = 0;
        gc.gridy = 8;
        gc.gridwidth = 2;
        add(new SaveProjectButton("Save Project"), gc);

        setBorder(BorderFactory.createTitledBorder("Project"));

        ArrayList<Task> tasks = new ArrayList<Task>();
        try {
            tasks = Project.getTaskToProjects(currentProject);
            gc.gridx = 0;
            gc.gridy = 12;
            gc.gridwidth = 1;
            add(new JLabel("Tasks Assigned:"), gc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 12;
        gc.gridx = 1;
        for (Task task : tasks) {  
            gc.gridy = i;          
            add(new JLabel("Task: " + task.getTid()), gc);
            i++;
        }
    }

    public static Project getUpdatedProject() {
        return new Project(prid, titleField.getText(), descriptionField.getText(), categoryField.getText(),
                startDateField.getText(), endDateField.getText(), Integer.parseInt(budgetField.getText()));
    }

    public static int taskIdToAddToProject() {
        return Integer.parseInt(addTaskToProjectField.getText());
    }
}