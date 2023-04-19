package prj5.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dependent {
    private String parentSSN;
    private String fName;
    private String mInit;
    private String lName;
    private String relationship;

}

