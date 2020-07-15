package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Employee extends IT_Personnel {
	int eid;
	int daysWorked;
	int hoursWorked;
	double wage;

	public Employee(int eid, String firstName, String lastName, String email, int daysWorked, int hoursWorked,
			double wage) {
		super(firstName, lastName, email);
		this.eid = eid;
		this.daysWorked = daysWorked;
		this.hoursWorked = hoursWorked;
		this.wage = wage;
	}
	public Employee() {
		super();
	}
	public static boolean addITToFile(IT_Personnel c,String file)  {
		int id = c.getIdentifier();
		String firstName = c.getFirstName();
		String lastName = c.getLastName();
		String email = c.getEmail();
		int daysWorked = ((Employee) c).getDaysWorked();
		int hoursWorked = ((Employee) c).getHoursWorked();
		double wage = ((Employee) c).getWage();
		File f = new File(file);
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f, true));
			String toWrite = id + " " + firstName + " " + lastName + " " + email + " " + daysWorked + " " + hoursWorked
					+ " " + wage;
			bw.write(toWrite + "\n");
			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Employee getEmployee(int eid) throws IOException {
		Employee e;
		File f = new File("../IT_Management/database/employee.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");
			int potEid = Integer.parseInt(words[0]);
			if (potEid == eid) {
				String firstName = words[1];
				String lastName = words[2];
				String email = words[3];
				int daysWorked = Integer.parseInt(words[4]);
				int hoursWorked = Integer.parseInt(words[5]);
				double wage = Double.parseDouble(words[6]);
				e = new Employee(potEid, firstName, lastName, email, daysWorked, hoursWorked, wage);
				br.close();
				return e;
			}
		}
		br.close();
		return null;
	}

	public static ArrayList<Employee> getAllAssignedToTaskEmployees(int tid) throws IOException {
		ArrayList<Employee> returner = new ArrayList<>();
		File f = new File("../IT_Management/database/joins/taskJoin/taskJoinEmployee.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");
			int potTid = Integer.parseInt(words[0]);
			if (potTid == tid) {
				Employee e = Employee.getEmployee(Integer.parseInt(words[1]));
				returner.add(e);
			}
		}
		br.close();
		return returner;
	}
	public static ArrayList<Employee> getAllAssignedToTicketEmployees(int tiid) throws IOException {
		ArrayList<Employee> returner = new ArrayList<>();
		File f = new File("../IT_Management/database/joins/ticketJoin/ticketJoinEmployee.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");
			int potTid = Integer.parseInt(words[0]);
			if (potTid == tiid) {
				Employee e = Employee.getEmployee(Integer.parseInt(words[1]));
				returner.add(e);
			}
		}
		br.close();
		return returner;
	}

	public ArrayList<Task> getTasks() throws NumberFormatException, IOException {
		ArrayList<Task> returner = new ArrayList<Task>();
		File f = new File("../IT_Management/database/taskJoinEmployee.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");
			int potEid = Integer.parseInt(words[1]);
			if (potEid == eid) {
				Task t = Task.getTask(Integer.parseInt(words[0]));
				returner.add(t);
			}
		}
		br.close();
		return returner;

	}

	public int getIdentifier() {
		return eid;
	}

	public void setIdentifier(int eid) {
		this.eid = eid;
	}

	public int getDaysWorked() {
		return daysWorked;
	}

	public void setDaysWorked(int daysWorked) {
		this.daysWorked = daysWorked;
	}

	public int getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(int hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	public double getWage() {
		return wage;
	}

	public void setWage(double wage) {
		this.wage = wage;
	}

	public double getPay() {
		return daysWorked * hoursWorked * wage;
	}

	public static boolean updateEmployeeInFile(Employee e) throws IOException {
		int toBeUpdatedId = e.getIdentifier();

		int hoursWorked = e.hoursWorked;
		int daysWorked = e.daysWorked;
		double wage = e.wage;

		File f = new File("../IT_Management/database/employee.txt");
		// File f = new File("database/employee.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String toWrite = "";
		String line;

		Boolean wasUpdated = false;

		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");

			int potEid = Integer.parseInt(words[0]);

			if (potEid == toBeUpdatedId) {
				toWrite += toBeUpdatedId + " " + e.firstName + " " + e.lastName + " " + e.email + " " + daysWorked + " "
						+ hoursWorked + " " + wage + "\n";
				wasUpdated = true;
			} else {
				toWrite += words[0] + " " + words[1] + " " + words[2] + " " + words[3] + " " + words[4] + " " + words[5]
						+ " " + words[6] + "\n";
			}
		}
		br.close();

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));

			bw.write(toWrite);

			bw.close();
		} catch (IOException z) {
			z.printStackTrace();
		}

		return wasUpdated;
	}

	public static ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		File f = new File("../IT_Management/database/employee.txt");
		// File f = new File("database//employee.txt");
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			String line;
			while ((line = br.readLine()) != null) {
				String[] words = line.split(" ");
				int potEid = Integer.parseInt(words[0]);
				String firstName = words[1];
				String lastName = words[2];
				String email = words[3];
				int daysWorked = Integer.parseInt(words[4]);
				int hoursWorked = Integer.parseInt(words[5]);
				double wage = Double.parseDouble(words[6]);
				employees.add(new Employee(potEid, firstName, lastName, email, daysWorked, hoursWorked, wage));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return employees;
	}
}
