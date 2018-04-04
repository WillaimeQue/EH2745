import java.io.File;
import java.util.ArrayList;

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
	}
	
	public void loadXML (String fileName) {
		
		try {
			
			File XmlFile = new File (fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			Document doc = dbBuilder.parse(XmlFile);
			
			// normalize CIM XML file
			doc.getDocumentElement().normalize();
			
			this.doc = doc;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
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
		
	}
	
	
	
	public void extractNode (Node node, ArrayList<String> requiredData) {
		Element element = (Element)node;
		ArrayList<String> resultArray = new ArrayList<String>();
		
		for (String data : requiredData) {
			String index = data.split(":")[0];
			String result = new String();
			
			if (index == "cim") {
					
				
				try {
					
					result = element.getElementsByTagName(data).item(0).getTextContent();
					resultArray.add(result);
					System.out.println(data + " : " + result);
				}catch (Exception e) {
					
					
					e.printStackTrace();
				}
			}else {
				
				try {
					result = element.getAttribute("rdf:ID");
					resultArray.add(result);
					System.out.println(data + " : " + result);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	
	
}

	
