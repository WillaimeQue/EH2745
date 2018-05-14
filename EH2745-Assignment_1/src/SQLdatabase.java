import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLdatabase {
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DB_URL = "jdbc:mysql://localhost/";
	
	// Database credentials
	static final String USER = "root";
	static final String PASS = "password"; // insert the password to SQL server
	
	static Connection conn = null;
	static Statement stmt = null;
	
	//method to connect to a SQL server
	public static void connectDatabase() {
		
		try {
			// Register JDBC driver 
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		try {
			// Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//method to create a SQL database
	public static void createDatabase (String dataBaseName) {
		
		try {
			// Execute a query to create database
			System.out.println("Creating database "+ dataBaseName + "...");
			stmt = conn.createStatement();
			String sql = "create database if not exists " + dataBaseName; // Create database  
			stmt.executeUpdate(sql);
			System.out.println("Database " + dataBaseName + " created successfully...");
			
			// Connect to the created Database
			conn = DriverManager.getConnection(DB_URL + dataBaseName, USER, PASS);
			stmt = conn.createStatement();
			
			System.out.println("Database used : " + dataBaseName);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//method to create a Table in the selected SQL database
	public static void createTable (String name, String key, ArrayList<String> cimAttribute, ArrayList<String> rdfAttribute) {
		
	//Add the cim and rdf based required data in one arraylist that represents the attributes of the table	
	ArrayList<String> attributes = cimAttribute;
	attributes.addAll(rdfAttribute);
	
		try {
			
			//Create the table
			System.out.println("Creating table " + name + "...");
			// Create the sql statement with the right attributes and the key
			stmt = conn.createStatement();
			String sql = "create table if not exists " + name + "("; // Create table 
			for (String attribute : attributes) {
				sql = sql + attribute + " varchar(255), ";
			}
			sql = sql + "primary key (" + key + "))";
			//System.out.println(sql);
			
			//Execute the SQL query
			stmt.executeUpdate(sql);
			System.out.println("Table " + name + " created successfully");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//method to insert values to a SQL Table
	public static void insertTable (String tableName, ArrayList<String> attributes, ArrayList<String> values) {
		
		try {
			
			//Create the SQL sttement
			System.out.println("Inserting values in table" + tableName + "...");
			stmt = conn.createStatement();
			String sql = "insert into " + tableName +"(";
			
			for (int i=0; i<attributes.size(); i++) {
				sql = sql + attributes.get(i);
				if (i==attributes.size()-1) {
					sql = sql + ")";
				} else {
					sql = sql + ",";
				}
			}
			
			sql = sql + "values (";
			
			for (int i=0; i<values.size(); i++) {
				sql = sql + values.get(i);
				if (i==values.size()-1) {
					sql = sql + ")";
				} else {
					sql = sql + ",";
				}
			}
			
			//Execute the SQL query
			//System.out.println(sql);
			stmt.executeUpdate(sql);
			System.out.println("Table" + tableName + "has been updated successfully");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//update a table by creating a SQL join statement
	static void update_join (String tableName1, String tableName2, String id1, String id2, String attribute1, String attribute2) {
		try {
			
			//Create the SQL statement
			System.out.println("updating values in table " + tableName1 + " from " + tableName2);
			stmt = conn.createStatement();
			
			//Update the attributes of the table 1 with the values of the table 2 where the element from both tables have the same ID
			String sql = "update " + tableName1 +" t1"
						+ " inner join " + tableName2 + " t2"
						+ " on " + "t1." + id1 + " = " + "t2." + id2
						+ " set " + "t1." + attribute1 + " = " + "t2." + attribute2;
			
			//Execute the SQL query
			stmt.executeUpdate(sql);
			System.out.println("Table" + tableName1 + "has been updated successfully");
			
			
		}catch(SQLException e){
			e.printStackTrace();

		}
	}
	
	//method to update the value of an attribute of the table 
	static void update(String tableName, String ID, String columnName, String value) {
		try {
			
			//Create the SQL statement
			System.out.println("updating values in table " + tableName);
			stmt = conn.createStatement();
			
			//Update the SQL table with the value at the wanted column where the ID are the same
			String sql = "update " + tableName + " set " + columnName + " = " + value + "where ID = " + "'" + ID + "'"; 
			//System.out.println(sql);
			
			//Execute the SQL query
			stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	//method to create a SQL "select where" query in a table
	public static ArrayList<String[]> select_where(String tableName, String[] attributes, String condition, String value) {

		ArrayList<String[]> final_result = new ArrayList<String[]>();

	      try {
	    	  	
	    	  	//Create a SQL statement
			stmt = conn.createStatement();
			String sql = "select ";
			
			//Loop on all the attributes to select in the table
			for (int i = 0; i<attributes.length; i++) {
				if (i == attributes.length - 1) {
					sql = sql + attributes[i];
				} else {
					sql = sql + attributes[i] + ",";
				}
				
			}
			
			//define the table and the condition of the select statement
			sql = sql + " from " + tableName + " where " + condition + " = '" + value + "'";
			
			//Get all the result of the SQL query
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String[] result =  new String[attributes.length];
				
				//Loop on the required attributes
				for (int i = 0; i<attributes.length; i++) {
					result[i] = rs.getString(attributes[i]);
				}
				
				//add the result of the loop to the total result
				final_result.add(result);
				
			}
			return final_result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return final_result;
		}  
	}
	
	//method that create a NodeTrav arraylist with all the Terminals (TE) of the system and the needed data
	public static ArrayList<NodeTrav> select_TE() {
		
		//Create a variable to store the result
		ArrayList<NodeTrav> result = new ArrayList<NodeTrav>();
		
	      try {
	    	  
	    	  	//Create a SQL statement to select the Terminal elements of the SQL Database
			stmt = conn.createStatement();
			String sql = "select ID, IdentifiedObject_name from Terminal" ;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				
				//Create an object of the type NodeTrav to store the data from the SQL Database (name, ID, node type)
				NodeTrav node = new NodeTrav();
				node.name = rs.getString("IdentifiedObject_name");
				node.ID = rs.getString("ID");
				node.node_type = "TE";
				result.add(node);
			}
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    return result;
	}
	
	//method that create a NodeTrav arraylist with all the Connectivity Node (CN) of the system and the needed data
	public static ArrayList<NodeTrav> select_CN() {
		
		//Create a variable to store the result
		ArrayList<NodeTrav> result = new ArrayList<NodeTrav>();
		
	      try {
	    	  	//Create a SQL statement to select the Terminal elements of the SQL Database
			stmt = conn.createStatement();
			String sql1 = "select ID, IdentifiedObject_name from ConnectivityNode" ;
			ResultSet rs1 = stmt.executeQuery(sql1);
			while (rs1.next()) {
				
				//Create an object of the type NodeTrav to store the data from the SQL Database (name, ID, node type)
				NodeTrav node = new NodeTrav();
				node.name = rs1.getString("IdentifiedObject_name");
				node.ID = rs1.getString("ID");
				node.node_type = "CN";
				result.add(node);
			}
			
			for (NodeTrav node : result) {
				
				//For each node created with this method, create a SQL statement to add the terminals to the terminal list and update the number of terminal
				String sql2 = "select ID from Terminal where Terminal_ConnectivityNode = " + "'" + node.ID + "'";
				ResultSet rs2 = stmt.executeQuery(sql2);
				while (rs2.next()) {
				node.terminalList.add(rs2.getString("ID"));
				node.num_attachTerm = node.terminalList.size();
			}
			
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    return result;
	}
	
	//method that create a NodeTrav arraylist with all the Conducting Equipment of a certain type (CE) of the system and the needed data
	public static ArrayList<NodeTrav> select_CEtype (String CE_type){

		//Create a variable to store the result
		ArrayList<NodeTrav> result = new ArrayList<NodeTrav>();
		
	      try {
	    	  
	    	  	//Create a SQL statement to select the Terminal elements of the SQL Database
			stmt = conn.createStatement();
			String sql = "select ID, IdentifiedObject_name from " + CE_type ;
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				//Create an object of the type NodeTrav to store the data from the SQL Database (name, ID, node type)
				NodeTrav node = new NodeTrav();
				node.name = rs.getString("IdentifiedObject_name");
				node.ID = rs.getString("ID");
				node.node_type = "CE";
				node.CE_type = CE_type;
				result.add(node);
			}
			
			for (NodeTrav node : result) {
				
				//For each node created with this method, create a SQL statement to add the terminals to the terminal list and update the number of terminal
				String sql2 = "select ID from Terminal where Terminal_ConductingEquipment = " + "'" + node.ID + "'";
				ResultSet rs2 = stmt.executeQuery(sql2);
				while (rs2.next()) {
				node.terminalList.add(rs2.getString("ID"));
				node.num_attachTerm = node.terminalList.size();
			}
			}
			
	      } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		    return result;
	}
	
	//final method that create an arraylist with all the Conducting Equipment (CE) of all CE types of the system and the needed data
	public static ArrayList<NodeTrav> select_CE(){
		
		ArrayList<NodeTrav> result = new ArrayList<NodeTrav>();
		ArrayList<String> CE_types = new ArrayList<String>();
		
		//Needed CE types
		CE_types.add("BusbarSection");
		CE_types.add("LinearShuntCompensator");
		CE_types.add("ACLineSegment");
		CE_types.add("Breaker");
		CE_types.add("PowerTransformer");
		CE_types.add("SynchronousMachine");
		CE_types.add("EnergyConsumer");
		CE_types.add("GeneratingUnit");

		//Loop on all the CE types
		for (String CE_type : CE_types) {
			//Use the last method to select the CE of a certain type
			result.addAll(select_CEtype(CE_type));
		}
		return result;
	}
	
	//method that determine whether or not a circuit breaker is open
	public static boolean isBreakerOpen (String ID) {
		try {
			
			//Create a SQL statement
			stmt = conn.createStatement();
			//Select the breaker by its ID
			String sql = "select Switch_open  from Breaker where ID = '" + ID  + "'";
			//Execute the SQL query
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				//Get the result of the SQL query
				String result = rs.getString("Switch_open");
				if (result.equals("false")) {
					//Return false if the breaker is not open
					return false;
				}
			}

			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}  
	}
	
}
	


