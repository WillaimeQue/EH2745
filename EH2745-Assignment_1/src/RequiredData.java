import java.util.ArrayList;

/*
 * This class is used to store all the required data of the cim object and to organise them into arraylists
Each cim object has :

 - an arraylist with all the required data that can be directly accessed by the cim model
 - an arraylist with all the required data that is accessed with the rdf description
 - an arraylist with the name of the cim object, the first arraylist and the second arraylist

*/

public class RequiredData {
	
	ArrayList<String> BaseVoltage_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> BaseVoltage_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> BaseVoltage_array = new ArrayList<Object>();
	
	ArrayList<String> Breaker_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> Breaker_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> Breaker_array = new ArrayList<Object>();
	
	ArrayList<String> EnergyConsumer_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> EnergyConsumer_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> EnergyConsumer_array = new ArrayList<Object>();
	
	ArrayList<String> GeneratingUnit_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> GeneratingUnit_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> GeneratingUnit_array = new ArrayList<Object>();
	
	ArrayList<String> PowerTransformer_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> PowerTransformer_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> PowerTransformer_array = new ArrayList<Object>();
	
	ArrayList<String> PowerTransformerEnd_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> PowerTransformerEnd_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> PowerTransformerEnd_array = new ArrayList<Object>();
	
	ArrayList<String> RatioTapChanger_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> RatioTapChanger_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> RatioTapChanger_array = new ArrayList<Object>();
	
	ArrayList<String> RegulatingControl_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> RegulatingControl_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> RegulatingControl_array = new ArrayList<Object>();
	
	ArrayList<String> Substation_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> Substation_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> Substation_array = new ArrayList<Object>();
	
	ArrayList<String> SynchronousMachine_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> SynchronousMachine_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> SynchronousMachine_array = new ArrayList<Object>();
	
	ArrayList<String> VoltageLevel_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> VoltageLevel_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> VoltageLevel_array = new ArrayList<Object>();
	
	ArrayList<String> Terminal_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> Terminal_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> Terminal_array = new ArrayList<Object>();
	
	ArrayList<String> ConnectivityNode_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> ConnectivityNode_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> ConnectivityNode_array = new ArrayList<Object>();
	
	ArrayList<String> BusbarSection_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> BusbarSection_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> BusbarSection_array = new ArrayList<Object>();
	
	ArrayList<String> LinearShuntCompensator_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> LinearShuntCompensator_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> LinearShuntCompensator_array = new ArrayList<Object>();
	
	ArrayList<String> ACLineSegment_cimRequiredData = new ArrayList<String>();
	ArrayList<String[]> ACLineSegment_rdfRequiredData = new ArrayList<String[]>();
	ArrayList<Object> ACLineSegment_array = new ArrayList<Object>();
	
	ArrayList<ArrayList<Object>> RequiredData_array = new ArrayList<ArrayList<Object>>();
	
	
	public RequiredData() {
	
	// BaseVoltage
	add_cimRequiredData(BaseVoltage_cimRequiredData, "cim:BaseVoltage.nominalVoltage");
	add_rdfRequiredData(BaseVoltage_rdfRequiredData, "", "rdf:ID");
	BaseVoltage_array.add("BaseVoltage");
	BaseVoltage_array.add(BaseVoltage_cimRequiredData);
	BaseVoltage_array.add(BaseVoltage_rdfRequiredData);
	RequiredData_array.add(BaseVoltage_array);

	
	//Breaker
	
	add_rdfRequiredData(Breaker_rdfRequiredData, "", "rdf:ID");//rdf:ID
	//add_rdfRequiredData(Breaker_rdfRequiredData, "", "rdf:about");//rdf:ID	
	add_cimRequiredData(Breaker_cimRequiredData, "cim:IdentifiedObject.name");//name
	add_cimRequiredData(Breaker_cimRequiredData, "cim:Switch.open");	// state
	add_rdfRequiredData(Breaker_rdfRequiredData,"cim:Equipment.EquipmentContainer", "rdf:resource");//equipmentContainer_rdf:ID
	add_rdfRequiredData(Breaker_rdfRequiredData, "cim:Breaker.BaseVoltage","rdf:resource"); //BaseVoltage
	Breaker_array.add("Breaker");
	Breaker_array.add(Breaker_cimRequiredData);
	Breaker_array.add(Breaker_rdfRequiredData);
	RequiredData_array.add(Breaker_array);
	
	
	//EnergyConsumer

	add_rdfRequiredData(EnergyConsumer_rdfRequiredData, "", "rdf:ID");//rdf:ID
	//add_rdfRequiredData(EnergyConsumer_rdfRequiredData, "", "rdf:about");//rdf:ID
	add_cimRequiredData(EnergyConsumer_cimRequiredData, "cim:IdentifiedObject.name");//name
	add_cimRequiredData(EnergyConsumer_cimRequiredData, "cim:EnergyConsumer.p");// P
	add_cimRequiredData(EnergyConsumer_cimRequiredData, "cim:EnergyConsumer.q");// Q
	add_rdfRequiredData(EnergyConsumer_rdfRequiredData,"cim:Equipment.EquipmentContainer", "rdf:resource"); //equipmentContainer_rdf:ID
	add_rdfRequiredData(EnergyConsumer_rdfRequiredData, "cim:EnergyConsumer.BaseVoltage","rdf:resource"); //BaseVoltage
	EnergyConsumer_array.add("EnergyConsumer");
	EnergyConsumer_array.add(EnergyConsumer_cimRequiredData);
	EnergyConsumer_array.add(EnergyConsumer_rdfRequiredData);
	RequiredData_array.add(EnergyConsumer_array);
	
	//GeneratingUnit

	add_rdfRequiredData(GeneratingUnit_rdfRequiredData, "", "rdf:ID");//rdf:ID
	//add_rdfRequiredData(GeneratingUnit_rdfRequiredData, "", "rdf:about");//rdf:ID	
	add_cimRequiredData(GeneratingUnit_cimRequiredData, "cim:IdentifiedObject.name");//name
	add_rdfRequiredData(GeneratingUnit_rdfRequiredData,"cim:Equipment.EquipmentContainer", "rdf:resource"); //equipmentContainer_rdf:ID
	add_cimRequiredData(GeneratingUnit_cimRequiredData, "cim:GeneratingUnit.maxOperatingP");//name
	add_cimRequiredData(GeneratingUnit_cimRequiredData, "cim:GeneratingUnit.minOperatingP");//name
	GeneratingUnit_array.add("GeneratingUnit");
	GeneratingUnit_array.add(GeneratingUnit_cimRequiredData);
	GeneratingUnit_array.add(GeneratingUnit_rdfRequiredData);
	RequiredData_array.add(GeneratingUnit_array);

	//PowerTransformer
	
	add_rdfRequiredData(PowerTransformer_rdfRequiredData, "", "rdf:ID");//rdf:ID
	//add_rdfRequiredData(PowerTransformer_rdfRequiredData, "", "rdf:about");//rdf:ID	
	add_cimRequiredData(PowerTransformer_cimRequiredData, "cim:IdentifiedObject.name");//name
	add_rdfRequiredData(PowerTransformer_rdfRequiredData,"cim:Equipment.EquipmentContainer", "rdf:resource"); //equipmentContainer_rdf:ID
	PowerTransformer_array.add("PowerTransformer");
	PowerTransformer_array.add(PowerTransformer_cimRequiredData);
	PowerTransformer_array.add(PowerTransformer_rdfRequiredData);
	RequiredData_array.add(PowerTransformer_array);
	
	//PowerTransformerEnd
	
	add_rdfRequiredData(PowerTransformerEnd_rdfRequiredData, "", "rdf:ID");//rdf:ID	
	//add_rdfRequiredData(PowerTransformerEnd_rdfRequiredData, "", "rdf:about");//rdf:ID	
	add_cimRequiredData(PowerTransformerEnd_cimRequiredData, "cim:IdentifiedObject.name");//name
	add_cimRequiredData(PowerTransformerEnd_cimRequiredData, "cim:PowerTransformerEnd.r"); //transformer r
	add_cimRequiredData(PowerTransformerEnd_cimRequiredData, "cim:PowerTransformerEnd.x"); //transformer x
	add_cimRequiredData(PowerTransformerEnd_cimRequiredData, "cim:PowerTransformerEnd.ratedS"); //transformer rated S
	add_rdfRequiredData(PowerTransformerEnd_rdfRequiredData, "cim:PowerTransformerEnd.PowerTransformer", "rdf:resource");//rdf:ID	
	add_rdfRequiredData(PowerTransformerEnd_rdfRequiredData, "cim:TransformerEnd.BaseVoltage", "rdf:resource");//rdf:ID	
	PowerTransformerEnd_array.add("PowerTransformerEnd");
	PowerTransformerEnd_array.add(PowerTransformerEnd_cimRequiredData);
	PowerTransformerEnd_array.add(PowerTransformerEnd_rdfRequiredData);
	RequiredData_array.add(PowerTransformerEnd_array);

	//RatioTapChanger
	
	add_rdfRequiredData(RatioTapChanger_rdfRequiredData, "", "rdf:ID");//rdf:ID	
	//add_rdfRequiredData(RatioTapChanger_rdfRequiredData, "", "rdf:about");//rdf:ID	
	add_cimRequiredData(RatioTapChanger_cimRequiredData, "cim:IdentifiedObject.name");//name
	add_cimRequiredData(RatioTapChanger_cimRequiredData,"cim:TapChanger.normalStep"); //step
	RatioTapChanger_array.add("RatioTapChanger");
	RatioTapChanger_array.add(RatioTapChanger_cimRequiredData);
	RatioTapChanger_array.add(RatioTapChanger_rdfRequiredData);
	RequiredData_array.add(RatioTapChanger_array);
	
	//RegulatingControl
	
	add_rdfRequiredData(RegulatingControl_rdfRequiredData, "", "rdf:ID");//rdf:ID
	//add_rdfRequiredData(RegulatingControl_rdfRequiredData, "", "rdf:about");//rdf:ID
	add_cimRequiredData(RegulatingControl_cimRequiredData, "cim:IdentifiedObject.name");//name
	add_cimRequiredData(RegulatingControl_cimRequiredData, "cim:RegulatingControl.targetValue"); //target value
	RegulatingControl_array.add("RegulatingControl");
	RegulatingControl_array.add(RegulatingControl_cimRequiredData);
	RegulatingControl_array.add(RegulatingControl_rdfRequiredData);
	RequiredData_array.add(RegulatingControl_array);
	
	//Substation
	
	add_rdfRequiredData(Substation_rdfRequiredData, "", "rdf:ID");//rdf:ID
	//add_rdfRequiredData(RegulatingControl_rdfRequiredData, "", "rdf:about");//rdf:ID
	add_cimRequiredData(Substation_cimRequiredData, "cim:IdentifiedObject.name");//name
	add_rdfRequiredData(Substation_rdfRequiredData,"cim:Substation.Region", "rdf:resource"); //region
	Substation_array.add("Substation");
	Substation_array.add(Substation_cimRequiredData);
	Substation_array.add(Substation_rdfRequiredData);
	RequiredData_array.add(Substation_array);
	
	//SynchronousMachine
	
	add_rdfRequiredData(SynchronousMachine_rdfRequiredData, "", "rdf:ID");//rdf:ID
	//add_rdfRequiredData(SynchronousMachine_rdfRequiredData, "", "rdf:about");//rdf:ID
	add_cimRequiredData(SynchronousMachine_cimRequiredData, "cim:IdentifiedObject.name");//name
	add_cimRequiredData(SynchronousMachine_cimRequiredData, "cim:RotatingMachine.ratedS");//ratedS
	add_cimRequiredData(SynchronousMachine_cimRequiredData, "cim:RotatingMachine.p");// P
	add_cimRequiredData(SynchronousMachine_cimRequiredData, "cim:RotatingMachine.q");// Q
	add_rdfRequiredData(SynchronousMachine_rdfRequiredData, "cim:RotatingMachine.GeneratingUnit", "rdf:resource");//genUnit_rdf:ID
	add_rdfRequiredData(SynchronousMachine_rdfRequiredData, "cim:RegulatingCondEq.RegulatingControl", "rdf:resource");//regControl_rdf:ID
	add_rdfRequiredData(SynchronousMachine_rdfRequiredData, "cim:Equipment.EquipmentContainer", "rdf:resource");//equipmentContainer_rdf:ID
	add_rdfRequiredData(SynchronousMachine_rdfRequiredData, "cim:SynchronousMachine.BaseVoltage","rdf:resource"); //BaseVoltage
	SynchronousMachine_array.add("SynchronousMachine");
	SynchronousMachine_array.add(SynchronousMachine_cimRequiredData);
	SynchronousMachine_array.add(SynchronousMachine_rdfRequiredData);
	RequiredData_array.add(SynchronousMachine_array);

	//VoltageLevel
	
	add_rdfRequiredData(VoltageLevel_rdfRequiredData, "", "rdf:ID");//rdf:ID	
	//add_rdfRequiredData(VoltageLevel_rdfRequiredData, "", "rdf:about");//rdf:ID	
	add_cimRequiredData(VoltageLevel_cimRequiredData, "cim:IdentifiedObject.name");//name
	add_rdfRequiredData(VoltageLevel_rdfRequiredData, "cim:VoltageLevel.Substation", "rdf:resource");//substation_rdf:ID
	add_rdfRequiredData(VoltageLevel_rdfRequiredData, "cim:VoltageLevel.BaseVoltage", "rdf:resource");//baseVoltage_rdf:ID	
	VoltageLevel_array.add("VoltageLevel");
	VoltageLevel_array.add(VoltageLevel_cimRequiredData);
	VoltageLevel_array.add(VoltageLevel_rdfRequiredData);
	RequiredData_array.add(VoltageLevel_array);
	
	//Terminal
	
	add_rdfRequiredData(Terminal_rdfRequiredData, "", "rdf:ID");//rdf:ID	
	add_cimRequiredData(Terminal_cimRequiredData, "cim:IdentifiedObject.name");//name
	add_rdfRequiredData(Terminal_rdfRequiredData, "cim:Terminal.ConductingEquipment", "rdf:resource");//Conducting Equipement
	add_rdfRequiredData(Terminal_rdfRequiredData, "cim:Terminal.ConnectivityNode", "rdf:resource");//Connectivity Node
	Terminal_array.add("Terminal");
	Terminal_array.add(Terminal_cimRequiredData);
	Terminal_array.add(Terminal_rdfRequiredData);
	RequiredData_array.add(Terminal_array);
	
	//ConnectivityNode
	
	add_rdfRequiredData(ConnectivityNode_rdfRequiredData, "", "rdf:ID");//rdf:ID	
	add_cimRequiredData(ConnectivityNode_cimRequiredData, "cim:IdentifiedObject.name");//name
	ConnectivityNode_array.add("ConnectivityNode");
	ConnectivityNode_array.add(ConnectivityNode_cimRequiredData);
	ConnectivityNode_array.add(ConnectivityNode_rdfRequiredData);
	RequiredData_array.add(ConnectivityNode_array);
	
	//BusbarSection
	
	add_rdfRequiredData(BusbarSection_rdfRequiredData, "", "rdf:ID");//rdf:ID	
	add_cimRequiredData(BusbarSection_cimRequiredData, "cim:IdentifiedObject.name");//name
	BusbarSection_array.add("BusbarSection");
	BusbarSection_array.add(BusbarSection_cimRequiredData);
	BusbarSection_array.add(BusbarSection_rdfRequiredData);
	RequiredData_array.add(BusbarSection_array);
	
	//LinearShuntCompensator
	
	add_rdfRequiredData(LinearShuntCompensator_rdfRequiredData, "", "rdf:ID");//rdf:ID	
	add_cimRequiredData(LinearShuntCompensator_cimRequiredData, "cim:IdentifiedObject.name");//name
	add_cimRequiredData(LinearShuntCompensator_cimRequiredData, "cim:LinearShuntCompensator.bPerSection");//b
	add_cimRequiredData(LinearShuntCompensator_cimRequiredData, "cim:ShuntCompensator.nomU");//base voltage
	LinearShuntCompensator_array.add("LinearShuntCompensator");
	LinearShuntCompensator_array.add(LinearShuntCompensator_cimRequiredData);
	LinearShuntCompensator_array.add(LinearShuntCompensator_rdfRequiredData);
	RequiredData_array.add(LinearShuntCompensator_array);
			
	//ACLineSegment
	
	add_rdfRequiredData(ACLineSegment_rdfRequiredData, "", "rdf:ID");//rdf:ID	
	add_cimRequiredData(ACLineSegment_cimRequiredData, "cim:IdentifiedObject.name");//name
	add_cimRequiredData(ACLineSegment_cimRequiredData, "cim:ACLineSegment.r");//r
	add_cimRequiredData(ACLineSegment_cimRequiredData, "cim:ACLineSegment.x");//x
	add_cimRequiredData(ACLineSegment_cimRequiredData, "cim:ACLineSegment.bch");//b
	add_cimRequiredData(ACLineSegment_cimRequiredData, "cim:Conductor.length");//length
	add_rdfRequiredData(ACLineSegment_rdfRequiredData, "cim:ConductingEquipment.BaseVoltage", "rdf:resource");//base voltage
	ACLineSegment_array.add("ACLineSegment");
	ACLineSegment_array.add(ACLineSegment_cimRequiredData);
	ACLineSegment_array.add(ACLineSegment_rdfRequiredData);
	RequiredData_array.add(ACLineSegment_array);
	}
	
	//Method to add a required data that can be directly accessed by the cim model
	public static void add_cimRequiredData(ArrayList<String> cimRequiredData, String data){
		cimRequiredData.add(data);
	}
	
	//Method to add a required data that is accessed with the rdf description
	public static void add_rdfRequiredData(ArrayList<String[]> rdfRequiredData, String cim_data, String rdf_data) {
		String[] data = new String[2];
		data[0] = cim_data;
		data[1] = rdf_data;
		rdfRequiredData.add(data);
	}

}
