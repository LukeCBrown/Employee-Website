package prj5.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Database {
    public static Connection getConnection() throws Exception {
        Connection connection = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/prj4", "Luke", "Luke35812358molo");
        return connection;
    }

    public static void main(String[] args) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        createEmployee(new Employee(
//                "123456789",
//                java.sql.Date.valueOf("2006-5-15"),
//                "Haerin",
//                "",
//                "Kang")
//        );
//        System.out.println(gson.toJson(getEmployeeByLastName("Kang")));
//        createDependent(new prj4.Dependent(
//                "123456789",
//                "Min",
//                "",
//                "Kang",
//                "Daughter")
//        );
//        System.out.println(gson.toJson(getDependentBySSN("123456789")));

        // Testing cascade on delete
//        deleteEmployeeBySSN("123456789");
//        System.out.println(gson.toJson(getEmployeeByLastName("Kang")));
//        System.out.println(gson.toJson(getDependentBySSN("123456789")));
        System.out.println(gson.toJson((getEmployeeByLastName("Kang"))));
            Employee updateHaerin = new Employee("123456789", null, null, "T", null);
          updateEmployeeBySSN(updateHaerin);
        System.out.println(gson.toJson(getEmployeeByLastName("Kang")));

    }


    public static String createEmployee(Employee newEmployee) throws Exception {
        if (newEmployee.getSsn().length() != 9) {
            return "Invalid SSN";
        }
        String query = String.format("insert into prj4.Employee(SSN, dob, FName, Minit, LName) " +
                        "values(%s, '%s', '%s', '%s', '%s')",
                newEmployee.getSsn(), newEmployee.getDob(), newEmployee.getFName(), newEmployee.getMInit(), newEmployee.getLName());
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try {
                    statement.execute(query);
                    return null;
                } catch (Exception ex) {
                    return "Creation Failed: " + ex.getMessage();
                }
            }
        }
    }


//    public static boolean createDependent(Dependent newDependent) throws Exception {
//        String query = String.format("insert into prj4.Dependent(ParentSSN, FName, Minit, LName, Relationship) " +
//                        "values(%s, '%s', '%s', '%s', '%s')",
//                newDependent.getParentSSN(), newDependent.getFName(), newDependent.getMInit(), newDependent.getLName(), newDependent.getRelationship());
//        try (Connection connection = getConnection()) {
//            try (Statement statement = connection.createStatement()) {
//                return statement.execute(query);
//            }
//        }
//    }

    public static String createDependent(Dependent newDependent) throws Exception {
        String query = String.format("INSERT INTO prj4.Dependent(ParentSSN, FName, Minit, LName, Relationship) " +
                        "VALUES(%s, '%s', '%s', '%s', '%s')",
                newDependent.getParentSSN(), newDependent.getFName(), newDependent.getMInit(), newDependent.getLName(), newDependent.getRelationship());
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try {
                    statement.execute(query);
                    return null;
                } catch (Exception ex) {
                    return "Creation Failed: " + ex.getMessage();
                }
            }
        }
    }

    public static List<Employee> getEmployeeByLastName(String lastName) throws Exception {
        String query = String.format("SELECT * from prj4.Employee WHERE LName = '%s'", lastName);
        List<Employee> employeeList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery(query)) {
                    while (rs.next()) {
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

    public static List<Dependent> getAllDependents() throws Exception {
        String query = String.format("SELECT * from prj4.Dependent");
        List<Dependent> DependentList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery(query)) {
                    while (rs.next()) {
                        Dependent e = new Dependent(
                                rs.getString("ParentSSN"),
                                rs.getString("FName"),
                                rs.getString("Minit"),
                                rs.getString("LName"),
                                rs.getString("Relationship"));
                        DependentList.add(e);
                    }
                }
            }
        }
        return DependentList;
    }

    public static List<Employee> getEmployeeByFirstName(String firstName) throws Exception {
        String query = String.format("SELECT * from prj4.Employee WHERE FName = '%s'", firstName);
        List<Employee> employeeList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery(query)) {
                    while (rs.next()) {
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
        String query = String.format("SELECT * from prj4.Employee WHERE SSN like '%%%s%%'", ssn);
        List<Employee> employeeList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery(query)) {
                    while (rs.next()) {
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

    public static boolean updateEmployeeBySSN(Employee updateEmployee) throws Exception {
        List<String> setClauses = new ArrayList<>();

        BiConsumer<String,Object> setIfNotNull = (c,i) -> {
            if(i != null){
                setClauses.add(c+" = '" + i + "'");
            }
        };
        setIfNotNull.accept("dob", updateEmployee.getDob());
        setIfNotNull.accept("FName", updateEmployee.getFName());
        setIfNotNull.accept("MInit", updateEmployee.getMInit());
        setIfNotNull.accept("LName", updateEmployee.getLName());

        String query = String.format("UPDATE prj4.Employee SET %s WHERE ssn = %s",
                String.join(",", setClauses), updateEmployee.getSsn());
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                return statement.execute(query);
            }
        }
    }

    public static boolean deleteEmployeeBySSN(String ssn) throws Exception {
        String query = String.format("DELETE FROM prj4.Employee WHERE SSN = '%s'", ssn);
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                return statement.execute(query);
            }
        }
    }
}
