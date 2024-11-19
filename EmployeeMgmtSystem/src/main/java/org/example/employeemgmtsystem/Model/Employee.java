package org.example.employeemgmtsystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Employee {

    //Validation Annotations//

    @NotEmpty(message = "ID can't be empty!")
    @Size(min= 3, message = "ID length must be more than \"2\" characters!")
    private String ID;

    @NotEmpty(message = "Name can't be empty!")
    @Size(min= 5, message = "Name length must be more than \"4\" letters!")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Name must be letters only without numbers!")
    private String name;

    @NotEmpty(message = "Email can't be empty!")
    @Email(message = "Invalid email format!")
    private String email;


    @Pattern(regexp =  "^05\\d{8}$", message = "Phone number must start with \"05\", and contains exactly \"10\" digits! ")
     //...\\d{8}: Matches the remaining 8 digits after the prefix 05
    //try digit
    private String phoneNumber;


    @Positive(message = "Age must be a number only!")
    @Min(26)
    private int age;

    @NotEmpty(message = "Position can't be empty!")
    @Pattern(regexp = "supervisor|coordinator", message = "Position must be either \"supervisor\" or \"coordinator\" only! ")
    private String position;

    @AssertFalse(message = "This variable's value must initially set to false.")
    private boolean onLeave;


    @NotNull(message = "Hire Date can't be null!")
    @PastOrPresent(message = "Hire Date must be in the present or the past.")
    @JsonFormat  //(yyyy-mm-dd)
    private Date hireDate;


    @NotNull(message = "Annual Leave can't be null!")
    @Positive(message = "Annual Leave must be a positive number only!")
    private int annualLeave;


    @AssertFalse(message = "This variable's value must initially set to false.")
    public boolean isOnLeave() {
        return onLeave;
    }
}
