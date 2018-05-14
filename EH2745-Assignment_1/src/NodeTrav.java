/****************************************************************************
Note : This Java class is inspired by the algorithm described in the paper :
An efficient method of extracting network information from CIM asset model
written by Srivats Shukla and Mark G. Yao
*****************************************************************************/

import java.util.ArrayList;
public class NodeTrav {
	
	String name;		//node of the node
	String ID;		//ID of the node
	String node_type;		//node_type (TE, CN or CE)
	String CE_type;			//CE type (Breaker, PowerTransformer, EnergyConsumer...)
	ArrayList<String> terminalList;		//List of the terminals (empty for node of type terminal
	int num_attachTerm;		//number of terminals attached
	int traversal_flag;		//is equal to 0 if the node has not been visited by the network traversal algorithm
	
	
	public NodeTrav() {
		this.name = "null";
		this.ID = "null";
		this.node_type = "null";
		this.CE_type = "null";
		this.terminalList = new ArrayList<String>();
		this.num_attachTerm = 0;
		this.traversal_flag = 0;
	}

}
