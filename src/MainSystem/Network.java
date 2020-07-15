package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Network {
	String name;
	int numNodes;
	public Network(String name, int numNodes) {
		this.name=name;
		this.numNodes=numNodes;
	}
    public static Network getNetwork(String desiredName) throws IOException {
        String getName;
        File f = new File("../IT_Management/database/network.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");
            getName = words[0];
            int NN = Integer.parseInt(words[1]);
            if (getName.equals(desiredName)){
                br.close();
                return new Network(desiredName, NN);
            }
        }
        br.close();

        return null;
    }
    public static void addNetwork(Network n) {
		File f = new File("../IT_Management/database/network.txt");
		BufferedWriter bw;
		if(!checkAdditionalAdd(n.getName())) {
			try {
				bw = new BufferedWriter(new FileWriter(f,true));
				String toWrite = n.getName()+" "+n.getNumNodes()+"\n";
				bw.write(toWrite);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    public static void addAllNetworks(ArrayList<Network> networks) {
		File f = new File("../IT_Management/database/network.txt");
		for(int i=0;i<networks.size();i++) {
			if(checkAdditionalAdd(networks.get(i).getName())) {
				networks.remove(i);
				i--;
			}
		}
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f,true));
			String toWrite = "";
			for(int i=0;i<networks.size();i++) {
				toWrite+=networks.get(i).getName()+" "+networks.get(i).getNumNodes()+"\n";
			}
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static ArrayList<String> getAllNetworks() throws IOException {
        ArrayList<String> networks = new ArrayList<String>();
        String getName;
        File f = new File("../IT_Management/database/network.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");
            getName = words[0];
            networks.add(getName);
        }
        br.close();
        return networks;
    }
    public static void updateNetwork(Network n) {
		File f = new File("../IT_Management/database/network.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			String toWrite="";
			while((line = br.readLine()) !=null) {
				String[] words = line.split(" ");
				if(!words[0].equals(n.getName())) {
					toWrite+=words[0]+" "+words[1]+"\n";
				}
				else
					toWrite+=n.getName()+" "+n.getNumNodes()+"\n";
			}
			br.close();
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static void deleteNetwork(Network n) {
		File f = new File("../IT_Management/database/network.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			String toWrite="";
			while((line = br.readLine()) !=null) {
				String[] words = line.split(" ");
				if(!words[0].equals(n.getName())) {
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
    public void addInventory(ArrayList<Inventory> inventories) {
		File f = new File("../IT_Management/database/joins/networkJoin/networksJoinInventory.txt");
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f,true));
			String toWrite = "";
			for(int i=0;i<inventories.size();i++) {
				toWrite+=getName().replaceAll(" ","_")+" "+inventories.get(i).getName()+"\n";
			}
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		numNodes+=inventories.size();
		updateNetwork(this);
    }
    public ArrayList<Inventory> getInventories(){
		ArrayList<Inventory> inventories = new ArrayList<Inventory>();
		File f = new File("../IT_Management/database/joins/networkJoin/networksJoinInventory.txt");
		try {
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while((line = br.readLine()) !=null) {
			String[] words = line.split(" ");
			if(words[0].equals(name)) {
				Inventory temp = Inventory.getInventory(words[1]);
				inventories.add(temp);
			}
		}
		br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return inventories;
    }
    private static boolean checkAdditionalAdd(String name) {
		File f = new File("../IT_Management/database/network.txt");
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
	public int getNumNodes() {
		return numNodes;
	}
	public void setNumNodes(int numNodes) {
		this.numNodes = numNodes;
	}
}

