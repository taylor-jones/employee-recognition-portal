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
  // user: { username: string; password: string } = null;
  //
  // constructor(private route: ActivatedRoute,
  //             private router: Router,
  //             private httpClient: HttpClient) {
  // }
  //
  // authenticate(u: string, p: string): void {
  //   const httpOptions = {
  //     headers: new HttpHeaders({
  //       'Content-Type': 'application/x-www-form-urlencoded'
  //     })
  //   };
  //
  //   const data = `username=${encodeURIComponent(u)}&password=${encodeURIComponent(p)}`;
  //
  //   this.httpClient.post<Login>(`/api/login`, data, httpOptions).subscribe(res => {
  //     if (res) {
  //       console.log(res);
  //       this.router.navigate(['/']);
  //     }
  //   });
  // }

  user: User = null;
  isLoggedIn: boolean;

  constructor(private http: HttpClient,
              private router: Router) {
  }

  authenticate(credentials: Credentials): Observable<void> {
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
    return this.http.get<any>(`/api/user`)
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
