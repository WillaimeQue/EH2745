import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Main {
	
	public static void main (String[] args) {
		
			String fileName = new String("Assignment_EQ_reduced.xml");
		
			ReadXML rXML = new ReadXML();
			rXML.loadXML(fileName);
			rXML.listXML();
			
			
			//Required Data
			ArrayList<String> requiredDataArray= new ArrayList<String>();
			requiredDataArray.add("rdf:ID");
			requiredDataArray.add("cim:IdentifiedObject.name");
//			requiredDataArray.add("cim:BaseVoltage.nominalVoltage"); 
//		    requiredDataArray.add("cim:GeneratingUnit.maxOperatingP");
//			requiredDataArray.add("cim:GeneratingUnit.minOperatingP");
//			requiredDataArray.add("cim:RotatingMachine.ratedS");
//			
			
			//BaseVoltage
			ArrayList<String> BaseVoltage_requiredData = new ArrayList<String>();
			BaseVoltage_requiredData.add("rdf:ID"); 
			BaseVoltage_requiredData.add("cim:BaseVoltage.nominalVoltage"); 
			
			//Substation
			ArrayList<String> Substation_requiredData = new ArrayList<String>();
			Substation_requiredData.add("rdf:ID");
			Substation_requiredData.add("cim:IdentifiedObject.name"); 
			
			//VoltageLevel
			ArrayList<String> VoltageLevel_requiredData = new ArrayList<String>();
			VoltageLevel_requiredData.add("rdf:ID"); 
			VoltageLevel_requiredData.add("cim:IdentifiedObject.name"); 
		
			//GeneratingUnit
			ArrayList<String> GeneratingUnit_requiredData = new ArrayList<String>();
			GeneratingUnit_requiredData.add("rdf:ID");
			GeneratingUnit_requiredData.add("cim:IdentifiedObject.name");
			GeneratingUnit_requiredData.add("cim:GeneratingUnit.maxOperatingP");
			GeneratingUnit_requiredData.add("cim:GeneratingUnit.minOperatingP");
			
			//SynchronousMachine
			ArrayList<String> SynchronousMachine_requiredData = new ArrayList<String>();
			SynchronousMachine_requiredData.add("rdf:ID");
			SynchronousMachine_requiredData.add("cim:IdentifiedObject.name");
			SynchronousMachine_requiredData.add("cim:RotatingMachine.ratedS");
			
			//RegulatingControl
			ArrayList<String> RegulatingControl_requiredData = new ArrayList<String>();
			RegulatingControl_requiredData.add("rdf:ID");
			RegulatingControl_requiredData.add("cim:IdentifiedObject.name");
			
			//PowerTransformer
			ArrayList<String> PowerTransformer_requiredData = new ArrayList<String>();
			PowerTransformer_requiredData.add("rdf:ID");
			PowerTransformer_requiredData.add("cim:IdentifiedObject.name");
			
			//EnergyConsumer
			ArrayList<String> EnergyConsumer_requiredData = new ArrayList<String>();
			EnergyConsumer_requiredData.add("rdf:ID");
			EnergyConsumer_requiredData.add("cim:IdentifiedObject.name");
			
			//PowerTransformerEnd
			ArrayList<String> PowerTransformerEnd_requiredData = new ArrayList<String>();
			PowerTransformerEnd_requiredData.add("rdf:ID");
			PowerTransformerEnd_requiredData.add("cim:IdentifiedObject.name");
			
			//Breaker
			ArrayList<String> Breaker_requiredData = new ArrayList<String>();
			Breaker_requiredData.add("rdf:ID");
			Breaker_requiredData.add("cim:IdentifiedObject.name");
			
			//RadioTapChanger
			ArrayList<String> RadioTapChanger_requiredData = new ArrayList<String>();
			RadioTapChanger_requiredData.add("rdf:ID");
			RadioTapChanger_requiredData.add("cim:IdentifiedObject.name");
			
			
			
			
			
			
			
			try {
			
				for (int i = 0; i < rXML.BaseVoltageList.getLength(); i++) {
					rXML.extractNode(rXML.BaseVoltageList.item(i),requiredDataArray);	
				}
				
				for (int i = 0; i < rXML.SubstationList.getLength(); i++) {
					rXML.extractNode(rXML.SubstationList.item(i),requiredDataArray);	
				}
				
				for (int i = 0; i < rXML.VoltageLevelList.getLength(); i++) {
					rXML.extractNode(rXML.VoltageLevelList.item(i),requiredDataArray);	
				}
				
				for (int i = 0; i < rXML.GeneratingUnitList.getLength(); i++) {
					rXML.extractNode(rXML.GeneratingUnitList.item(i),requiredDataArray);	
				}
				
				for (int i = 0; i < rXML.SynchronousMachineList.getLength(); i++) {
					rXML.extractNode(rXML.SynchronousMachineList.item(i),requiredDataArray);	
				}
				
				for (int i = 0; i < rXML.RegulatingControlList.getLength(); i++) {
					rXML.extractNode(rXML.RegulatingControlList.item(i),requiredDataArray);	
				}
				
				for (int i = 0; i < rXML.PowerTransformerList.getLength(); i++) {
					rXML.extractNode(rXML.PowerTransformerList.item(i),requiredDataArray);	
				}
				
				for (int i = 0; i < rXML.EnergyConsumerList.getLength(); i++) {
					rXML.extractNode(rXML.EnergyConsumerList.item(i),requiredDataArray);	
				}
				
				for (int i = 0; i < rXML.PowerTransformerEndList.getLength(); i++) {
					rXML.extractNode(rXML.PowerTransformerEndList.item(i),requiredDataArray);	
				}
				
				for (int i = 0; i < rXML.BreakerList.getLength(); i++) {
					rXML.extractNode(rXML.BreakerList.item(i),requiredDataArray);	
				}
				
				for (int i = 0; i < rXML.RatioTapChangerList.getLength(); i++) {
					rXML.extractNode(rXML.RatioTapChangerList.item(i),requiredDataArray);	
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
	}

}
