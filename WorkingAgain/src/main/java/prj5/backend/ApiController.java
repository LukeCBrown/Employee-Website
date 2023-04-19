package prj5.backend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prj5.database.Database;
import prj5.database.Dependent;
import prj5.database.Employee;

import java.util.List;


@Controller
public class ApiController {

    @GetMapping("/")
    public String indexMethod() {
        return "index";
    }


    @GetMapping("/createEmployee")
    public String createEmployeeMethod(Model model) {
        model.addAttribute("emptyEmployee", new Employee());
        model.addAttribute("ErrorMessage", "");

        return "createEmployee";
    }

    @PostMapping("/createOne")
    public String createOne(@ModelAttribute("emptyEmployee") Employee e, Model model) throws Exception {
        String result = Database.createEmployee(e);
        if (result == null) {
            return "index";
        } else {
            System.out.println(result);
            model.addAttribute("ErrorMessage", result);
            return "createEmployee";
        }
    }

    @GetMapping("/retrieve")
    public String retrieveMethod(Model model) {
        model.addAttribute("emptySearch", new Employee());
        return "retrieve";
    }

    @PostMapping("/retrieveOne")
    public String searchOne(@ModelAttribute("emptySearch") Employee e, Model model) throws Exception {
        List<Employee> result = Database.getEmployeeBySSN(e.getSsn());
        if (result == null) {
            return "retrieve";
        } else {
            System.out.println(result);
            model.addAttribute("employees", result);
            return "results";
        }
    }

    @PostMapping("/update")
    public String updateOne(@ModelAttribute("updateEmployee") Employee updateEmployee, Model model) throws Exception {
        boolean result = Database.updateEmployeeBySSN(updateEmployee);
        return retrieveMethod(model);
    }


    @GetMapping("/update/{ssn}")
    public String updateMethod(@PathVariable("ssn") String ssn, Model model) throws Exception {
        List<Employee> employees = Database.getEmployeeBySSN(ssn);
        Employee employee = null;
        if (!employees.isEmpty()) {
            employee = employees.get(0);
        }
        model.addAttribute("employee", employee);
        return "updateEmployee";
    }


    @GetMapping("/dependentsPage")
    public String dependentMethod(Model model) throws Exception {
        List<Dependent> result = Database.getAllDependents();
        model.addAttribute("dependents", result);
        return "dependents";
    }

    @GetMapping("/addDependentOne/{ssn}")
    public String createDependentMethod(@PathVariable("ssn") String ssn, Model model) throws Exception {
        Dependent d = new Dependent();
        d.setParentSSN(ssn);
        model.addAttribute("newDependent", d);
        return "createDependent";
    }


    @PostMapping("/createDependent")
    public String createDependent(@ModelAttribute("newDependent") Dependent newDependent, Model model) throws Exception {
        String result = Database.createDependent(newDependent);
        return "index";
    }



    @GetMapping("/delete/{ssn}")
    public String deleteEmployeeBySSN(@PathVariable("ssn") String ssn, Model model) throws Exception {
        boolean result = Database.deleteEmployeeBySSN(ssn);

            return retrieveMethod(model);
    }
}
