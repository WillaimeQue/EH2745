import java.util.ArrayList;

public class Busbar {
	
	public int index;
	public String CE_ID;
	public String CN_ID;
	public ArrayList<String> linear_shunt_compensator_array = new ArrayList<String>();
	public ArrayList<NodeTrav> toBusbar1;
	public ArrayList<NodeTrav> toBusbar2;
	public ArrayList<NodeTrav> toBusbar3;
	public ArrayList<NodeTrav> toBusbar4;
	public ArrayList<NodeTrav> toBusbar6;
	public ArrayList<ArrayList<NodeTrav>> toBusbars;
	

	
	
	public Busbar(int index, String CE_ID, String CN_ID) {
		
		this.index = index;
		this.CE_ID = CE_ID;
		this.CN_ID = CN_ID;
		
		this.toBusbar1 = new ArrayList<NodeTrav>();
		this.toBusbar2 = new ArrayList<NodeTrav>();
		this.toBusbar3 = new ArrayList<NodeTrav>();
		this.toBusbar4 = new ArrayList<NodeTrav>();
		this.toBusbar6 = new ArrayList<NodeTrav>();
		this.toBusbars = new ArrayList<ArrayList<NodeTrav>>();
		this.toBusbars.add(toBusbar1);
		this.toBusbars.add(toBusbar2);
		this.toBusbars.add(toBusbar3);
		this.toBusbars.add(toBusbar4);
		this.toBusbars.add(toBusbar6);


	}
	

	public void add_to_connexion(int index, ArrayList<String> element_array) {
		for (String element : element_array) {
		}
		
	}
	
}	
