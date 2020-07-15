package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class ProjectManager extends Employee{
		public ProjectManager(int pid,String firstName,String lastName,String email,int daysWorked,int hoursWorked,double wage){
			super(pid,firstName,lastName,email,daysWorked,hoursWorked,wage);
		}
		public ProjectManager() {
			super();
		}
	public static ProjectManager getProjectManager(int pid) throws IOException {
		ProjectManager pm;
		File f = new File("../IT_Management/database/pm.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");
			int potPid = Integer.parseInt(words[0]);
			if (potPid == pid) {
				String firstName = words[1];
				String lastName = words[2];
				String email = words[3];
				int daysWorked = Integer.parseInt(words[4]);
				int hoursWorked=Integer.parseInt(words[5]);
				double wage=Double.parseDouble(words[6]);
				pm = new ProjectManager(potPid, firstName, lastName, email,daysWorked,hoursWorked,wage);
				br.close();
				return pm;
			}
		}
		br.close();
		return null;
	}


	public static ArrayList<ProjectManager> getAllProjectManagers() throws IOException {
		ArrayList<ProjectManager> projectManagers = new ArrayList<ProjectManager>();
		File f = new File("../IT_Management/database/pm.txt");
		BufferedReader br;
		br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");
			int pid = Integer.parseInt(words[0]);
			String firstName=words[1];
			String lastName=words[2];
			String email=words[3];
			int daysWorked=Integer.parseInt(words[4]);
			int hoursWorked=Integer.parseInt(words[5]);
			double wage=Double.parseDouble(words[6]);
			ProjectManager pm = new ProjectManager(pid,firstName,lastName,email,daysWorked,hoursWorked,wage);
			projectManagers.add(pm);
			// br.close();
		}
		br.close();
		return projectManagers;
	}
}
