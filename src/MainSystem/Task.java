package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.SSLEngineResult.Status;

public class Task extends to_dos {

	public enum Statuses {
		UPCOMING, INPROGRESS, BLOCKED, COMPLETED;

		public static Statuses stringToStatus(String status) {
			switch (status.toLowerCase()) {
			case "inprogress":
				return Statuses.INPROGRESS;
			case "blocked":
				return Statuses.BLOCKED;
			case "completed":
				return Statuses.COMPLETED;
			default:
				return Statuses.UPCOMING;
			}
		}
	}

	Statuses status;

	public Task(int tid, String Title, String description, String category, String startDate, String endDate,
			int budget, Statuses status) {
		super(tid, Title, description, category, startDate, endDate, budget);
		this.status = status;
	}

	@Override
	public String toString() {
		String taskString = "ID: " + tid + " \n";
		taskString += "Title: " + title + " \n";
		taskString += "Description: " + description + " \n";
		taskString += "Category: " + category + " \n";
		taskString += "StartDate: " + startDate + " \n";
		taskString += "EndDate: " + endDate + " \n";
		taskString += "Budget: " + budget + " \n";
		taskString += "Status: " + status.toString() + " \n";

		return taskString;
	}

	public static boolean addTaskToFile(Task t) {
		int tid = t.tid;

		if (t.title == null || t.title.isEmpty()) {
			System.out.println("Empty title");
			return false;
		}

		String title = t.title.replaceAll(" ", "_");
		String description = t.description.replaceAll(" ", "_");
		String category = t.category.replaceAll(" ", "_");

		String startDate = t.startDate;
		String endDate = t.endDate;
		int budget = t.budget;
		Statuses status = t.status;

		// Changes all empty data (that is allowed to be empty) to an _ which will allow
		// it to empty
		// Will handle extension 1B. on createTask use case
		description = checkAndChangeEmpty(description);
		category = checkAndChangeEmpty(category);
		startDate = checkAndChangeEmpty(startDate);
		endDate = checkAndChangeEmpty(endDate);

		if (status == null)
			status = Statuses.UPCOMING;

		File f = new File("../IT_Management/database/task.txt");
		// File f = new File("database/task.txt");
		BufferedWriter bw;

		try {
			bw = new BufferedWriter(new FileWriter(f, true));
			BufferedReader br = new BufferedReader(new FileReader(f));
			String toWrite = tid + " " + title + " " + description + " " + category + " " + startDate + " " + endDate
					+ " " + budget + " " + status.toString();
			bw.write(toWrite + "\n");
			br.close();
			bw.close();
			System.out.println("Success");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Error");
		return false;
	}

	public static boolean updateStatus(Task t, Statuses status) throws IOException {
		t.setStatus(status);
		return updateTaskInFile(t);
	}

	public static boolean updateTaskInFile(Task t) throws IOException {
		int toBeUpdatedId = t.tid;

		// Prevents the title from being empty
		// Will handle extension 1A. on createTask use case
		if (t.title == null || t.title.isEmpty())
			return false;

		String title = t.title.replaceAll(" ", "_");
		String description = t.description.replaceAll(" ", "_");
		String category = t.category.replaceAll(" ", "_");
		String startDate = t.startDate;
		String endDate = t.endDate;
		int budget = t.budget;
		Statuses status = t.status;

		// Changes all empty data (that is allowed to be empty) to an _ which will allow
		// it to empty
		// Will handle extension 1B. on createTask use case
		description = checkAndChangeEmpty(description);
		category = checkAndChangeEmpty(category);
		startDate = checkAndChangeEmpty(startDate);
		endDate = checkAndChangeEmpty(endDate);

		if (status == null)
			status = Statuses.UPCOMING;

		File f = new File("../IT_Management/database/task.txt");
		// File f = new File("database/task.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String toWrite = "";
		String line;

		Boolean wasUpdated = false;

		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");

			int potTid = Integer.parseInt(words[0]);

			if (potTid == toBeUpdatedId) {
				toWrite += toBeUpdatedId + " " + title + " " + description + " " + category + " " + startDate + " "
						+ endDate + " " + budget + " " + status.toString() + "\n";


				// If the new status is different from the old status

				if (!(status.toString().equals(words[7]))) {

					ArrayList<Employee> employees = Employee.getAllAssignedToTaskEmployees(toBeUpdatedId);

					String newMessage = "Status of task: " + title + " has changed from " + words[7] + " to "
							+ status.toString();

					Random rand = new Random();

					for (Employee employee : employees) {

						int randomNum = 0;
						while (Notification.checkUnique(randomNum) != -1) {
							randomNum = rand.nextInt(10000);
						}
						Notification newNote = new Notification(randomNum, employee.getIdentifier(), newMessage, false);
						Notification.addNotificationToFile(newNote);
					}
				}

				wasUpdated = true;
			} else {
				toWrite += words[0] + " " + words[1] + " " + words[2] + " " + words[3] + " " + words[4] + " " + words[5]
						+ " " + words[6] + " " + words[7] + "\n";
			}
		}
		br.close();

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));

			bw.write(toWrite);

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wasUpdated;
	}

	public static int checkUnique(int id) throws IOException {
		File f = new File("../IT_Management/database/task.txt");
		// File f = new File("database/task.txt");

		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");
			int potId = Integer.parseInt(words[0]);
			if (potId == id) {
				return potId;
			}
		}
		br.close();
		return -1;
	}

	public static Task getTask(int tid) throws IOException {
		Task t;
		String title;
		String description;
		String category;
		String startDate;
		String endDate;
		int budget;
		Statuses status;

		File f = new File("../IT_Management/database/task.txt");
		// File f = new File("database//task.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;

		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");

			int potTid = Integer.parseInt(words[0]);

			if (potTid == tid) {
				title = words[1].replaceAll("_", " ");
				description = words[2].replaceAll("_", " ");
				category = words[3].replaceAll("_", " ");
				startDate = words[4];
				endDate = words[5];
				budget = Integer.parseInt(words[6]);
				status = Enum.valueOf(Statuses.class, words[7]);
				t = new Task(potTid, title, description, category, startDate, endDate, budget, status);
				br.close();
				return t;
			}
		}
		br.close();
		return null;
	}

	public static boolean deleteTaskInFile(Task t, String file) {
		File f = new File(file);
		BufferedWriter bw;
		boolean flag = false;
		String toWrite = "";
		String line;
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));

			line = br.readLine();
			while (line != null) {
				String[] words = line.split(" ");
				int potPid = Integer.parseInt(words[0]);
				if (potPid != t.getTid()) {
					toWrite += line + "\n";
				} else
					flag = true;
				line = br.readLine();
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(f));
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static ArrayList<Task> getAllTasks() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		File f = new File("../IT_Management/database/task.txt");
		BufferedReader br;
		String line;
		try {
			br = new BufferedReader(new FileReader(f));
			while ((line = br.readLine()) != null) {
				String[] words = line.split(" ");
				if (words.length > 6) {
					tasks.add(new Task(Integer.parseInt(words[0]), words[1], words[2], words[3], words[4], words[5],
							Integer.parseInt(words[6]), Statuses.stringToStatus(words[7])));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tasks;
	}

	public Statuses getStatus() {
		return status;
	}

	public void setStatus(Statuses status) {
		this.status = status;
	}

	public void addSingleEmployee(Employee emp) {
		super.addSingleEmployee(emp, "../IT_Management/database/joins/taskJoin/taskJoinEmployee.txt");
	}

	public void addEmployees(ArrayList<Employee> emps) {
		super.addEmployees(emps, "../IT_Management/database/joins/taskJoin/taskJoinEmployee.txt");
	}

	public ArrayList<Employee> getEmployees() throws IOException {
		ArrayList<Employee> emp;
		return emp = super.getEmployees("../IT_Management/database/joins/taskJoin/taskJoinEmployee.txt");
	}

	public void addProjectManager(ProjectManager pm) {
		super.addProjectManager(pm, "../IT_Management/database/joins/taskJoin/taskJoinProjectmanager.txt");
	}

	public ProjectManager getProjectManager() throws IOException {
		return super.getProjectManager("../IT_Management/database/joins/taskJoin/taskJoinProjectmanager.txt");
	}

	public void addSingleCustomer(Customer cust) {
		super.addSingleCustomer(cust, "../IT_Management/database/joins/taskJoin/taskJoinCustomer.txt");
	}

	public void addCustomers(ArrayList<Customer> custs) {
		super.addCustomers(custs, "../IT_Management/database/joins/taskJoin/taskJoinCustomer.txt");
	}

	public ArrayList<Customer> getCustomers() throws IOException {
		ArrayList<Customer> custs;
		return custs = super.getCustomers("../IT_Management/database/joins/taskJoin/taskJoinCustomer.txt");
	}
}
