import { Injectable } from '@angular/core';
import {User} from '../../models/user.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserAccountService {

  constructor(private httpClient: HttpClient) { }

  getAllUsers(): Observable<User[]>  {
    return this.httpClient.get<User[]>(`/api/users`);
  }
}
