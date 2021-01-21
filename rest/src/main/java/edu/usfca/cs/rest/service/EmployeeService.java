package edu.usfca.cs.rest.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import edu.usfca.cs.rest.model.Employee;
import edu.usfca.cs.rest.utilities.DatabaseConnection;

/**
 * This service acts as the controller i.e. it obtains data from the database and return it to the view/main JAX resource.
 * @author ayush
 *
 */
public class EmployeeService {
	
	private final String dbPropFileLocation = "/Users/ayush/EclipseProjects/rest/database.properties";
	private DatabaseConnection connection = null;
	
	private final String EMP_ALL = "SELECT * FROM employee;";
	private final String EMP_SEL = "SELECT * FROM employee WHERE id = ?;";
	private final String EMP_INS = "INSERT INTO `employee`(`first_name`,`last_name`,`email`,`phone`)VALUES(?,?,?,?);";
	private final String EMP_UPD = "UPDATE `employee` SET `first_name` = ?, `last_name` = ?, `email` = ?, `phone` = ? WHERE `id` = ? ;";
	private final String EMP_DEL = "DELETE FROM `employee_db`.`employee` WHERE id =  ? ;";
	
	@SuppressWarnings("deprecation")
	private DatabaseConnection getConnection() {
		DatabaseConnection connection = null;
		 try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			connection = new DatabaseConnection(this.dbPropFileLocation);
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e1) {
			System.err.println("Unable to connect with mysql : "+e1.getMessage());
		}
		return connection;
	
	}

	public List<Employee> getAllEmployees(){
		
		this.connection = getConnection();
		List<Employee> employees = new ArrayList<>();
		try (Connection conn = this.connection.getConnection(); 
				Statement stmt = conn.createStatement()) {
        
            ResultSet rs = stmt.executeQuery(EMP_ALL);
            while (rs.next()) {
            	
            	employees.add(new Employee(rs.getInt("id"),rs.getString("first_name")
            			,rs.getString("last_name"),rs.getString("email")
            			,rs.getString("phone")));
            }
        }
        catch (SQLException ex){
        	System.err.println("SQLException (ALL)"+ex.getMessage());
        }
		return employees;
	}
	
	public Employee getEmployee(Integer employeeId) {
		Employee e1= null;
		this.connection = getConnection();
		
		try (Connection conn = this.connection.getConnection(); 
				PreparedStatement stmt = conn.prepareStatement(EMP_SEL)) {
			stmt.setInt(1, employeeId);
        
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	
            	e1 = new Employee(rs.getInt("id"),rs.getString("first_name")
            			,rs.getString("last_name"),rs.getString("email")
            			,rs.getString("phone"));
            }
        }
        catch (SQLException ex){
        	System.err.println("SQLException (ALL)"+ex.getMessage());
        }
		return e1;
	}

	public Employee addEmployee(Employee e) {
		
		this.connection = getConnection();
		int insertId = 0;
		
		try (Connection conn = this.connection.getConnection(); 
				PreparedStatement stmt = conn.prepareStatement(EMP_INS, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, e.getFirst_name());
			stmt.setString(2, e.getLast_name());
			stmt.setString(3, e.getEmail());
			stmt.setString(4, e.getPhone());
        
			int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating employee failed, no rows affected.");
            }
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                while (rs.next()) {
                    insertId = rs.getInt(1);
                    e.setId(insertId);
                }
            }
        }
        catch (SQLException ex){
        	System.err.println("SQLException (ALL)"+ex.getMessage());
        }
		return e;
		
	}

	public Employee updateEmployee(Employee e) {
		this.connection = getConnection();
		
		try (Connection conn = this.connection.getConnection(); 
				PreparedStatement stmt = conn.prepareStatement(EMP_UPD, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, e.getFirst_name());
			stmt.setString(2, e.getLast_name());
			stmt.setString(3, e.getEmail());
			stmt.setString(4, e.getPhone());
			stmt.setInt(5, e.getId());
        
			int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating employee failed, no rows affected.");
            }
            
        }
        catch (SQLException ex){
        	System.err.println("SQLException (ALL)"+ex.getMessage());
        }
		return getEmployee(e.getId());
	}

	public List<Employee> removeEmployee(Integer employeeId) {
		
		this.connection = getConnection();
		
		try (Connection conn = this.connection.getConnection(); 
				PreparedStatement stmt = conn.prepareStatement(EMP_DEL)) {
			stmt.setInt(1, employeeId);
        
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating employee failed, no rows affected.");
            }
            
        }
        catch (SQLException ex){
        	System.err.println("SQLException (ALL)"+ex.getMessage());
        }
		return getAllEmployees();
	}
	
}
