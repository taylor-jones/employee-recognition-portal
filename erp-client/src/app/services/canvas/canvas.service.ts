import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export class CanvasService {

  private jsonHeaders: HttpHeaders = null;
  private textHeaders: HttpHeaders = null;

  constructor(private httpClient: HttpClient) { 
    this.jsonHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  }

  addSignature(imgBase64: string): Observable<string> {
    return this.httpClient.post<string>('/api/signatures', {"signature": imgBase64}, {headers: this.jsonHeaders});
  }

  getSignature(): Observable<string> {
    return this.httpClient.get('/api/signatures', {responseType: 'text'});
  }

}
