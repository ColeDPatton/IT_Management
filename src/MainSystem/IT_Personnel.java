package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class IT_Personnel {
	String firstName;
	String lastName;
	int id;
	String email;
	public IT_Personnel(String firstName,String lastName,String email){
		this.firstName=firstName;
		this.lastName=lastName;
		this.email = email;
	}
	public IT_Personnel() {
		// TODO Auto-generated constructor stub
	}
	public void setIdentifier(int id) {
		this.id=id;
	}
	public int getIdentifier() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int checkUnique(int id,String file) throws IOException {
		File f = new File(file);
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
	public static boolean addITToFile(IT_Personnel c,String file)  {
		int id = c.getIdentifier();
		String firstName = c.getFirstName();
		String lastName=c.getLastName();
		String email=c.getEmail();
		File f = new File(file);
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f,true));
			String toWrite = id+" "+firstName+" "+lastName+" "+email;
			bw.write(toWrite+"\n");
			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean deleteITInFile(IT_Personnel c,String file) {
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
				if(potPid!=c.getIdentifier()) {
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
	public static int getITType(IT_Personnel IT) {
		if(IT.getClass()==new Customer().getClass()) {
			return 1;
		}
		else if(IT.getClass()==new Employee().getClass()) {
			return 2;
		}
		else if(IT.getClass()==new ProjectManager().getClass()) {
			return 3;
		}
		else
			return 0;
	}

}
