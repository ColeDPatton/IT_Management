package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Meetings {
	int mid;
	String title;
	String startDate;
	String endDate;
	int recurringNum;
	String recurringMeas;
	public Meetings(int mid,String title,String startDate,String endDate,int recurringNum,String recurringMeas) {
		this.mid =mid;
		this.title=title;
		this.startDate=startDate;
		this.endDate=endDate;
		this.recurringNum=recurringNum;
		this.recurringMeas=recurringMeas;
	}
	public static boolean addMeetingsToFile(Meetings m) {
		int mid = m.mid;

		if (m.title == null || m.title.isEmpty())
			return false;

		String title = m.title.replaceAll(" ", "_");
		String startDate = m.startDate;
		String endDate = m.endDate;
		int recurringNum=m.recurringNum;
		String recurringMeas=m.recurringMeas;

		// Changes all empty data (that is allowed to be empty) to an _ which will allow it to empty
		// Will handle extension 1B. on createTask use case
		startDate = checkAndChangeEmpty(startDate);
		endDate = checkAndChangeEmpty(endDate);
		recurringMeas = checkAndChangeEmpty(endDate);
		File f = new File("../IT_Management/database/meetings.txt");
		BufferedWriter bw;

		try {
			bw = new BufferedWriter(new FileWriter(f, true));
			BufferedReader br = new BufferedReader(new FileReader(f));
			String toWrite = mid + " " + title + " " + startDate + " " + endDate + " " + recurringNum+" "+recurringMeas;
			bw.write(toWrite+ "\n");
			br.close();
			bw.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean updateMeetingsInFile(Meetings m) throws IOException {
		int toBeUpdatedId = m.mid;

		// Prevents the title from being empty
		// Will handle extension 1A. on createTask use case
		if (m.title == null || m.title == "")
			return false;

		String title = m.title.replaceAll(" ", "_");
		String startDate = m.startDate;
		String endDate = m.endDate;
		int recurringNum=m.recurringNum;
		String recurringMeas=m.recurringMeas;

		// Changes all empty data (that is allowed to be empty) to an _ which will allow it to empty
		// Will handle extension 1B. on createTask use case
		startDate = checkAndChangeEmpty(startDate);
		endDate = checkAndChangeEmpty(endDate);
		recurringMeas = checkAndChangeEmpty(endDate);
		File f = new File("../IT_Management/database/meetings.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String toWrite = "";
		String line;
		Boolean wasUpdated = false;
		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");
			int potMid = Integer.parseInt(words[0]);
			if (potMid == toBeUpdatedId) {
				toWrite += toBeUpdatedId + " " + title + " " + startDate + " " + endDate + " " + recurringNum+" "+recurringMeas+"\n";
				wasUpdated = true;
			} else {
				toWrite += words[0] + " " + words[1] + " " + words[2] + " " + words[3] + " " + words[4] + " " + words[5] + "\n";
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

	public static Meetings getMeeting(int mid) throws IOException {
		Meetings m;
		File f = new File("../IT_Management/database/meetings.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;

		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");

			int potMid = Integer.parseInt(words[0]);

			if (potMid == mid) {
				String title = words[1];
				String startDate = words[2];
				String endDate =words[3];
				int recurringNum=Integer.parseInt(words[4]);
				String recurringMeas=words[5];
				m = new Meetings(potMid, title,startDate, endDate,recurringNum,recurringMeas);
				br.close();
				return m;
			}
		}
		br.close();
		return null;
	}
	public static boolean deleteMeetingInFile(Meetings m) {
		File f = new File("../IT_Management/database/meetings.txt");
		BufferedWriter bw;
		boolean flag = false;
		String toWrite="";
		String line;
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			line = br.readLine();
			while(line !=null) {
				String[] words = line.split(" ");
				int potMid = Integer.parseInt(words[0]);
				if(potMid!=m.getMid()) {
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
	public int getRecurringNum() {
		return recurringNum;
	}
	public void setRecurringNum(int recurringNum) {
		this.recurringNum = recurringNum;
	}
	public String getRecurringMeas() {
		return recurringMeas;
	}
	public void setRecurringMeas(String recurringMeas) {
		this.recurringMeas = recurringMeas;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public static void addEmployeeToMeeting(ArrayList<Employee> emps, Meetings m) {
		File f = new File("../IT_Management/database/joins/meetingsJoin/meetingsJoinEmployees.txt");
		BufferedWriter bw;
		try {
			Random rand = new Random();

			String newMessage = "You have been assigned to meeting: " + m.title;

			bw = new BufferedWriter(new FileWriter(f,true));
			String toWrite = "";
			for(int i=0;i<emps.size();i++) {
				if(Project.checkAdditionalAddToJoin(m.getMid(),emps.get(i).getIdentifier(),"../IT_Management/database/joins/meetingsJoin/meetingsJoinEmployees.txt")==-1) {
					toWrite+=m.getMid()+" "+emps.get(i).getIdentifier()+"\n";
					// Adds notification for employee that was assigned

					int randomNum = 0;
					while (Notification.checkUnique(randomNum) != -1) {
						randomNum = rand.nextInt(10000);
					}

					Notification newNote = new Notification(randomNum, emps.get(i).getIdentifier(), newMessage, false);
					Notification.addNotificationToFile(newNote);
				}
			}
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static ArrayList<Employee> getEmployeeToMeetings(Meetings m) throws IOException {
		ArrayList<Employee> emps = new ArrayList<Employee>();
		File f = new File("../IT_Management/database/joins/meetingsJoin/meetingsJoinEmployees.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while((line = br.readLine()) !=null) {
			String[] words = line.split(" ");
			if(Integer.parseInt(words[0])==m.getMid()) {
				Employee temp = Employee.getEmployee(Integer.parseInt(words[1]));
				emps.add(temp);
			}
		}
		br.close();
		return emps;
	}
	public static void deleteEmployeeFromMeetings(ArrayList<Employee> empsToRemove,Meetings m) throws IOException { //takes in array list of TID's
		ArrayList<String> empsToWrite = new ArrayList<String>();
		File f = new File("../IT_Management/database/joins/meetingsJoin/meetingsJoinEmployees.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while((line = br.readLine()) !=null) {
			String[] words = line.split(" ");
			int potMid = Integer.parseInt(words[0]);
			if(m.getMid()==potMid && empsToRemove.indexOf(Integer.parseInt(words[1]))==-1) {
				empsToWrite.add(words[0]+" "+words[1]);
			}
		}
		br.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter(f,true));
		String toWrite ="";
		for(int i=0;i<empsToWrite.size();i++) {
			toWrite+=empsToWrite.get(i)+"\n";
		}
		bw.write(toWrite);
		bw.close();
	}
}
