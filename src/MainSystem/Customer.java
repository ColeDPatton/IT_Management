package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Customer extends IT_Personnel {
	int cid;
	String business;

	public Customer(int cid, String firstName, String lastName, String email, String business) {
		super(firstName, lastName, email);
		this.cid = cid;
		this.business = business;
	}
	public Customer() {
		super();
	}
	public void setIdentifer(int cid) {
		this.cid = cid;
	}

	public int getIdentifier() {
		return cid;
	}
	public static void addTicketForCustomer(ArrayList<Ticket> tickets, Customer c) throws IOException {
		Business b = Business.getBusiness(c.getBusiness());
		b.incrementTickets();
		Business.updateBusiness(b);
		File f = new File("../IT_Management/database/joins/ticketJoin/ticketJoinCustomer.txt");
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f,true));
			String toWrite = "";
			for(int i=0;i<tickets.size();i++) {
				if(Project.checkAdditionalAddToJoin(c.getIdentifier(),tickets.get(i).getTid(),"../IT_Management/database/joins/ticketJoin/ticketJoinCustomer.txt")==-1)
				toWrite+=c.getIdentifier()+" "+tickets.get(i).getTid()+"\n";
			}
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static ArrayList<Ticket> getTicketForCustomer(Customer c) throws IOException {
		ArrayList<Ticket> tasks = new ArrayList<Ticket>();
		File f = new File("../IT_Management/database/joins/ticketJoin/ticketJoinCustomer.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while((line = br.readLine()) !=null) {
			String[] words = line.split(" ");
			if(Integer.parseInt(words[0])==c.getIdentifier()) {
				Ticket temp = (Ticket) to_dos.getTo_Dos(Integer.parseInt(words[1]),"../IT_Management/database/tickets.txt");
				tasks.add(temp);
			}
		}
		br.close();
		return tasks;
	}
	public static void deleteTicketForCustomer(ArrayList<Integer> ticketsToRemove,Customer c) throws IOException { //takes in array list of TID's
		ArrayList<String> ticketsToWrite = new ArrayList<String>();
		File f = new File("../IT_Management/database/joins/ticketJoin/ticketJoinCustomer.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while((line = br.readLine()) !=null) {
			String[] words = line.split(" ");
			int potCid = Integer.parseInt(words[0]);
			if(potCid==c.getIdentifier() && ticketsToRemove.indexOf(Integer.parseInt(words[1]))==-1) {
				ticketsToWrite.add(words[0]+" "+words[1]);
			}
		}
		br.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter(f,true));
		String toWrite ="";
		for(int i=0;i<ticketsToWrite.size();i++) {
			toWrite+=ticketsToWrite.get(i)+"\n";
		}
		bw.write(toWrite);
		bw.close();
	}
	public static Customer getCustomer(int cid) throws IOException {
		Customer c;
		String firstName;
		String lastName;
		String email;
		String business;
		File f = new File("../IT_Management/database/customer.txt");
		// File f = new File("database//customer.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			String[] words = line.split(" ");
			int potCid = Integer.parseInt(words[0]);
			if (potCid == cid) {
				firstName = words[1];
				lastName = words[2];
				email = words[3];
				business = words[4];
				c = new Customer(potCid, firstName, lastName, email, business);
				br.close();
				return c;
			}
		}
		br.close();
		return null;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}

	public static ArrayList<Customer> getAllCustomers() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		File f = new File("../IT_Management/database/customer.txt");
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
				String business = words[4];
				customers.add(new Customer(potEid, firstName, lastName, email, business));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customers;
	}
}
