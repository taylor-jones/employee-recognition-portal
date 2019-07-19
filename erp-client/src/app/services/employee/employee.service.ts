import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Employee } from '../../models/employee.model';

@Injectable({
	providedIn: 'root'
})

export class EmployeeService {
	constructor(private httpClient: HttpClient) {}

	getEmployeeById(id: number): Observable<Employee> {
		return this.httpClient.get<Employee>(`/api/employees/${id}`);
  }
  
  getAllEmployees(): Observable<Employee[]> {
    return this.httpClient.get<Employee[]>('/api/employees').pipe(
      map((result: any) => {
        return result._embedded.employees;
      })
    )
  }
}
