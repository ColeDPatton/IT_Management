package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import MainSystem.Task.Statuses;

public class to_dos {
	int tid;
	String title;
	String description;
	String category;
	String startDate;
	String endDate;
	int budget;
	public to_dos(int tid,String Title,String description,String category, String startDate,String endDate,int budget) {
		this.tid=tid;
		this.title=Title;
		this.description=description;
		this.category=category;
		this.startDate = startDate;
		this.endDate=endDate;
		this.budget=budget;
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
		return taskString;
	}
	public static boolean addToDoToFile(to_dos t,String file) {
		int tid = t.tid;

		if (t.title == null || t.title.isEmpty())
			return false;

		String title = t.title.replaceAll(" ", "_");
		String description = t.description.replaceAll(" ", "_");
		String category = t.category.replaceAll(" ", "_");

		String startDate = t.startDate;
		String endDate = t.endDate;
		int budget = t.budget;

		// Changes all empty data (that is allowed to be empty) to an _ which will allow it to empty
		// Will handle extension 1B. on createTask use case
		description = checkAndChangeEmpty(description);
		category = checkAndChangeEmpty(category);
		startDate = checkAndChangeEmpty(startDate);
		endDate = checkAndChangeEmpty(endDate);
		
		File f = new File(file);
		//File f = new File("database/task.txt");
		BufferedWriter bw;

		try {
			bw = new BufferedWriter(new FileWriter(f, true));
			BufferedReader br = new BufferedReader(new FileReader(f));
			String toWrite = tid + " " + title + " " + description + " " + category + " " + startDate + " " + endDate + " " + budget;
			bw.write(toWrite+ "\n");
			br.close();
			bw.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean updateToDoInFile(to_dos t,String file) throws IOException {
		int toBeUpdatedId = t.tid;

		// Prevents the title from being empty
		// Will handle extension 1A. on createTask use case
		if (t.title == null || t.title == "")
			return false;

		String title= t.title.replaceAll(" ","_");
		String description= t.description.replaceAll(" ","_");
		String category= t.category.replaceAll(" ","_");
		String startDate = t.startDate;
		String endDate = t.endDate;
		int budget = t.budget;

		// Changes all empty data (that is allowed to be empty) to an _ which will allow it to empty
		// Will handle extension 1B. on createTask use case
		description = checkAndChangeEmpty(description);
		category = checkAndChangeEmpty(category);
		startDate = checkAndChangeEmpty(startDate);
		endDate = checkAndChangeEmpty(endDate);
		File f = new File(file);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String toWrite = "";
		String line;

		Boolean wasUpdated = false;

		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");

			int potTid = Integer.parseInt(words[0]);

			if (potTid == toBeUpdatedId) {
				toWrite += toBeUpdatedId + " " + title + " " + description + " " + category + " " + startDate + " " + endDate + " " + budget + "\n";
				wasUpdated = true;
			} else {
				toWrite += words[0] + " " + words[1] + " " + words[2] + " " + words[3] + " " + words[4] + " " + words[5] + " " + words[6] + "\n";
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
	protected static String checkAndChangeEmpty(String empty) {
		if (empty == null || empty == "")
			return "_";
		return empty;
	}
	public static int checkUnique(int id,String file) throws IOException {
		File f = new File(file);
		// File f = new File("database/task.txt");

		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while((line = br.readLine()) !=null) {
			String[] words = line.split(" ");
			int potId = Integer.parseInt(words[0]);
			if(potId==id) {
				return potId;
			}
		}
		br.close();
		return -1;
	}

	public static to_dos getTo_Dos(int tid,String file) throws IOException {
		to_dos t;
		File f = new File(file);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;

		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");

			int potTid = Integer.parseInt(words[0]);

			if (potTid == tid) {
				String title = words[1];
				String description = words[2];
				String category = words[3];
				String startDate = words[4];
				String endDate = words[5];
				int budget = Integer.parseInt(words[6]);
				t = new to_dos(potTid, title, description, category, startDate, endDate, budget);
				br.close();
				return t;
			}
		}
		br.close();
		return null;
	}
	public static boolean deleteToDoInFile(to_dos t,String file) {
		File f = new File(file);
		BufferedWriter bw;
		boolean flag = false;
		String toWrite="";
		String line;
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			line = br.readLine();
			while(line !=null) {
				String[] words = line.split(" ");
				int potPid = Integer.parseInt(words[0]);
				if(potPid!=t.getTid()) {
					toWrite+=line+"\n";
				}
				else
					flag=true;
				line=br.readLine();
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(f));
			bw.write(toWrite);
			bw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getBudget() {
		return budget;
	}
	public void setBudget(int budget) {
		this.budget = budget;
	}
	public void addSingleEmployee(Employee emp, String file) {
		File f = new File(file);
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f,true));
			if(checkAdditionalAddToTask(emp.getIdentifier(),file)==-1)
			{
				String toWrite = tid+" "+emp.getIdentifier();
				bw.write(toWrite+"\n");
				
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addEmployees(ArrayList<Employee> emps,String file) {
		File f = new File(file);
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f,true));
			String toWrite = "";
			for(int i=0;i<emps.size();i++) {
				if(checkAdditionalAddToTask(emps.get(i).getIdentifier(),file)==-1)
				toWrite+=+tid+" "+emps.get(i).getIdentifier()+"\n";
			}
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Employee> getEmployees(String file) throws IOException{
		ArrayList<Employee> emps = new ArrayList<Employee>();
		File f = new File(file);
		// File f = new File("database//taskJoinEmployee.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while((line = br.readLine()) !=null) {
			String[] words = line.split(" ");
			if(Integer.parseInt(words[0])==tid) {
				Employee temp = Employee.getEmployee(Integer.parseInt(words[1]));
				emps.add(temp);
			}
		}
		br.close();
		return emps;
	}
	public void addProjectManager(ProjectManager pm, String file) {
		File f = new File(file);
		// File f = new File("database//taskJoinProjectmanager.txt");
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f,true));
			if(checkAdditionalAddToTask(pm.getIdentifier(),file)==-1) {
				String toWrite = tid+" "+pm.getIdentifier();
				bw.write(toWrite+"\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ProjectManager getProjectManager(String file) throws IOException {
		File f = new File(file);
		// File f = new File("database//taskJoinProjectmanager.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while((line = br.readLine()) !=null) {
			String[] words = line.split(" ");
			if(Integer.parseInt(words[0])==tid) {
				ProjectManager temp = ProjectManager.getProjectManager(Integer.parseInt(words[1]));
				return temp;
			}
		}
		br.close();
		return null;
	}
	public void addSingleCustomer(Customer cust, String file) {
		//File f = new File("IT_Management/database/taskJoinCustomer.txt");
		File f = new File(file);
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f,true));
			if(checkAdditionalAddToTask(cust.getIdentifier(),file)==-1) {
				String toWrite = tid+" "+cust.getIdentifier();
				bw.write(toWrite+"\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addCustomers(ArrayList<Customer> custs, String file) {
		//File f = new File("IT_Management/database/taskJoinCustomer.txt");
		File f = new File(file);
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f,true));
			String toWrite = "";
			for(int i=0;i<custs.size();i++) {
				if(checkAdditionalAddToTask(custs.get(i).getIdentifier(),file)==-1) {
					toWrite+=tid+" "+custs.get(i).getIdentifier()+"\n";
				}
				
			}
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Customer> getCustomers(String file) throws IOException{
		ArrayList<Customer> custs = new ArrayList<Customer>();
		//File f = new File("IT_Management/database/taskJoinCustomer.txt");
		File f = new File(file);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while((line = br.readLine()) !=null) {
			String[] words = line.split(" ");
			if(Integer.parseInt(words[0])==tid) {
				Customer temp = Customer.getCustomer(Integer.parseInt(words[1]));
				custs.add(temp);
			}
		}
		br.close();
		return custs;
	}
	protected int checkAdditionalAddToTask(int id,String file) {
		File f = new File(file);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			while((line = br.readLine()) !=null) {
				String[] words = line.split(" ");
				if(Integer.parseInt(words[0])==tid && Integer.parseInt(words[1])==id) {
					return 1;
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;

	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
}
