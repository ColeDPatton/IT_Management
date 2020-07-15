package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Project extends to_dos {
	public Project(int prid, String Title, String description, String category, String startDate, String endDate,
			int budget) {
		super(prid, Title, description, category, startDate, endDate, budget);
	}

	public static void addTaskToProjects(ArrayList<Task> tasks, Project p) {
		File f = new File("../IT_Management/database/joins/projectJoin/projectJoinTask.txt");
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f, true));
			String toWrite = "";
			for (int i = 0; i < tasks.size(); i++) {
				if (checkAdditionalAddToJoin(p.getTid(), tasks.get(i).getTid(),
						"../IT_Management/database/joins/projectJoin/projectJoinTask.txt") == -1)
					toWrite += p.getTid() + " " + tasks.get(i).getTid() + "\n";
			}
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Project> GetAllProjects() {
		ArrayList<Project> projects = new ArrayList<Project>();
		File f = new File("../IT_Management/database/project.txt");
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			String line;
			while ((line = br.readLine()) != null) {
				String[] words = line.split(" ");
				int prid = Integer.parseInt(words[0]);
				String Title = words[1];
				String description = words[2];
				String category = words[3];
				String startDate = words[4];
				String endDate = words[5];
				int budget = Integer.parseInt(words[6]);
				projects.add(new Project(prid, Title, description, category, startDate, endDate, budget));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projects;
	}

	public static Project getProjectById(int Prid) {
		File f = new File("../IT_Management/database/project.txt");
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			String line;
			while ((line = br.readLine()) != null) {
				String[] words = line.split(" ");
				int prid = Integer.parseInt(words[0]);
				if (prid == Prid) {
					String Title = words[1];
					String description = words[2];
					String category = words[3];
					String startDate = words[4];
					String endDate = words[5];
					int budget = Integer.parseInt(words[6]);
					br.close();
					return new Project(prid, Title, description, category, startDate, endDate, budget);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean updateProjectInFile(Project t) throws IOException {
		int toBeUpdatedId = t.tid;

		// Prevents the title from being empty
		if (t.title == null || t.title.isEmpty())
			return false;

		String title = t.title.replaceAll(" ", "_");
		String description = t.description.replaceAll(" ", "_");
		String category = t.category.replaceAll(" ", "_");
		String startDate = t.startDate;
		String endDate = t.endDate;
		int budget = t.budget;

		description = checkAndChangeEmpty(description);
		category = checkAndChangeEmpty(category);
		startDate = checkAndChangeEmpty(startDate);
		endDate = checkAndChangeEmpty(endDate);

		File f = new File("../IT_Management/database/project.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String toWrite = "";
		String line;

		Boolean wasUpdated = false;

		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");

			int potTid = Integer.parseInt(words[0]);

			if (potTid == toBeUpdatedId) {
				toWrite += toBeUpdatedId + " " + title + " " + description + " " + category + " " + startDate + " "
						+ endDate + " " + budget + "\n";
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wasUpdated;
	}

	public static int checkAdditionalAddToJoin(int id1, int id2, String string) throws IOException {
		File f = new File(string);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");
			if (Integer.parseInt(words[0]) == id1 && Integer.parseInt(words[1]) == id2) {
				return 1;
			}
		}
		return -1;
	}

	public static ArrayList<Task> getTaskToProjects(Project p) throws IOException {
		ArrayList<Task> tasks = new ArrayList<Task>();
		File f = new File("../IT_Management/database/joins/projectJoin/projectJoinTask.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");
			if (Integer.parseInt(words[0]) == p.getTid()) {
				Task temp = Task.getTask(Integer.parseInt(words[1]));
				tasks.add(temp);
			}
		}
		br.close();
		return tasks;
	}

	public static void deleteTasksFromProject(ArrayList<Integer> tasksToRemove, Project p) throws IOException { 
		ArrayList<String> tasksToWrite = new ArrayList<String>();
		File f = new File("../IT_Management/database/joins/projectJoin/projectJoinTask.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");
			int potPrid = Integer.parseInt(words[0]);
			if (p.getTid() == potPrid && tasksToRemove.indexOf(Integer.parseInt(words[1])) == -1) {
				tasksToWrite.add(words[0] + " " + words[1]);
			}
		}
		br.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
		String toWrite = "";
		for (int i = 0; i < tasksToWrite.size(); i++) {
			toWrite += tasksToWrite.get(i) + "\n";
		}
		bw.write(toWrite);
		bw.close();
	}

	public static void addEMeetingsToProject(ArrayList<Meetings> m, Project p) {
		File f = new File("../IT_Management/database/joins/projectJoin/projectJoinMeetings.txt");
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f, true));
			String toWrite = "";
			for (int i = 0; i < m.size(); i++) {
				if (checkAdditionalAddToJoin(p.getTid(), m.get(i).getMid(),
						"../IT_Management/database/joins/projectJoin/projectJoinMeetings.txt") == -1)
					toWrite += +p.getTid() + " " + m.get(i).getMid() + "\n";
			}
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Meetings> getMeetings(Project p) throws IOException {
		ArrayList<Meetings> m = new ArrayList<Meetings>();
		File f = new File("../IT_Management/database/joins/projectJoin/projectJoinMeetings.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");
			if (Integer.parseInt(words[0]) == p.getTid()) {
				Meetings temp = Meetings.getMeeting(Integer.parseInt(words[1]));
				m.add(temp);
			}
		}
		br.close();
		return m;
	}

	public static void deleteMeetings(ArrayList<Integer> meetingsToRemove, Project p) throws IOException {
		ArrayList<String> meetingsToWrite = new ArrayList<String>();
		File f = new File("../IT_Management/database/joins/projectJoin/projectJoinMeetings.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");
			int potPrid = Integer.parseInt(words[0]);
			if (potPrid == p.getTid() && meetingsToRemove.indexOf(Integer.parseInt(words[1])) == -1) {
				meetingsToWrite.add(words[0] + " " + words[1]);
			}
		}
		br.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
		String toWrite = "";
		for (int i = 0; i < meetingsToWrite.size(); i++) {
			toWrite += meetingsToWrite.get(i) + "\n";
		}
		bw.write(toWrite);
		bw.close();
	}

	public void addSingleEmployee(Employee emp) {
		super.addSingleEmployee(emp, "../IT_Management/database/joins/projectJoin/projectJoinEmployee.txt");
	}

	public void addEmployees(ArrayList<Employee> emps) {
		super.addEmployees(emps, "../IT_Management/database/joins/projectJoin/projectJoinEmployee.txt");
	}

	public ArrayList<Employee> getEmployees() throws IOException {
		ArrayList<Employee> emp;
		return emp = super.getEmployees("../IT_Management/database/joins/projectJoin/projectJoinEmployee.txt");
	}

	public void addProjectManager(ProjectManager pm) {
		super.addProjectManager(pm, "../IT_Management/database/joins/projectJoin/projectJoinProjectmanager.txt");
	}

	public ProjectManager getProjectManager() throws IOException {
		return super.getProjectManager("../IT_Management/database/joins/projectJoin/projectJoinProjectmanager.txt");
	}

	public void addSingleCustomer(Customer cust) {
		super.addSingleCustomer(cust, "../IT_Management/database/joins/projectJoin/projectJoinCustomer.txt");
	}

	public void addCustomers(ArrayList<Customer> custs) {
		super.addCustomers(custs, "../IT_Management/database/joins/projectJoin/projectJoinCustomer.txt");
	}

	public ArrayList<Customer> getCustomers() throws IOException {
		ArrayList<Customer> custs;
		return custs = super.getCustomers("../IT_Management/database/joins/projectJoin/projectJoinCustomer.txt");
	}

}
