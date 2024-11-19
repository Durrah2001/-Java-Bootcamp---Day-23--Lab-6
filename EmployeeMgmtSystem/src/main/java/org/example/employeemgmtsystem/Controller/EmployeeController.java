package org.example.employeemgmtsystem.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.employeemgmtsystem.ApiResponse.ApiResponse;
import org.example.employeemgmtsystem.Model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employeeMgmt-system")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();

    //CRUD endpoints

    @GetMapping("/display")
    public ResponseEntity getEmployee(){
        return ResponseEntity.status(200).body(employees);
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee, Errors error){

        if(error.hasErrors()){
            String msg = error.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(msg);
        }

        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee added successfully!"));

    }

    @PutMapping("/update/{ID}")
    public ResponseEntity updateEmployee(@PathVariable String ID, @RequestBody @Valid Employee employee, Errors error){
        if(error.hasErrors()){
            String msg = error.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(msg);
        }

        for (Employee e : employees) {
            if(e.getID().equals(ID)) {
                e.setName(employee.getName());
                e.setID(employee.getID());
                e.setEmail(employee.getEmail());
                e.setPhoneNumber(employee.getPhoneNumber());
                e.setAge(employee.getAge());
                e.setPosition(employee.getPosition());
                e.setOnLeave(employee.isOnLeave());
                e.setHireDate(employee.getHireDate());
                e.setAnnualLeave(employee.getAnnualLeave());

                return ResponseEntity.status(200).body(new ApiResponse("Employee updated successfully!"));
            }
        }
          return ResponseEntity.status(400).body(new ApiResponse("Employee with this ID not found!"));
    }


    @DeleteMapping("/delete/{ID}")
    public ResponseEntity deleteEmployee(@PathVariable String ID){

        boolean isFound = false;
        for(Employee e : employees){
            if(e.getID().equals(ID)) {
                employees.remove(e);
                isFound = true;
                break;
            }
        }
        if(isFound) {
            return ResponseEntity.status(200).body(new ApiResponse("Employee with this ID deleted successfully!"));
        }
        else {
            return ResponseEntity.status(400).body(new ApiResponse("Employee with this ID not found!"));
        }
    }

    //End CRUD endpoints//
    //////////////////////////


    @GetMapping("/search-ByPos/{pos}")
    public ResponseEntity searchByPos(@PathVariable String pos){

        // //No need for @Valid and Error, because the pos variable validating
        if(!(pos.equalsIgnoreCase("supervisor") || pos.equalsIgnoreCase("coordinator"))){

            return ResponseEntity.status(200).body(new ApiResponse("Only 'supervisor' or 'coordinator' are available positions."));
        }


        ArrayList<Employee> basedPos = new ArrayList<>();

        for(Employee e : employees){
            if(e.getPosition().equalsIgnoreCase(pos) || e.getPosition().equalsIgnoreCase(pos)){
                basedPos.add(e);
            }
        } //End for

          return ResponseEntity.status(200).body(basedPos);


    } //End endpoint


    @GetMapping("/display-ByAge/{minAge}")
    public ResponseEntity displayByAge(@PathVariable int minAge){

        if(minAge < 26){
            return ResponseEntity.status(400).body(new ApiResponse("Age must be greater than 25."));
        }


        ArrayList<Employee> basedOnAge = new ArrayList<>();
        for(Employee e : employees){
            if(e.getAge() >= minAge){
                basedOnAge.add(e);
            }


        } //End for

        return ResponseEntity.status(200).body(basedOnAge);

    }


    @PutMapping("/apply-leave/{ID}")
    public ResponseEntity applyForLeave(@PathVariable String ID) {


        for (Employee e : employees) {
            //Check if emp exist
            if (!(e.getID().equals(ID))) {
                return ResponseEntity.status(400).body(new ApiResponse("Employee with this ID not found!"));
            }

            if (e.isOnLeave()) {
                return ResponseEntity.status(400).body(new ApiResponse("This employee has a leave already!"));
            }

            if (e.getAnnualLeave() < 1) {
                return ResponseEntity.status(400).body(new ApiResponse("This employee doesn't have remaining days to apply for leave!"));

            }

            e.setOnLeave(true);
            e.setAnnualLeave(e.getAnnualLeave() - 1);

        } //End for

        return ResponseEntity.status(200).body(new ApiResponse("Apply for a leave done successfully for this employee!"));

    }

     @GetMapping("/display-noHasLeave")
    public ResponseEntity displayWithNonLeave(){

        ArrayList<Employee> noLeave = new ArrayList<>();
        for(Employee e : employees){

            if(e.getAnnualLeave() == 0){
                noLeave.add(e);
            }
        }

        return ResponseEntity.status(200).body(noLeave);

    }


     @PutMapping("/change-pos/{ID}/{pos}")
    public ResponseEntity promoteToSupervisor(@PathVariable String ID, @PathVariable String pos){


        for(Employee e : employees){

            if(!(e.getID().equals(ID))){
                return ResponseEntity.status(400).body(new ApiResponse("Employee with this ID not found!"));
            }

            if(!(pos.equalsIgnoreCase("supervisor"))){
                return ResponseEntity.status(400).body(new ApiResponse("Only supervisors can promote the employ!"));
            }

            if(e.getAge() < 30){
                return ResponseEntity.status(400).body(new ApiResponse("Only employees that their ages at least 30, can promote to position of supervisor!"));

            }
            if(e.isOnLeave()){
                return ResponseEntity.status(400).body(new ApiResponse("Employee can't promote if he/she currently on leave!"));
            }

            e.setPosition("Supervisor");


        } //End for


       return ResponseEntity.status(200).body(new ApiResponse("Position of this employee changed to supervisor successfully!"));

    } //End endpoint






}//End controller
