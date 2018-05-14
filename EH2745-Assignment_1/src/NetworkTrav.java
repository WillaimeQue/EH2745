/****************************************************************************
Note : This Java class is inspired by the algorithm described in the paper :
An efficient method of extracting network information from CIM asset model
written by Srivats Shukla and Mark G. Yao
*****************************************************************************/

import java.util.ArrayList;
import java.util.Stack;

public class NetworkTrav {
	
	//Use the SQL methods defined in the class SQLdatabase to select all the node traversed and store them in different arrays according to their type
	static ArrayList<NodeTrav> TE_array = SQLdatabase.select_TE();
	static ArrayList<NodeTrav> CN_array = SQLdatabase.select_CN();
	static ArrayList<NodeTrav> CE_array = SQLdatabase.select_CE();

	//Create stacks to store the node visited during the network traversal algorithm and keep track of the order in which these nodes have been visited
	static Stack<NodeTrav> CN_stack = new Stack<NodeTrav>();
	static Stack<NodeTrav> CE_stack = new Stack<NodeTrav>();
	static Stack<NodeTrav> everything_stack = new Stack<NodeTrav>();
	
	//Define the indexes used (starting node , previous node, current node, next node)
	static NodeTrav startingNode;
	static NodeTrav prev_node;
	static NodeTrav curr_node;
	static NodeTrav next_node;


	//We define the Connectivity Node of the Busbar as the starting node
	//initialization of the network traversal algorithm
	
	public static void initialize(String ID) {
		
		TE_array = SQLdatabase.select_TE();
		CN_array = SQLdatabase.select_CN();
		CE_array = SQLdatabase.select_CE();

		CN_stack = new Stack<NodeTrav>();
		CE_stack = new Stack<NodeTrav>();
		everything_stack = new Stack<NodeTrav>();
		
		startingNode = new NodeTrav();
		prev_node = new NodeTrav();
		curr_node = new NodeTrav();
		next_node = new NodeTrav();
		
		//Initialize the starting busbar as the current node
		for (NodeTrav node : CN_array) {
			if (node.ID.equals(ID)) {
				startingNode = node;
				curr_node = startingNode;
				prev_node = new NodeTrav();
				
			}
		}
	}
	
	//Method that find the next node to travel to according to the previous and the current node
	public static void find_next_node() {
		
		//If the current node is a Terminal
		if (curr_node.node_type == "TE") {
			//If the previous node is a Conducting Equipment
			if (prev_node.node_type == "CE") {
				//The next node will  logically be a CN
				//Loop on every CN to find which one is connected to the Terminal
				for (NodeTrav CN_node : CN_array) {
					for (String CN_terminal : CN_node.terminalList) {
						if (CN_terminal.equals(curr_node.ID)) {
							next_node = CN_node;
							return;
						}
					}
				}
			
			} else {
				//If the previous node is Connectivity Node then the next one will be a Conducting Equipment
				//Loop on every CE to find which one is connected to the Terminal
				for (NodeTrav CE_node : CE_array) {
					for (String CE_terminal : CE_node.terminalList) {
						if (CE_terminal.equals(curr_node.ID)) {
 							next_node = CE_node;
 							return;
						}
						
					}
					
				}
			}
		} else { 
				//Finally if the current node is a CE or a CN,  the next node will be a Terminal
				//Loop on every Terminal in the Terminal list to find which one is attached to the current node and if it has not been visited yet
				for (String terminal_ID : curr_node.terminalList) {
					for (NodeTrav TE_node : TE_array) {
						if(terminal_ID.equals(TE_node.ID) && TE_node.traversal_flag==0) {
						next_node = TE_node;
						return;
						} 
					}
				}
		}
	}
	
	//Method that travel in the network from a busbar to either another busbar or an ending equipment
	public static void network_traversal(Busbar busbar, Y_Matrix Y_matrix ) {
		
		//while condition for the while loop
		boolean while_condition = true;
	
		
		while (while_condition) {
			
			//Use the find next node method to find wich node will be visited next
			find_next_node();
			
			
			
			//If the current node is a Terminal
			if (curr_node.node_type.equals("TE")) {
				
				//Push this node to the big stack, mark the terminal as traversed and update the indexes
				everything_stack.push(curr_node);
				curr_node.traversal_flag = 1;
				update_ind();	
			}
				
			//If the current node is a Connectivity Node 
			if (curr_node.node_type.equals("CN")) {
				
				//Push this node to the Connectivity Stack and the big stack
				CN_stack.push(curr_node);
				everything_stack.push(curr_node);
				//Test if the Connectivity Node is attached to a Busbar
				//Loop on the Terminals in the terminal list of the Connectivity Node
				for (String CN_terminal : curr_node.terminalList) {
					//Loop on all the Conducting Equipments 
					for (NodeTrav CE_node : CE_array) {
						//Loop on every Terminals in order to find the corresponding Terminal in the Terminal list of the Conducting Equipment
						for (String CE_terminal : CE_node.terminalList) {
							//If the corresponding Terminal is attached to a Busbar Section 
							if (CE_terminal.equals(CN_terminal) && CE_node.CE_type.equals("BusbarSection")) {
								for (NodeTrav terminal : TE_array) {
									if (terminal.ID.equals(CE_terminal)) {
										
										//If the CN is connected to a Busbar then end the method
										//Push the node to the big stack and to the CE stack, mark the terminal as visited and publish the result with the publish method and exit the while loop
										everything_stack.push(terminal);
										terminal.traversal_flag = 1;
										CE_stack.push(CE_node);
										everything_stack.push(CE_node);
										publish(busbar, Y_matrix);
										while_condition = false;
									} else {
										//If there is no corresponding Terminal this means that all the Terminals of the Connectivity Node have been visited
										//Then update the indexes
										update_ind();	
									}
								}
								
								
							} else {
								//If the CN is not connected to a busbar update the indexes and continue the while loop
								update_ind();
							}
						}
					}
				}	
			}	
				
			//If the current node is a Conducting Equipment
			if (curr_node.node_type.equals("CE")) {
				
				//Push the node in the CE stack and in the big stack
				CE_stack.push(curr_node);
				everything_stack.push(curr_node);
				
				//If the node has only one Terminal (ending device) or is an open breaker (using the isBreakerOpen method) 
				if ((curr_node.num_attachTerm == 1)&&!prev_node.ID.equals("null")||(curr_node.node_type.equals("Breaker")&&SQLdatabase.isBreakerOpen(curr_node.ID))){
					
					//This means that the network travel algorithm has found an end device
					//Publish the result and exit the while loop
					publish(busbar,Y_matrix);
					while_condition = false;
				}	
				
				//If the CE is not an end device then update the indexes and continue the while loop
				update_ind();	
			}
		
		
		
		}
	
		
	}
	
	//Method that take a busbar and the Y-matrix associated and travel in the network to build the Y-matrix
	public static void busbar_travel (Busbar busbar, Y_Matrix Y_matrix) {
		
		//initialization
		initialize(busbar.CN_ID);
		
		//Print the result for the busbar
		System.out.println("\n \n");
		System.out.println("**********************" + startingNode.name + "**********************");
		
		//Travel in the network for every Terminal connected to the Connectivity Node of the busbar
		for (String Bus_terminal : startingNode.terminalList) {
			for (NodeTrav terminal : TE_array) {
				if (terminal.ID.equals(Bus_terminal)) {
					curr_node = terminal;
					prev_node = new NodeTrav();
					network_traversal(busbar, Y_matrix);
				}
			}
			
		}
	}
	
	
	//Method to update indexes
	public static void update_ind() {
		prev_node = curr_node;
		curr_node = next_node;
		curr_node.traversal_flag = 1;
	}
	
	
	//Method to publish the result of the network traversal once the algorithm has traveled from a busbar to another busbar or to an end device
	public static void publish (Busbar busbar, Y_Matrix Y_matrix) {
		
		//Print the result once the traversal is finished
		
		//**************************************************************
		//Uncomment the print lines in order to see the CE and big stack
		//**************************************************************

		if (!CE_stack.isEmpty()&&!everything_stack.isEmpty()) {
			//System.out.println("\n \n");
			//System.out.println("Traversal terminated");
			//System.out.println("\n");
			//System.out.println("Publishing CE Stack...");
			int index = busbar.index;
			while (!CE_stack.isEmpty()) {
				
				NodeTrav node = CE_stack.pop();
				//System.out.println(node.CE_type + " " + node.name + " : " + node.ID);
				
				//If the ending of the network traversal is another busbar then get the index of that busbar
				if (node.CE_type.equals("BusbarSection")) {
					for (Busbar ending_busbar : Y_matrix.busbar_array) {
						if (ending_busbar.CE_ID.equals(node.ID)) {
							index = ending_busbar.index;
						}
					}
					
				}
				
				//If the ending of the network traversal is a Conducting Equipment then add the node to the busbar arraylist where all the conducting equipement between the busbars are stored
				if ((node.CE_type.matches("PowerTransformer|ACLineSegment|LinearShuntCompensator|EnergyConsumer|SynchronousMachine|GeneratingUnit"))&&!busbar.toBusbars.get(index).contains(node)) {
					
					busbar.toBusbars.get(index).add(node);
				}
				
			}

			//System.out.println("\n");
			//System.out.println("Publishing Everything Stack...");
			while (!everything_stack.isEmpty()) {
				NodeTrav node = everything_stack.pop();
				//System.out.println(node.name + " : " + node.ID);
				
			}
		}
		
		
		
	}
	
	
	//Method that find if a Conducting Node has an untraversed terminal left
	public static boolean CN_untraversed_terminal(NodeTrav CN_node) {
		boolean result = false;
		
		//Loop on all the Terminals in the terminal list
		for (String CN_terminal : CN_node.terminalList) {
			for (NodeTrav terminal : TE_array) {
				if (terminal.ID == CN_terminal) {
					//If the terminal has not been visited return true
					if (terminal.traversal_flag == 0) {
						System.out.println("untraversed terminal");
						result = true;
					}
				}
			}
		}
		
		return result;
	}

}
