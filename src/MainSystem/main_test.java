package MainSystem;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class main_test {
	public static void main(String[] args) throws IOException {
		System.out.println("Creating new inventory");
		Scanner in = new Scanner(System.in);

		System.out.println("Enter name");
		String name = in.nextLine();
		System.out.println("Enter type");
		String type = in.nextLine();
		System.out.println("Enter condition");
		String condition = in.nextLine();
		Inventory i = new Inventory(name,type,condition);
		Inventory.addInventory(i);
		Inventory i2 = Inventory.getInventory(name);
		System.out.println(i2.getName());
		System.out.println("old condition" +i2.getCondition());
		System.out.println("Enter new condition");
		String newCond = in.nextLine();
		i.setCondition(newCond);
		Inventory.updateInventory(i);
		Inventory i3 = Inventory.getInventory(name);
		System.out.println("new condition" +i3.getCondition());
		Inventory.deleteInventory(i);
		
		
		
		
		System.out.println("Enter name");
		String nameN = in.nextLine();
		System.out.println("Enter number of nodes");
		String numNodes = in.nextLine();
		Network n = new Network(nameN,Integer.parseInt(numNodes));
		Network.addNetwork(n);
		Network n2 = Network.getNetwork(nameN);
		System.out.println(n2.getName());
		System.out.println("old numNodex" +n2.getNumNodes());
		System.out.println("Enter new numNodes");
		String newNumNode = in.nextLine();
		n.setNumNodes(Integer.parseInt(newNumNode));
		Network.updateNetwork(n);
		Network n3 = Network.getNetwork(nameN);
		System.out.println("new numNode" +n3.getNumNodes());
		Network.deleteNetwork(n);
		/*
		System.out.println("Creating new meeting");
		Scanner in = new Scanner(System.in);

		Project newProject = new Project (123, "Business Project", "Description", "Business", "12/14/2019", "12/18/2019", 5000);


		to_dos.addToDoToFile(newProject, "IT_Management/database/project.txt");

		ArrayList<Meetings> mList = new ArrayList<Meetings>();
		mList.add(newMeeting);

		System.out.println("Assigning meeting to Project");

		System.out.println("Input project id to assign to meeting");
		int projectId = in.nextInt();
		Project newProject2 = Project.getProjectById(projectId);

		Project.addEMeetingsToProject(mList,newProject);

		ArrayList<Meetings> testList = Project.getMeetings(newProject2);
		System.out.println("Meeting Title after calling getMeetings on project: " + testList.get(0).title);
		*/
	}
}
