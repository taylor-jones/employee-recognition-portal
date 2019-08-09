import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Award } from '../../models/award.model';

@Injectable({ providedIn: 'root' })
export class ReportService {
  private headers: HttpHeaders = null;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
  ) {}


  getUserAwardCounts(): Observable<any> {
    return this.http.get<any[]>(`/api/users/awards/totals`);
  }

  getEmployeeAwardCounts(): Observable<any> {
    return this.http.get<any[]>(`/api/employees/awards/totals`);
  }

  getEmployeeAwardDiversity(): Observable<any> {
    return this.http.get<any[]>(`/api/employees/awards/diversity`);
  }

  getEmployeesWithAwardType(awardTypeId: number): Observable<any> {
    return this.http.get<any[]>(`/api/employees/byAwardType/${awardTypeId}`);
  }

  getAwardTypeCounts(): Observable<any> {
    return this.http.get<any[]>(`/api/awardTypes/totals`);
  }

  getRegionAwardCounts(): Observable<any> {
    return this.http.get<any[]>(`/api/regions/awards/totals`);
  }

  getRegionAwards(regionId: number): Observable<any> {
    return this.http.get<any[]>(`/api/regions/${regionId}/awards`);
  }
  
  getRegionEmployees(regionId: number): Observable<any> {
    return this.http.get<any[]>(`/api/regions/${regionId}/employees`);
  }

}

