import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export class CanvasService {

  private headers: HttpHeaders = null;
  private endpointBase: string = null;

  constructor(private httpClient: HttpClient) { 
    this.endpointBase = '/testing'; 
    this.headers = new HttpHeaders({'Content-Type': 'application/json'});
  }

  addSignature(imgBase64: string): Observable<string> {
    return this.httpClient.post<string>('/api/v1/signature', {"signature": imgBase64}, {headers: this.headers});
  }

}
