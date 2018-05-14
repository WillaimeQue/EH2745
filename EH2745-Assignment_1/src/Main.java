/*
 * This class is the main class
 * It contains the main method which :
 *  - parses the XML files
 *  - stores the data in an SQL database
 *  - travels in the system networks
 *  - calculate the Y-Matrix of the system
 *  
 *  Note : Don't forget to drop the database in case of duplicate names of database 
 *  			drop database power_system;
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.sun.javafx.collections.MappingChange.Map;



public class Main {
	
	public static void main (String[] args) {
			
			//Select the files names
			//Note : the parsing of the XML files and the SQL Database works both sets of files but the calcul of the Y-Matrix is not adapted to the big files
			//Files names :
		
			//MicroGridTestConfiguration_T1_BE_EQ_V2
			//MicroGridTestConfiguration_T1_BE_SSH_V2
		
			//Assignment_EQ_reduced
			//Assignment_SSH_reduced
		
			String fileNameEQ = new String("Assignment_EQ_reduced.xml");		
			String fileNameSSH = new String("Assignment_SSH_reduced.xml");
			
			//Creating the XML reader for each files from the class ReadXML
			ReadXML readerEQ = new ReadXML();
			ReadXML readerSSH = new ReadXML();
			readerEQ.loadXML(fileNameEQ);
			readerSSH.loadXML(fileNameSSH);
			
			//Listing the element of the XML files into node lists
			readerEQ.listXML();
			readerSSH.listXML();
			
			//Creating SQL database
			SQLdatabase.connectDatabase();
			SQLdatabase.createDatabase("power_system");
			
			//Instance of the required data from the class RequiredData for the parsing
			RequiredData RequiredData = new RequiredData();
			
			try {
				
				//Parsing of the EQ and SSH files
				System.out.println("\n \n");
				System.out.println("******************************************************");
				System.out.println("**           Parsing EQ and SSH files               **");
				System.out.println("******************************************************");

				//Parsing for all the CIM objects stored in the class Required Data
				for (ArrayList<Object> CIM_object : RequiredData.RequiredData_array) {
					
					//Get the name of the CIM object
					String name = (String) CIM_object.get(0);
					//Get the Cim required Data
					ArrayList<String> cimRequiredData = (ArrayList<String>)CIM_object.get(1);
					//Get the Cim required Data
					ArrayList<String[]> rdfRequiredData = (ArrayList<String[]>)CIM_object.get(2);
					//Get the node list from the two files
					NodeList nodeListEQ = readerEQ.nodeListMap.get(name);
					NodeList nodeListSSH = readerSSH.nodeListMap.get(name);
					
					System.out.println("\n \n Parsing " + name + "... \n");
					
					//Create a Table in the SQL Database for the Cim object with the required data as attributes
					SQLdatabase.createTable(name, "ID", ReadXML.convertCimAttributes(cimRequiredData),ReadXML.convertRdfAttributes(rdfRequiredData));			
					
					System.out.println("\n");
					
					//Parsing and storing in the SQL Database the needed data of the Cim object from the two files
					ReadXML.parse_EQ (nodeListEQ, name, cimRequiredData, rdfRequiredData);
					ReadXML.parse_SSH (nodeListSSH, name, cimRequiredData, rdfRequiredData);

					
				}
				
				//Updating BaseVoltage for the Tables "Braker", "SynchronousMachine" and "EnergyConsumer"
				SQLdatabase.update_join("Breaker", "VoltageLevel", "Equipment_EquipmentContainer", "ID", "Breaker_BaseVoltage", "VoltageLevel_BaseVoltage");
				SQLdatabase.update_join("SynchronousMachine", "VoltageLevel", "Equipment_EquipmentContainer", "ID", "SynchronousMachine_BaseVoltage", "VoltageLevel_BaseVoltage");
				SQLdatabase.update_join("EnergyConsumer", "VoltageLevel", "Equipment_EquipmentContainer", "ID", "EnergyConsumer_BaseVoltage", "VoltageLevel_BaseVoltage");
	
				
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//Network Traversal
			
			System.out.println("\n \n");
			System.out.println("******************************************************");
			System.out.println("**                Network Traversal                 **");
			System.out.println("******************************************************");

			
			//Printing the Connectivity Nodes (CN) from the class NetworkTrav and the needed data for the Network Traversal Algorithm
			System.out.println("\n");
			System.out.println("************CN array************");
			for(NodeTrav CN : NetworkTrav.CN_array) {
				System.out.println("\n");
				System.out.println("name:"+CN.name);
				System.out.println("ID:"+CN.ID);
				System.out.println("node type:"+CN.node_type);
				System.out.println("CE type:"+CN.CE_type);
				System.out.println("Terminal list:"+CN.terminalList);
				System.out.println("num attached terminals:"+CN.num_attachTerm);

			}
			
			//Printing the Conductivity Equipment (CE) from the class NetworkTrav and the needed data EnergyConsumer

			System.out.println("\n");
			System.out.println("************CE array************");
			for(NodeTrav CE : NetworkTrav.CE_array) {
				System.out.println("\n");
				System.out.println("name:"+CE.name);
				System.out.println("ID:"+CE.ID);
				System.out.println("node type:"+CE.node_type);
				System.out.println("CE type:"+CE.CE_type);
				System.out.println("Terminal list:"+CE.terminalList);
				System.out.println("num attached terminals:"+CE.num_attachTerm);

			}
			
			
			//Y matrix
			System.out.println("\n \n");
			System.out.println("******************************************************");
			System.out.println("**                     Y-Matrix                     **");
			System.out.println("******************************************************");
			
			
			//Creating the matrix busbars 
			//For the reduced Network :
			
			//BE-Busbar_1 at index 0 of the matrix
			Busbar busbar1 = new Busbar (0, "#_64901aec-5a8a-4bcb-8ca7-a3ddbfcd0e6c", "#_4836f99b-c6e9-4ee8-a956-b1e3da882d46");
			
			//BE-Busbar_2 at index 1 of the matrix
			Busbar busbar2 = new Busbar (1, "#_ef45b632-3028-4afe-bc4c-a4fa323d83fe", "#_ae99bd74-26b1-443a-b1a5-656320283a36");
			
			//BE-Busbar_3 at index 2 of the matrix
			Busbar busbar3 = new Busbar (2, "#_5caf27ed-d2f8-458a-834a-6b3193a982e6", "#_bf851342-832e-4ea2-b2ad-b09729b3af23");
			
			//BE-Busbar_4 at index 3 of the matrix
			Busbar busbar4 = new Busbar (3, "#_fd649fe1-bdf5-4062-98ea-bbb66f50402d", "#_0f074167-d8ad-40ed-b0fa-7dc7e9f5f77c");
			
			//BE-Busbar_6 at index 4 of the matrix
			Busbar busbar6 = new Busbar (4, "#_364c9ca2-0d1d-4363-8f46-e586f8f66a8c", "#_1695eb20-9044-4133-a3fd-2147f55f170d");
			
			ArrayList<Busbar> busbar_array = new ArrayList<Busbar>();
			busbar_array.add(busbar1);
			busbar_array.add(busbar2);
			busbar_array.add(busbar3);
			busbar_array.add(busbar4);
			busbar_array.add(busbar6);
			
			//Creating the Y-Matrix
			Y_Matrix Y_matrix = new Y_Matrix(busbar_array);
					
			//Printing the Y-Matrix
			Y_matrix.print_matrix();
			
	}	
	
	
	
	
	
	
}
