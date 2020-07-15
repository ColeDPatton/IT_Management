package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Inventory {
	String name;
	String machineType;
	String condition;
	public Inventory(String name, String machineType,String condition) {
		this.name=name;
		this.machineType=machineType;
		this.condition=condition;
	}
    public static Inventory getInventory(String desiredName) throws IOException {
        String getName;
        File f = new File("../IT_Management/database/inventory.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");
            getName = words[0];
            String MT = words[1];
            String condition = words[2];
            if (getName.equals(desiredName)){
                br.close();
                return new Inventory(desiredName, MT,condition);
            }
        }
        br.close();

        return null;
    }
    public static void addInventory(Inventory i) {
		File f = new File("../IT_Management/database/inventory.txt");
		BufferedWriter bw;
		if(!checkAdditionalAdd(i.getName()))
		{
			try {
				bw = new BufferedWriter(new FileWriter(f,true));
				String toWrite = i.getName()+" "+i.getMachineType()+" "+i.getCondition()+"\n";
				bw.write(toWrite);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    public static void addAllInventory(ArrayList<Inventory> inventories) {
		File f = new File("../IT_Management/database/inventory.txt");
		for(int i=0;i<inventories.size();i++) {
			if(checkAdditionalAdd(inventories.get(i).getName())) {
				inventories.remove(i);
				i--;
			}
		}
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f,true));
			String toWrite = "";
			for(int i=0;i<inventories.size();i++) {
				toWrite+=inventories.get(i).getName()+" "+inventories.get(i).getMachineType()+" "+inventories.get(i).getCondition()+"\n";
			}
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static ArrayList<String> getAllInventory() throws IOException {
        ArrayList<String> inventories = new ArrayList<String>();
        String getName;
        File f = new File("../IT_Management/database/inventory.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");
            getName = words[0];
            inventories.add(getName);
        }
        br.close();
        return inventories;
    }
    public static void updateInventory(Inventory i) {
		File f = new File("../IT_Management/database/inventory.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			String toWrite="";
			while((line = br.readLine()) !=null) {
				String[] words = line.split(" ");
				if(!words[0].equals(i.getName())) {
					toWrite+=words[0]+" "+words[1]+" "+words[2]+"\n";
				}
				else
					toWrite+=i.getName()+" "+i.getMachineType()+" "+i.getCondition()+"\n";
			}
			br.close();
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static void deleteInventory(Inventory i) {
		File f = new File("../IT_Management/database/inventory.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			String toWrite="";
			while((line = br.readLine()) !=null) {
				String[] words = line.split(" ");
				if(!words[0].equals(i.getName())) {
					toWrite+=words[0]+" "+words[1]+" "+words[2]+"\n";
				}
			}
			br.close();
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    private static boolean checkAdditionalAdd(String name) {
		File f = new File("../IT_Management/database/inventory.txt");
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			String line;
			while((line = br.readLine()) !=null) {
				String[] words = line.split(" ");
				if(words[0].equals(name))
					return true;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
}
