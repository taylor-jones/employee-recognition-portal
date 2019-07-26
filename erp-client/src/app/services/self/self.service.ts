import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../../models/user.model';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SelfService {

  constructor(private httpClient: HttpClient) { }

  getSelf(): Observable<User> {
    return this.httpClient.get<User>(`/api/users/me`);
  }
}
