import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {PasswordReset, RecoveryQuestion} from '../../models/recovery.model';
import {Router} from '@angular/router';
import {catchError, map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AccountRecoveryService {

  constructor(private httpClient: HttpClient) {
  }

  getRecoveryQuestions(username: string): Observable<RecoveryQuestion[]> {
    return this.httpClient.get<RecoveryQuestion[]>(`api/recover/${username}/questions`);
  }

  setRecoveryQuestions(username: string, questions: RecoveryQuestion[]): Observable<void> {
    return this.httpClient.post<void>(`/api/recover/${username}/questions`, questions);
  }

  checkRecoveryAnswers(username: string, questions: RecoveryQuestion[]): Observable<void> {
    return this.httpClient.post<void>(`/api/recover/${username}/answers`, questions);
  }

  setNewPassword(updateUser: PasswordReset): Observable<void> {
    return this.httpClient.post<void>(`/api/recover/${updateUser.username}/newPassword`, updateUser);
  }
}
