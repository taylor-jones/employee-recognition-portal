import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../models/user.model';

@Injectable({
	providedIn: 'root'
})

export class UserService {

	private baseEndpoint = `/api/users`;
	private jsonHeaders: HttpHeaders = null;

	constructor(private httpClient: HttpClient) {
		this.jsonHeaders = new HttpHeaders({'Content-Type': 'application/json'});
	}

	getUserById(id: number): Observable<User> {
		return this.httpClient.get<User>(`${this.baseEndpoint}/${id}`);
	}

	getAllUsers(): Observable<User[]> {
		return this.httpClient.get<User[]>(`${this.baseEndpoint}`);
	}

	addUser(user: User): Observable<string> {
		return this.httpClient.post<string>(`${this.baseEndpoint}`, user, {headers: this.jsonHeaders});
	}

	deleteUser(user: User): Observable<string> {
		return this.httpClient.delete<string>(`${this.baseEndpoint}/${user.id}`, {headers: this.jsonHeaders});
	}
}
