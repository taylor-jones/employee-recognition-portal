import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './services/authentication/authentication.service';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';


@Component({
  selector: 'erp-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'employee-recognition-portal';
  _isLoggedIn: boolean = false;
  _isAdmin: boolean = false;

  constructor (
    private _authService: AuthenticationService,
    private _router: Router,
    private _cookieService: CookieService,
  ) {}

  ngOnInit(): void {
    this.refreshLoginState();
  }

  get isLoggedIn() {
    return this._isLoggedIn;
  }

  get isAdmin() {
    return this._isAdmin;
  }

  handleLogout(): void {
    this._authService.logout();
    this.refreshLoginState()
  }

  refreshLoginState(): void {
    console.log('refreshing login state');
    const cookies = this._cookieService.getAll();
    this._isLoggedIn = cookies['userId'] !== null && cookies['userId'] !== undefined;
    this._isAdmin = this.isLoggedIn && cookies['admin'] == 'true';
    console.log('isLoggedIn: ', this.isLoggedIn, ' | isAdmin: ', this._isAdmin);
  }
}
