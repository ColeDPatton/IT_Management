package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Business {
    private String name;
    private int ticketsMade;

    public Business(String name, int ticketsMade) {
        this.ticketsMade = ticketsMade;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public int getNumberOfTicketsMade() {
        return ticketsMade;
    }
    public int calculateThisMonthPay() {
		int basePay = 5000;
		int overPay = ticketsMade > 5 ? (ticketsMade-5)*200 : 0;
		return basePay + overPay;
    }
	public void addCustomers(ArrayList<Customer> custs) {
		File f = new File("../IT_Management/database/joins/businessJoin/businessJoinCustomer.txt");
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f,true));
			String toWrite = "";
			for(int i=0;i<custs.size();i++) {
				if(checkAdditionalAddToBusiness(custs.get(i).getIdentifier(),"../IT_Management/database/joins/businessJoin/businessJoinCustomer.txt")==-1) {
					toWrite+=getName().replaceAll(" ","_")+" "+custs.get(i).getIdentifier()+"\n";
				}
				
			}
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void incrementTickets() {
		ticketsMade++;
		File f = new File("../IT_Management/database/business.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			String toWrite="";
			while((line = br.readLine()) !=null) {
				String[] words = line.split(" ");
				if(!words[0].equals(this.getName())) {
					toWrite+=words[0]+" "+words[1]+"\n";
				}
				else
					toWrite+=this.getName()+" "+this.getNumberOfTicketsMade()+"\n";
			}
			br.close();
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Customer> getCustomers() throws IOException{
		ArrayList<Customer> custs = new ArrayList<Customer>();
		//File f = new File("../IT_Management/database/taskJoinCustomer.txt");
		File f = new File("../IT_Management/database/joins/businessJoin/businessJoinCustomer.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while((line = br.readLine()) !=null) {
			String[] words = line.split(" ");
			if(words[0].equals(name)) {
				Customer temp = Customer.getCustomer(Integer.parseInt(words[1]));
				custs.add(temp);
			}
		}
		br.close();
		return custs;
	}
    public static Business getBusiness(String name) throws IOException {
        String business;
        int tickedUsed;
        File f = new File("../IT_Management/database/business.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");
            business = words[0];
            tickedUsed = Integer.parseInt(words[1]);
            if (business.equals(name)){
                br.close();
                return new Business(name, tickedUsed);
            }
        }
        br.close();

        return null;
    }
    public static void addBusiness(Business b) {
		File f = new File("../IT_Management/database/business.txt");
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f,true));
			String toWrite = b.getName()+" "+b.getNumberOfTicketsMade()+"\n";
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static ArrayList<String> getAllBusinesses() throws IOException {
        ArrayList<String> businesses = new ArrayList<String>();
        String business;
        File f = new File("../IT_Management/database/business.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");
            business = words[0];
            businesses.add(business);
        }
        br.close();
        return businesses;
    }
    public static void updateBusiness(Business b) {
		File f = new File("../IT_Management/database/business.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			String toWrite="";
			while((line = br.readLine()) !=null) {
				String[] words = line.split(" ");
				if(!words[0].equals(b.getName())) {
					toWrite+=words[0]+" "+words[1]+"\n";
				}
				else
					toWrite+=b.getName()+" "+b.getNumberOfTicketsMade()+"\n";
			}
			br.close();
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	private int checkAdditionalAddToBusiness(int id,String file) {
		File f = new File(file);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));		
			String line;
			while((line = br.readLine()) !=null) {
				String[] words = line.split(" ");
				if(words[0].equals(name) && Integer.parseInt(words[1])==id) {
					return 1;
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;

	}

}
