import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.*;


public class ReadXML {
	
	Document doc;
	NodeList BaseVoltageList;
	NodeList SubstationList;
	NodeList VoltageLevelList;
	NodeList GeneratingUnitList;
	NodeList SynchronousMachineList;
	NodeList RegulatingControlList;
	NodeList PowerTransformerList;
	NodeList EnergyConsumerList;
	NodeList PowerTransformerEndList;
	NodeList BreakerList;
	NodeList RatioTapChangerList;
	NodeList TerminalList;
	NodeList ConnectivityNodeList;
	NodeList BusbarSectionList;
	NodeList LinearShuntCompensatorList;
	NodeList ACLineSegmentList;
	
	HashMap<String, NodeList> nodeListMap;
	
	public ReadXML() {
		Document doc;
		NodeList BaseVoltageList;
		NodeList SubstationList;
		NodeList VoltageLevelList;
		NodeList GeneratingUnitList;
		NodeList SynchronousMachineList;
		NodeList RegulatingControlList;
		NodeList PowerTransformerList;
		NodeList EnergyConsumerList;
		NodeList PowerTransformerEndList;
		NodeList BreakerList;
		NodeList RatioTapChangerList;
		NodeList TerminalList;
		NodeList ConnectivityNodeList;
		NodeList BusbarSectionList;
		NodeList LinearShuntCompensator;
		NodeList ACLineSegment;
		
	}
	
	//method to load an XML file
	public void loadXML (String fileName) {
		
		try {
			
			//Create a file
			File XmlFile = new File (fileName);
			//Create a document builder factory
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			//Create a docuement builder
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			Document doc = dbBuilder.parse(XmlFile);
			
			// normalize CIM XML file
			doc.getDocumentElement().normalize();
			
			this.doc = doc;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//method to create the node list of the needed cim object
	public void listXML () {
		this.BaseVoltageList = doc.getElementsByTagName("cim:BaseVoltage");
		this.SubstationList = doc.getElementsByTagName("cim:Substation");
		this.VoltageLevelList = doc.getElementsByTagName("cim:VoltageLevel");
		this.GeneratingUnitList = doc.getElementsByTagName("cim:GeneratingUnit");
		this.SynchronousMachineList = doc.getElementsByTagName("cim:SynchronousMachine");
		this.RegulatingControlList = doc.getElementsByTagName("cim:RegulatingControl");
		this.PowerTransformerList = doc.getElementsByTagName("cim:PowerTransformer");
		this.EnergyConsumerList = doc.getElementsByTagName("cim:EnergyConsumer");
		this.PowerTransformerEndList = doc.getElementsByTagName("cim:PowerTransformerEnd");
		this.BreakerList = doc.getElementsByTagName("cim:Breaker");
		this.RatioTapChangerList = doc.getElementsByTagName("cim:RatioTapChanger");
		this.TerminalList = doc.getElementsByTagName("cim:Terminal");
		this.ConnectivityNodeList = doc.getElementsByTagName("cim:ConnectivityNode");
		this.BusbarSectionList = doc.getElementsByTagName("cim:BusbarSection");
		this.LinearShuntCompensatorList = doc.getElementsByTagName("cim:LinearShuntCompensator");
		this.ACLineSegmentList = doc.getElementsByTagName("cim:ACLineSegment");
		
		
		//Creating a Hasmap to match each node list with the name of the element
		nodeListMap = new HashMap<String, NodeList>();

		//ACLineSegment
		nodeListMap.put("ACLineSegment", this.ACLineSegmentList);
		//BaseVoltage
		nodeListMap.put("BaseVoltage", this.BaseVoltageList);
		//Breaker
		nodeListMap.put("Breaker", this.BreakerList);
		//BusbarSection
		nodeListMap.put("BusbarSection", this.BusbarSectionList);
		//ConnectivityNode
		nodeListMap.put("ConnectivityNode", this.ConnectivityNodeList);
		//EnergyConsumer
		nodeListMap.put("EnergyConsumer", this.EnergyConsumerList);
		//GeneratingUnit
		nodeListMap.put("GeneratingUnit", this.GeneratingUnitList);
		//LinearShuntCompensator
		nodeListMap.put("LinearShuntCompensator", this.LinearShuntCompensatorList);
		//PowerTransformer
		nodeListMap.put("PowerTransformer", this.PowerTransformerList);
		//PowerTransformerEnd
		nodeListMap.put("PowerTransformerEnd", this.PowerTransformerEndList);
		//RatioTapChanger
		nodeListMap.put("RatioTapChanger", this.RatioTapChangerList);
		//RegulatingControl
		nodeListMap.put("RegulatingControl", this.RegulatingControlList);
		//Substation
		nodeListMap.put("Substation", this.SubstationList);
		//SynchronousMachine
		nodeListMap.put("SynchronousMachine", this.SynchronousMachineList);
		//Terminal
		nodeListMap.put("Terminal", this.TerminalList);
		//VoltageLevel
		nodeListMap.put("VoltageLevel", this.VoltageLevelList);
		
	}
	
	//method to extract the required data that are directly accessible from the cim model
	public static ArrayList<String> cim_extractNode (Node node, ArrayList<String> requiredData) {
		
		//Converting the node to element type
		Element element = (Element)node;
		//Array to store the results
		ArrayList<String> resultArray = new ArrayList<String>();
		
		//Loop on the required data
		for (String data : requiredData ) {
			//Creating a variable to store the result
			String result = new String();
			
			try {
				//Get the result by its tag name	
				result = element.getElementsByTagName(data).item(0).getTextContent();
				resultArray.add("'"+ result + "'");
				System.out.println(data + " : " + result);
			}catch (Exception e) {
				//in case of exception, the returned result is null
				resultArray.add("null");
			}
			
		}
		
		return resultArray;	
	}
	
	//method to extract the required data that are accessible from the rdf description
	public static ArrayList<String> rdf_extractNode (Node node, ArrayList<String[]> requiredData) {
		
		//Converting the node to element type
		Element element = (Element)node;
		//Array to store the results
		ArrayList<String> resultArray = new ArrayList<String>();
		
		//Loop on the required data
		for (String[] data : requiredData ) {
			//Creating a variable to store the result
			String result = new String();
			try {
			//in case of the rdf required data is the ID
			if (data[0]=="") {
				result = element.getAttribute(data[1]);
				if (result != "") {
				resultArray.add("'#"+ result + "'");
				System.out.println(data[1] + " : " + result);
				}
			}else {
				//Using the getChild method to get the required child node of the cim object
				Element child = getChild(element, data[0]);
				//Get the attribute described by the rdf tag
				result = child.getAttribute(data[1]);
				resultArray.add("'"+ result + "'");
				System.out.println(data[0] + " " + data[1] + " : " + result);
			}
			}catch(Exception e){
				//in case of exception, the returned result is null
				resultArray.add("null");
			}
		}
		return resultArray;
	}
	
	//method that get the child element of a node in the XML file
	public static Element getChild (Element parent, String childName) {
		//Loop on all the child of the node, from the fist child until there is no child left
		for(Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
	        if (child instanceof Element && childName.equals(child.getNodeName())) {
	        		return (Element) child;
	        }
	    }
	    return null;
	}
	
	//Method that parse the EQ file
	public static void parse_EQ (NodeList nodeList, String tableName, ArrayList<String> cimRequiredData, ArrayList<String[]> rdfRequiredData){
		//Loop on all the nodes of the node list
		for (int i = 0; i < nodeList.getLength(); i++) {
			
			//Using the cim_extractNode to extract the cim needed attributes
			ArrayList<String> attributes = convertCimAttributes(cimRequiredData);
			//Using the rdf_extractNode to extract and add the rdf needed attributes
			attributes.addAll(convertRdfAttributes(rdfRequiredData));
			
			//Using the cim_extractNode to extract the cim needed values
			ArrayList<String> values = ReadXML.cim_extractNode(nodeList.item(i), cimRequiredData);
			//Using the rdf_extractNode to extract and add the rdf needed values
			values.addAll(ReadXML.rdf_extractNode(nodeList.item(i), rdfRequiredData));
			
			//Insert the values at the right place in the SQL Table
			SQLdatabase.insertTable(tableName, attributes, values);
			System.out.print("\n");
		}
	}
	
	//Method that parse the SSH file
	public static void parse_SSH (NodeList nodeList, String tableName, ArrayList<String> cimRequiredData, ArrayList<String[]> rdfRequiredData){
		//Loop on all the nodes of the node list
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element)nodeList.item(i);
			String ID = element.getAttribute("rdf:about");
			
			//Using the cim_extractNode to extract the cim needed attributes
			ArrayList<String> values = ReadXML.cim_extractNode(nodeList.item(i), cimRequiredData);
			//Using the rdf_extractNode to extract and add the rdf needed attributes
			values.addAll(ReadXML.rdf_extractNode(nodeList.item(i), rdfRequiredData));
			
			//Using the cim_extractNode to extract the cim needed values
			ArrayList<String> attributes = convertCimAttributes(cimRequiredData);
			//Using the rdf_extractNode to extract and add the rdf needed values
			attributes.addAll(convertRdfAttributes(rdfRequiredData));
			
			//Loop for every attribute that haven't a value from the the EQ file 
			for (int j = 0; j < values.size(); j++) {
				if (!values.get(j).equals("null")) {
					//Update the table with the new values from the SSH file
					SQLdatabase.update(tableName,ID, attributes.get(j), values.get(j));
				}
				
			}
			System.out.print("\n");
		}
	}
	
	//method that convert the attributes of the XML to an adapted form for the SQL Database 
	public static ArrayList<String> convertCimAttributes (ArrayList<String> cimArray){
		ArrayList<String> newcimArray = new ArrayList<String>();
		
		//Loop for every element of the cim data
		for (String element : cimArray) {
			//split for at ":"
			element = element.split(":")[1];
			//replace the "." by "_" to be adapted with SQL language
			element = element.split("\\.")[0] + "_" + element.split("\\.")[1];
			newcimArray.add(element);
		}
		
		return newcimArray;
	}
		
	//method that convert the attributes of the XML to an adapted form for the SQL Database 
	public static ArrayList<String> convertRdfAttributes (ArrayList<String[]> rdfArray){
		ArrayList<String> newrdfArray = new ArrayList<String>();
		
		//Loop for every element of the cim data
		for (String[]element : rdfArray) {
			//case of ID attribute
			if (element[0].equals("")) {
				newrdfArray.add(element[1].split(":")[1]);
			}else {
			//split for at ":" and replace "." by "_" to be adapted to SQL language
			newrdfArray.add(element[0].split(":")[1].split("\\.")[0] + "_" + element[0].split(":")[1].split("\\.")[1]);
			}
		}
		
		return newrdfArray;
	}
}

	
