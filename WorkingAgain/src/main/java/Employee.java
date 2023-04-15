import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Date;

@Data
@AllArgsConstructor
public class Employee {
    private String ssn;
    private Date dob;
    private String fName;
    private String mInit;
    private String lName;
}

