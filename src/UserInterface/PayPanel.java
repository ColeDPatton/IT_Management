package UserInterface;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import MainSystem.Business;
import MainSystem.Employee;
import MainSystem.Task;

import java.awt.*;

public class PayPanel extends JPanel {

    static GridBagConstraints gc = new GridBagConstraints();
    static Business currentBusiness;
    ArrayList<Integer> incomes = new ArrayList<Integer>();
    ArrayList<Double> costs = new ArrayList<Double>();

    public PayPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Month Finances"));

        try {
            ArrayList<String> businesses = Business.getAllBusinesses();
            gc.gridx = 0;
            gc.gridy = 0;
            gc.gridwidth = 2;
            add(new JLabel("Income: "), gc);
            gc.gridwidth = 1;
            int spacing = 1;
            for (int i = 0; i < businesses.size(); i++) {
                currentBusiness = Business.getBusiness(businesses.get(i));
                incomes.add(currentBusiness.calculateThisMonthPay());
                gc.gridx = 0;
                gc.gridy = spacing;
                add(new JLabel(currentBusiness.getName() + ":"), gc);
                gc.gridx = 1;
                add(new JLabel("$" + currentBusiness.calculateThisMonthPay()), gc);
                spacing++;
            }
            ArrayList<Employee> employees = Employee.getAllEmployees();
            gc.gridx = 0;
            gc.gridy = spacing++;
            gc.gridwidth = 2;
            add(new JLabel("Salaries: "), gc);
            gc.gridwidth = 1;
            for (int i = 0; i < employees.size(); i++) {
                costs.add(employees.get(i).getPay());
                gc.gridx = 0;
                gc.gridy = spacing;
                add(new JLabel(employees.get(i).getFirstName() + " " + employees.get(i).getLastName() + ":"), gc);
                gc.gridx = 1;
                add(new JLabel("$" + (employees.get(i).getPay()) * 2), gc);
                spacing++;
            }
            ArrayList<Task> tasks = Task.getAllTasks();
            gc.gridx = 0;
            gc.gridy = spacing++;
            gc.gridwidth = 2;
            add(new JLabel("Task Budgets: "), gc);
            gc.gridwidth = 1;
            for (int i = 0; i < tasks.size(); i++) {
                costs.add(tasks.get(i).getBudget() * 1.0);
                gc.gridx = 0;
                gc.gridy = spacing;
                add(new JLabel("Task: " + tasks.get(i).getTid()), gc);
                gc.gridx = 1;
                add(new JLabel("$" + tasks.get(i).getBudget()), gc);
                spacing++;
            }
            
            gc.gridx = 0;
            gc.gridy = spacing++;
            add(new JLabel("Total income: "), gc);
            int totalIncome = 0;
            for (int income : incomes) {
                totalIncome+=income;
            }
            gc.gridx = 1;
            add(new JLabel("$" + totalIncome), gc);

            gc.gridx = 0;
            gc.gridy = spacing++;
            add(new JLabel("Total Costs: "), gc);
            double totalCosts = 0;
            for (double cost : costs) {
                totalCosts+=cost;
            }
            gc.gridx = 1;
            add(new JLabel("$" + totalCosts), gc);

            gc.gridx = 0;
            gc.gridy = spacing++;
            add(new JLabel("Total Profit: "), gc);
            double totalProfit = totalIncome - totalCosts;
            gc.gridx = 1;
            add(new JLabel("$" + totalProfit), gc);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}