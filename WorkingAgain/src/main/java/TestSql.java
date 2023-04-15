import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestSql {
    public static Connection getConnection() throws Exception {
        Connection connection = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/prj4", "Luke", "Luke35812358molo");
        return connection;
    }
    public static void main(String[] args) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(getEmployeeByLastName("Bag")));
    }
    public static List<Employee> getEmployeeByLastName(String lastName) throws Exception {
        String query = String.format("SELECT * from Employee WHERE LName = '%s'", lastName);
        List<Employee> employeeList = new ArrayList<>();
        try(Connection connection = getConnection()){
            try(Statement statement = connection.createStatement()) {
                try(ResultSet rs = statement.executeQuery(query)){
                    while(rs.next()){
                        Employee e = new Employee(
                                rs.getString("SSN"),
                                rs.getDate("dob"),
                                rs.getString("FName"),
                                rs.getString("Minit"),
                                rs.getString("LName"));
                        employeeList.add(e);
                    }
                }
            }
        }
        return employeeList;
    }

    public static List<Employee> getEmployeeByFirstName(String firstName) throws Exception {
        String query = String.format("SELECT * from Employee WHERE FName = '%s'", firstName);
        List<Employee> employeeList = new ArrayList<>();
        try(Connection connection = getConnection()){
            try(Statement statement = connection.createStatement()) {
                try(ResultSet rs = statement.executeQuery(query)){
                    while(rs.next()){
                        Employee e = new Employee(
                                rs.getString("SSN"),
                                rs.getDate("dob"),
                                rs.getString("FName"),
                                rs.getString("Minit"),
                                rs.getString("LName"));
                        employeeList.add(e);
                    }
                }
            }
        }
        return employeeList;
    }

    public static List<Employee> getEmployeeBySSN(String ssn) throws Exception {
        String query = String.format("SELECT * from Employee WHERE SSN = '%s'", ssn);
        List<Employee> employeeList = new ArrayList<>();
        try(Connection connection = getConnection()){
            try(Statement statement = connection.createStatement()) {
                try(ResultSet rs = statement.executeQuery(query)){
                    while(rs.next()){
                        Employee e = new Employee(
                                rs.getString("SSN"),
                                rs.getDate("dob"),
                                rs.getString("FName"),
                                rs.getString("Minit"),
                                rs.getString("LName"));
                        employeeList.add(e);
                    }
                }
            }
        }
        return employeeList;
    }
    public static void deleteEmployeeBySSN(String ssn) throws Exception {
        String query = String.format("DELETE FROM Employee WHERE SSN = '%s'", ssn);
        try(Connection connection = getConnection()){
            try(Statement statement = connection.createStatement()){
                try(ResultSet)
            }
        }
    }

}
