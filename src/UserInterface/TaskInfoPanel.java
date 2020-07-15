package UserInterface;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import MainSystem.Employee;
import MainSystem.Task;
import MainSystem.Task.Statuses;

import java.awt.*;

public class TaskInfoPanel extends JPanel {

    static GridBagConstraints gc = new GridBagConstraints();
    static JTextField idField = new JTextField(10);
    static JTextField titleField = new JTextField(10);
    static JTextField descriptionField = new JTextField(10);
    static JTextField categoryField = new JTextField(10);
    static JTextField startDateField = new JTextField(10);
    static JTextField endDateField = new JTextField(10);
    static JTextField budgetField = new JTextField(10);
    static StatusDropDownList statusField = new StatusDropDownList();
    static JTextField addEmployeeToTaskField = new JTextField(10);

    static int tid;

    public TaskInfoPanel(int Tid) {
        setLayout(new GridBagLayout());
        tid = Tid;
        try {
            ArrayList<Employee> employees;
            Task currentTask;
            if (Tid != 0) {
                currentTask = Task.getTask(Tid);
                titleField = new JTextField(currentTask.getTitle(), 12);
                descriptionField = new JTextField(currentTask.getDescription(), 12);
                categoryField = new JTextField(currentTask.getCategory(), 12);
                startDateField = new JTextField(currentTask.getStartDate(), 12);
                endDateField = new JTextField(currentTask.getEndDate(), 12);
                budgetField = new JTextField(Integer.toString(currentTask.getBudget()), 12);
                statusField.setSelectedItem(currentTask.getStatus());
                employees = currentTask.getEmployees();

                gc.gridwidth = 1;
                gc.gridx = 0;
                gc.gridy = 11;
                add(new JLabel("Employees Assigned: "), gc);
                int i;
                for (i = 0; i < employees.size(); i++) {
                    gc.gridx = 1;
                    gc.gridy = 11 + i;
                    String fullName = employees.get(i).getFirstName() + " " + employees.get(i).getLastName();
                    add(new JLabel(fullName), gc);
                }

                if (MainFrame.currentRole == 1) {
                    gc.gridx = 0;
                    gc.gridy = 12 + i;
                    JLabel addEmployeeToTaskLabel = new JLabel("Employee Id: ");
                    add(addEmployeeToTaskLabel, gc);
                    gc.gridx = 1;
                    addEmployeeToTaskField = new JTextField(12);
                    add(addEmployeeToTaskField, gc);

                    gc.gridy = 13 + i;
                    add(new AddEmployeeToTaskButton(Tid, true), gc);
                }
                gc.gridy += 1;
                gc.gridx = 0;
                gc.gridwidth = 2;
                add(new CommentButton("View Comments", currentTask.getTid()),gc);
                gc.gridwidth = 1;
            } else {
                idField = new JTextField(12);
                titleField = new JTextField(12);
                descriptionField = new JTextField(12);
                categoryField = new JTextField(12);
                startDateField = new JTextField(12);
                endDateField = new JTextField(12);
                budgetField = new JTextField(12);
                statusField = new StatusDropDownList();
            }
            JLabel id = new JLabel(tid == 0 ? "Id: " : "Id: " + tid);
            JLabel title = new JLabel("Title: ");
            JLabel description = new JLabel("Description: ");
            JLabel category = new JLabel("Category: ");
            JLabel startDate = new JLabel("Start Date: ");
            JLabel endDate = new JLabel("End Date: ");
            JLabel budget = new JLabel("Budget: ");
            JLabel status = new JLabel("Status: ");

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
            gc.gridx = 0;
            gc.gridy = 7;
            add(status, gc);

            gc.gridx = 1;
            if (tid == 0) {
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
            gc.gridy = 7;
            add(statusField, gc);

            gc.gridx = 0;
            gc.gridy = 8;
            gc.gridwidth = 2;
            add(new SaveTaskButton("Save Task", tid == 0), gc);

        } catch (IOException e) {
            e.printStackTrace();
        }

        setBorder(BorderFactory.createTitledBorder("Task"));
    }

    public static ArrayList<String> getNewTaskFields() {
        ArrayList<String> taskFields = new ArrayList<String>();
        taskFields.add(tid == 0 ? idField.getText() : Integer.toString(tid));
        taskFields.add(titleField.getText());
        taskFields.add(descriptionField.getText());
        taskFields.add(categoryField.getText());
        taskFields.add(startDateField.getText());
        taskFields.add(endDateField.getText());
        taskFields.add(budgetField.getText());
        return taskFields;
    }

    public static int employeeIdToAddToTask() {
        return Integer.parseInt(addEmployeeToTaskField.getText());
    }
}