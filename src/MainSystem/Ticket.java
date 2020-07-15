package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.SSLEngineResult.Status;

import MainSystem.Task.Statuses;

public class Ticket extends to_dos {
	public String business;
	public String status;
	public int createdById;

	public Ticket(int tiid, String Title, String description, String category, String startDate, String endDate,
			int budget, String business, int creatorId, Statuses status) {
		super(tiid, Title, description, category, startDate, endDate, budget);
		this.business = business;
		createdById = creatorId;
		this.status = status.toString();
	}

	public void addSingleEmployee(Employee emp) {
		super.addSingleEmployee(emp, "../IT_Management/database/joins/ticketJoin/ticketJoinEmployee.txt");
	}

	public void addEmployees(ArrayList<Employee> emps) {
		super.addEmployees(emps, "../IT_Management/database/joins/ticketJoin/ticketJoinEmployee.txt");
	}

	public ArrayList<Employee> getEmployees() throws IOException {
		return super.getEmployees("../IT_Management/database/joins/ticketJoin/ticketJoinEmployee.txt");
	}

	public void addProjectManager(ProjectManager pm) {
		super.addProjectManager(pm, "../IT_Management/database/joins/ticketJoin/ticketJoinProjectmanager.txt");
	}

	public ProjectManager getProjectManager() throws IOException {
		return super.getProjectManager("../IT_Management/database/joins/ticketJoin/ticketJoinProjectmanager.txt");
	}

	public void addSingleCustomer(Customer cust) {
		super.addSingleCustomer(cust, "../IT_Management/database/joins/ticketJoin/ticketJoinCustomer.txt");
	}

	public void addCustomers(ArrayList<Customer> custs) {
		super.addCustomers(custs, "../IT_Management/database/joins/ticketJoin/ticketJoinCustomer.txt");
	}

	public ArrayList<Customer> getCustomers() throws IOException {
		return super.getCustomers("../IT_Management/database/joins/ticketJoin/ticketJoinCustomer.txt");
	}

	public Statuses getStatus() {
		return Statuses.stringToStatus(this.status);
	}

	public static Ticket getTicket(int tiid) throws IOException {
		Ticket t;
		String title;
		String description;
		String category;
		String startDate;
		String endDate;
		int budget;
		String business;
		int creatorId;
		String status;

		File f = new File("../IT_Management/database/ticket.txt");
		// File f = new File("database//task.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;

		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");

			int potTid = Integer.parseInt(words[0]);

			if (potTid == tiid) {
				title = words[1].replaceAll("_", " ");
				description = words[2].replaceAll("_", " ");
				category = words[3].replaceAll("_", " ");
				startDate = words[4];
				endDate = words[5];
				budget = Integer.parseInt(words[6]);
				business = words[7];
				creatorId = Integer.parseInt(words[8]);
				status = words[9];
				t = new Ticket(potTid, title, description, category, startDate, endDate, budget, business, creatorId,
						Statuses.stringToStatus(status));
				br.close();
				return t;
			}
		}
		br.close();
		return null;
	}

	public static boolean addTicketToFile(Ticket t) {
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
		String business = t.business;
		int createdById = t.createdById;
		String status = t.status;

		// Changes all empty data (that is allowed to be empty) to an _ which will allow
		// it to empty
		// Will handle extension 1B. on createTask use case
		description = checkAndChangeEmpty(description);
		category = checkAndChangeEmpty(category);
		startDate = checkAndChangeEmpty(startDate);
		endDate = checkAndChangeEmpty(endDate);

		File f = new File("../IT_Management/database/ticket.txt");
		// File f = new File("database/task.txt");
		BufferedWriter bw;

		try {
			bw = new BufferedWriter(new FileWriter(f, true));
			BufferedReader br = new BufferedReader(new FileReader(f));
			String toWrite = tid + " " + title + " " + description + " " + category + " " + startDate + " " + endDate
					+ " " + budget + " " + business + " " + createdById + " " + status;
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

	public static boolean updateTicketInFile(Ticket t) throws IOException {
		int toBeUpdatedId = t.tid;

		// Prevents the title from being empty
		// Will handle extension 1A. on createTicket use case
		if (t.title == null || t.title.isEmpty())
			return false;

		String title = t.title.replaceAll(" ", "_");
		String description = t.description.replaceAll(" ", "_");
		String category = t.category.replaceAll(" ", "_");
		String startDate = t.startDate;
		String endDate = t.endDate;
		int budget = t.budget;
		String business = t.business;
		int createdById = t.createdById;
		String status = t.status;

		// Changes all empty data (that is allowed to be empty) to an _ which will allow
		// it to empty
		// Will handle extension 1B. on createTask use case
		description = checkAndChangeEmpty(description);
		category = checkAndChangeEmpty(category);
		startDate = checkAndChangeEmpty(startDate);
		endDate = checkAndChangeEmpty(endDate);

		File f = new File("../IT_Management/database/ticket.txt");
		// File f = new File("database/ticket.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String toWrite = "";
		String line;

		Boolean wasUpdated = false;

		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");

			int potTid = Integer.parseInt(words[0]);

			if (potTid == toBeUpdatedId) {
				toWrite += toBeUpdatedId + " " + title + " " + description + " " + category + " " + startDate + " "
						+ endDate + " " + budget + " " + business + " " + createdById + " " + status + "\n";

				if (!status.toString().equals(words[7])) {

					// Send notifications to employees assigned to the ticket
					ArrayList<Employee> employees = Employee.getAllAssignedToTicketEmployees(toBeUpdatedId);
					String newMessage = "Status of Ticket: " + title + " has changed from " + words[9] + " to "
							+ status.toString();
					for (Employee employee : employees) {
						Random rand = new Random();
						int randomNum = 0;
						while (Notification.checkUnique(randomNum) != -1) {
							randomNum = rand.nextInt(10000);
						}
						Notification newNote = new Notification(randomNum, employee.getIdentifier(), newMessage, false);
						Notification.addNotificationToFile(newNote);
					}

					// Send notifications to the customer who created the ticket
					String newMessageForCustomer = "Status of Ticket: " + title + " has changed from " + words[9]
							+ " to " + status.toString();
					Random rand = new Random();
					int randomNum = 0;
					while (Notification.checkUnique(randomNum) != -1) {
						randomNum = rand.nextInt(10000);
					}
					Notification newNote = new Notification(randomNum, createdById, newMessageForCustomer,
							false);
					Notification.addNotificationToFile(newNote);
				}

				wasUpdated = true;
			} else {
				toWrite += words[0] + " " + words[1] + " " + words[2] + " " + words[3] + " " + words[4] + " " + words[5]
						+ " " + words[6] + " " + words[7] + " " + words[8] + " " + words[9] + "\n";
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
}
