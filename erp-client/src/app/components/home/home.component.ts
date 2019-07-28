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
  employees: Employee[] = null;
  employee: Employee;
  errorMessage: string;
  employeeId: number;
  id: number;

  constructor(private employeeService: EmployeeService) { }

  ngOnInit() {
  }

  getAllEmployees(): void {
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
}
