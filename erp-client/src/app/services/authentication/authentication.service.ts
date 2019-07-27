import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Credentials} from '../../models/credentials.model';
import {Observable, of, throwError} from 'rxjs';
import {catchError, map, retry} from 'rxjs/operators';
import {User} from '../../models/user.model';

@Injectable({
  providedIn: 'root'
})

export class AuthenticationService {

  user: User = null;
  isLoggedIn: boolean;

  constructor(private http: HttpClient,
              private router: Router) {
  }

  authenticate(credentials: Credentials): Observable<User> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded'
      })
    };

    const data = `username=${encodeURIComponent(credentials.username)}&password=${encodeURIComponent(credentials.password)}`;

    return this.http.post<User>(`/api/login`, data, httpOptions)
      .pipe(
        map((user) => {
          this.isLoggedIn = true;
          this.user = user;
          return user;
        }),
        catchError(error => {
          this.isLoggedIn = false;
          this.user = null;
          return throwError(error);
        })
      );
  }

  isAuthenticated(): Observable<boolean> {
    if (this.user !== null && this.isLoggedIn) {
      return of(this.isLoggedIn);
    }
    return this.http.get<User>(`/api/users/whoAmI`)
      .pipe(
        map(
          user => {
            this.isLoggedIn = true;
            this.user = user;
            return this.isLoggedIn;
          }
        ),
        catchError(error => {
          this.isLoggedIn = false;
          this.user = null;
          return of(this.isLoggedIn);
        })
      );
  }

  logout(): void {
    this.http.post(`/api/logout`, {})
      .pipe(
        retry(3)
      )
      .subscribe(
        success => {
          this.isLoggedIn = false;
          this.user = null;
          this.router.navigate([ 'login' ]);
        },
        error => {
          this.isLoggedIn = false;
          this.user = null;
          this.router.navigate([ 'login' ]);
        }
      );
  }

}
