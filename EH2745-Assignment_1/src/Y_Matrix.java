/*
 *This class represents the Y-Matrix of the system
 *It calculate the admittances between the buses and add them into the matrix 
 * 
 */

import java.util.ArrayList;

public class Y_Matrix {
	
	//ArrayList where all the busbars of the system are stored
	public ArrayList<Busbar> busbar_array;
	
	//Base power of the system used to calculate the per unit values
	public double s_b; //base power
	
	//Values of the Y-Matrix
	public Complex[][] matrix;
	
	public Y_Matrix (ArrayList<Busbar> busbar_array) {
		
		//Create the busbar array
		this.busbar_array = busbar_array;
		
		//The base power is set to 500
		this.s_b = 500; 
		
		//Initialization of all the values to 0 + i0
		this.matrix = new Complex[busbar_array.size()][busbar_array.size()];
		for (int i = 0; i<busbar_array.size(); i++) {
			for (int j = 0; j<busbar_array.size(); j++) {
				Complex init = new Complex(0,0);
				this.matrix[i][j] = init;
			}
		}
		
		
		//Loop on every busbar
		//This correspond to the first index of the Y-Matrix 
		for (Busbar busbar : this.busbar_array) {
			
			//Method described in the class NetworkTrav that travel from a busbar to all the other busbars and to the ending Conducting Equipment attached to this busbar
			NetworkTrav.busbar_travel(busbar, this);
			
			//Loop on all the busbars attached to the concerned busbar
			//This correspond to second the index of the Y-Matrix
			for (int i = 0;  i<  busbar.toBusbars.size(); i++) {
				
				//Print the results
				System.out.println("\n");
				System.out.println("From bus " + busbar.index + " to " + i + " : ");
				
				//True if the indexes of the matrix are the same 
				boolean same_node = false;
				
				//If the indexes of the Y-Matrix are the same then the value is equal to the sum of all the admittances connected to the busbar
				if (busbar.index==i) {
					
					same_node = true;
					
					for (int j = 0;  j<  busbar.toBusbars.size(); j++) {
						for (NodeTrav node : busbar.toBusbars.get(j)) {
							
							//Add all the admittances attached to the busbar
							this.matrix[busbar.index][i] = this.matrix[busbar.index][i].plus(this.admittance(node, same_node));
								
						}		
					}
					
				}
				
				//If the indexes of the Y-Matrix are not the same then the value is equal to the opposite of the admittance between the two busbars
				if (busbar.index != i){
					same_node = false;

					for (NodeTrav node : busbar.toBusbars.get(i)) {
						
						//Substract all the admittances between the busbars
						this.matrix[busbar.index][i] = this.matrix[busbar.index][i].minus(this.admittance(node, same_node));

					}
				}
				
					
			}
		}
		
		
		
		
	}
	
	
	//Method that calculate the admittance of the Conducting Equipment
	public Complex admittance (NodeTrav node ,boolean same_node) {
		
		//Initialization of the result
		Complex admittance = new Complex(0,0);
		
		if (node == null) {
			return admittance;
		}
		
		//If the Conducting Equipment is a Power Transformer
		if (node.CE_type == "PowerTransformer") {
			
			//Get the value of R, X and the rated S of the Power Transformer with a SQL "select_where" statement
			String[] attributes = new String[4];
			attributes[0] ="PowerTransformerEnd_r";
			attributes[1] ="PowerTransformerEnd_x";
			attributes[2] ="PowerTransformerEnd_ratedS";
			attributes[3] ="TransformerEnd_BaseVoltage";
			ArrayList<String[]> select_results = SQLdatabase.select_where("PowerTransformerEnd", attributes, "PowerTransformerEnd_PowerTransformer", node.ID);
			
			//In case of different results do not take into account the values equals to zero
			for (String[] select_result : select_results) {
				if(Double.parseDouble(select_result[0]) != 0 && Double.parseDouble(select_result[1]) != 0){
					double r = Double.parseDouble(select_result[0]);
					double x = Double.parseDouble(select_result[1]);
					double s = Double.parseDouble(select_result[2]);
					double coeff = 100*x/Math.sqrt(r*r + x*x); 
					
					//The impedance is equal to j multiplied by the ratio of reactance (in per cent) multiplied by the value of base power and divided by the power of the transformer
					Complex impedance = new Complex (0, coeff*s_b/s);
					
					//The admittance is calculated as the inverse of the impedance
					admittance = impedance.inv();
				}
			}
			
		}
		
		//If the Conducting Equipment is an AC Line 
		if (node.CE_type == "ACLineSegment") {
			
			//Get the values of the linear resistance, reactance and of the capacitor and also the length of the line and the base voltage of the line 
			//The values are fetched with a SQL "select where" statement
			String[] attributes = new String[5];
			attributes[0] ="ACLineSegment_r";
			attributes[1] ="ACLineSegment_x";
			attributes[2] ="ACLineSegment_bch";
			attributes[3] ="Conductor_length";
			attributes[4] ="ConductingEquipment_BaseVoltage";
			ArrayList<String[]> select_results = SQLdatabase.select_where(node.CE_type, attributes, "ID", node.ID);
			for (String[] select_result : select_results) {
				double r = Double.parseDouble(select_result[0]);
				double x = Double.parseDouble(select_result[1]);
				double bch = Double.parseDouble(select_result[2]);
				double l = Double.parseDouble(select_result[3]);
				
				//The impedance is calculated as the multiplication of the linear impedance
				Complex impedance = new Complex (r,x).scale(l);
				
				//Get the value of the base voltage with a SQL "select where" statement
				double u_b = Double.parseDouble(String.join("",SQLdatabase.select_where("BaseVoltage", new String[]{"BaseVoltage_nominalVoltage"} , "ID", select_result[4]).get(0)));
				//The base impedance is calculated as the base voltage squared divided by the base power
				double base_impedance = u_b*u_b/s_b;
				
				//The admittance is calculated as the inverse of the impedance
				admittance = impedance.scale(1/base_impedance).inv();
				
				//If we are calculating the admittance of a single bus we have to take into account the value of the capacitor
				if (same_node) {
					
					//The admittance is equal to the linear capacitor multiplied by the length of the line and divided by two since the capacitors are modeled as if there are located at each end of the line
					admittance.plus(new Complex (0, bch*l*base_impedance/2));
					
				}
			}
					
		}
		
		
		//If the Conducting Equipment is an End Consumer
		if (node.CE_type == "EnergyConsumer") {
			
			//Get the active and reactive power of the load with a SQL "select where" statement 
			String[] attributes = new String[2];
			attributes[0] ="EnergyConsumer_p";
			attributes[1] ="EnergyConsumer_q";
			ArrayList<String[]> select_results = SQLdatabase.select_where(node.CE_type, attributes, "ID", node.ID);
			for (String[] select_result : select_results) {
				double p = Double.parseDouble(select_result[0]);
				double q = Double.parseDouble(select_result[1]);
				
				//The power S is equal to : S = P + jQ
				Complex s = new Complex (p, q);
				
				//The admittance is equal to the conjugate of the consumer power divided by the base power
				admittance = s.conjugate().scale(1/s_b);
			
			}
		}
		
		//If the Conducting Equipment is a Linear Shunt Compensator
		if (node.CE_type == "LinearShuntCompensator") {
			
			//Get the value of b and the normalized voltage with a SQL "select where" statement
			String[] attributes = new String[2];
			attributes[0] ="LinearShuntCompensator_bPerSection";
			attributes[1] ="ShuntCompensator_nomU";
			ArrayList<String[]> select_results = SQLdatabase.select_where(node.CE_type, attributes, "ID", node.ID);
			for (String[] select_result : select_results) {
				double b = Double.parseDouble(select_result[0]);
				double u_n = Double.parseDouble(select_result[1]);
				
				//The admittance is equal to the value of the capacitor multiplied by the base impedance
				admittance = new Complex (0, b*u_n*u_n/s_b);
	
			}
		}
		
		//Print the name of the Conducting Equipment and the value of the admittance
		System.out.println(node.CE_type + " " + node.name + "admittance : " + admittance);

		return admittance;
	}
	
	//Method to print the value of the Y-Matrix
	public void print_matrix () {
		System.out.println("\n \n");

		System.out.println("Printing Y matrix...");
		System.out.println("\n");

		//Loop on all the coefficient of the Y-Matrix
		for (int i = 0; i<busbar_array.size();i++) {
			for (int j = 0; j<busbar_array.size();j++) {
				System.out.println("Y" + i + j + " : " + matrix[i][j].toString());
			}
		}
	}
}

