package prj5.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartialEmployee {
    private String ssn;
    private Date dob;
    private String fName;
    private String mInit;
    private String lName;
}

