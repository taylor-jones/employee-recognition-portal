import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User, UserWithTimeStamp} from '../../models/user.model';

@Injectable({
  providedIn: 'root'
})

export class UserService {

  private baseEndpoint = `/api/users`;
  private jsonHeaders: HttpHeaders = null;

  constructor(private httpClient: HttpClient) {
    this.jsonHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  }

	getUserByUsername(username: string): Observable<User> {
		return this.httpClient.get<User>(`${this.baseEndpoint}/username/${username}`);
  }
  
  getUserDetailsByUsername(username: string): Observable<UserWithTimeStamp> {
    return this.httpClient.get<UserWithTimeStamp>(`${this.baseEndpoint}/username/${username}/details`);
  }

  checkUsername(username: string): Observable<boolean> {
    return this.httpClient.get<boolean>(`${this.baseEndpoint}/validate/${username}`);
  }

  getAvailability(): Observable<any> {
    return this.httpClient.get(`${this.baseEndpoint}/availability`);
  }

  getAllUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(`${this.baseEndpoint}`);
  }

  addUser(user: User): Observable<string> {
    return this.httpClient.post<string>(`${this.baseEndpoint}`, user, {headers: this.jsonHeaders});
  }

  addUserCreatedUser(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.baseEndpoint}/newAccount`, user, {headers: this.jsonHeaders});
  }

  updateUser(user: User): Observable<User> {
    return this.httpClient.put<User>(`${this.baseEndpoint}/${user.id}`, user, {headers: this.jsonHeaders});
  }

  deleteUser(user: User): Observable<User> {
    return this.httpClient.delete<User>(`${this.baseEndpoint}/${user.id}`, {headers: this.jsonHeaders});
  }
}
