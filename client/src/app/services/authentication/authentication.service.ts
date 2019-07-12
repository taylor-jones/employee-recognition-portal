import { Injectable } from '@angular/core';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  user: { u: string, p: string} = null;

  constructor(private router: Router) { }

  authenticate(username: string, password: string): void {
    console.log(username);
    console.log(password);
    this.user = { u: username, p: password};
    this.router.navigate(['/']);
  }
}
