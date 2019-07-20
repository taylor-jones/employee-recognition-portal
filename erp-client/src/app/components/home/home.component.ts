import {Component, OnInit} from '@angular/core';
import {EmployeeService} from '../../services/employee/employee.service';
import {Employee} from '../../models/employee.model';

@Component({
  selector: 'erp-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  awards: Employee[] = [];
  employees: Employee[] = [];
  employee: Employee;
  errorMessage: string;
  employeeId: number;

  constructor(private employeeService: EmployeeService) { }

  ngOnInit() {
  }

  getAllEmployees(): void {
    this.employees = [];
    this.employeeService.getAllEmployees().subscribe(
      (employees) => {
        this.employees = employees;
      },
      (error) => {
        this.errorMessage = 'Failed to load employees';
      },
      () => {
        // If you want to do something
      }
    );
  }

  getEmployeeById(id: number): void {
    this.employees = [];
    this.employee = null;
    this.employeeService.getEmployeeById(id).subscribe(
      (employee) => {
        this.employee = employee;
      },
      (error) => {
        this.errorMessage = 'Failed to load employee';
      },
      () => {
        // If you want to do something
      }
    );
  }

  getAllAwards(): void {
    this.awards = [];
    this.employeeService.getAllAwards().subscribe(
      (awards) => {
        this.awards = awards;
      },
      (error) => {
        this.errorMessage = 'Failed to load awards';
      },
      () => {
        // If you want to do something
      }
    );
  }
}
