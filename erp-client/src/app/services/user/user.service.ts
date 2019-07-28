import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../models/user.model';

@Injectable({
	providedIn: 'root'
})

export class UserService {
	constructor(private httpClient: HttpClient) {}

	getUserById(id: number): Observable<User> {
		return this.httpClient.get<User>(`/api/users/${id}`);
	}

	getAllUsers(): Observable<User[]> {
		return this.httpClient.get<User[]>(`/api/users`);
	}
}
