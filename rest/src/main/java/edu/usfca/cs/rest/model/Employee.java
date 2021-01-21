package edu.usfca.cs.rest.model;

/**
 * Employee Class : Contains meta data of an employee
 * @author ayush
 *
 */
public class Employee {
	
	private Integer id;
	private String first_name;
	private String last_name;
	private String email;
	private String phone;
	
	// Default Constructor for JSON conversion
	public Employee() {}
	
	public Employee(Integer id, String first_name, String last_name, String email, String phone) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.phone = phone;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
