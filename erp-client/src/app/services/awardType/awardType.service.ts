import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AwardType } from '../../models/awardType.model';

@Injectable({
	providedIn: 'root'
})

export class AwardTypeService {
	constructor(private httpClient: HttpClient) {}

	getAwardTypeById(id: number): Observable<AwardType> {
		return this.httpClient.get<AwardType>(`/api/awardTypes/${id}`);
	}

	getAllAwardTypes(): Observable<AwardType[]> {
		return this.httpClient.get<AwardType[]>(`/api/awardTypes`);
	}
}
