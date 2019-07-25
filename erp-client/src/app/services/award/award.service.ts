import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Award } from '../../models/award.model';

@Injectable({
	providedIn: 'root'
})

export class AwardService {
  private headers: HttpHeaders = null;
  private endpointBase: string = null;

	constructor(private httpClient: HttpClient) {}

	getAwardById(id: number): Observable<Award> {
		return this.httpClient.get<Award>(`/api/awards/${id}`);
	}

	getAllAwards(): Observable<Award[]> {
		return this.httpClient.get<Award[]>(`/api/awards`);
	}
}


