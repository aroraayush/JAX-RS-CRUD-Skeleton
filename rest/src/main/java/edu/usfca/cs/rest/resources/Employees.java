package edu.usfca.cs.rest.resources;

import java.util.List;
import edu.usfca.cs.rest.model.Employee;
import edu.usfca.cs.rest.service.EmployeeService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Connects to database and performs CRUD operations for Employee data
 * GET, PUT, POST and DELETE methods for employees data
 */
@Path("/employees")
public class Employees {
	
	EmployeeService es = new EmployeeService();

    /**
     * Method handling HTTP GET request. 
     * @return JSON Array that represents list of employees.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getEmployees() {
        return es.getAllEmployees();
    }
    

    /**
     * Method handling HTTP GET request. 
     * @return JSON that represents an employee data.
     */
    @GET
    @Path("/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployee(@PathParam("employeeId") Integer employeeId) {
    	return es.getEmployee(employeeId);
    }
    
    /**
     * Method handling HTTP POST request. 
     * @return JSON that represents an employee data along with the insertion id.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Employee addEmployee(Employee e) {
    	es.addEmployee(e);
    	return e;
    }
    
    /**
     * Method handling HTTP POST request. 
     * @return JSON that represents updated employee data.
     */
    @PUT
    @Path("/{employeeId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Employee updateEmployee(@PathParam("employeeId") Integer employeeId, Employee e) {
    	e.setId(employeeId);
    	return es.updateEmployee(e);
    }
    
    /**
     * Method handling HTTP POST request. 
     * @return JSON Array that represents all employees except the deleted one.
     */
    @DELETE
    @Path("/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> deleteEmployee(@PathParam("employeeId") Integer employeeId) {
    	return es.removeEmployee(employeeId);
    }
}
