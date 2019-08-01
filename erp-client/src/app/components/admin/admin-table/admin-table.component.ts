import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import {EmployeeService} from 'src/app/services/employee/employee.service';

@Component({
  selector: 'admin-table',
  templateUrl: './admin-table.component.html',
  styleUrls: ['./admin-table.component.scss']
})
export class AdminTableComponent implements OnInit {

  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;

  employees: any = null;
  errorMessage: string;
  displayedColumns: string[] = ['id', 'email', 'firstName', 'lastName'];
  pageSizeOptions: number[];
  pageSize: number;
  length: number;
  dataSource = new MatTableDataSource([]);

  constructor(private employeeService: EmployeeService) {}

  ngOnInit() {
    this.getAllEmployees();
  }

  getAllEmployees(): void {
    this.employeeService.getAllEmployees().subscribe (
      (employees) => {
        this.employees = employees;
        this.length = employees.length;
        this.dataSource = new MatTableDataSource(this.employees);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      (error) => {
        this.errorMessage = 'Failed to load employees';
      },
    );
  }

  public filter(value: string) {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
  }
  
}
