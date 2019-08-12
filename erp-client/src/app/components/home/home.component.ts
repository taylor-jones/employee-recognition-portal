import { Component, OnInit } from '@angular/core';
import { AppComponent } from 'src/app/app.component';
import { Router } from '@angular/router';

@Component({
  selector: 'erp-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  constructor(
    private _app: AppComponent,
    private _router: Router,
  ) {}
  
  ngOnInit() {
    // if not logged in, redirect to login page.
    if (!this._app.isLoggedIn) {
      this._router.navigate(['/login']);
    // if admin, redirect to admin page.
    } else if (this._app.isAdmin) {
      this._router.navigate(['/admin']);
    // if logged in but not admin, redirect to account page.
    } else {
      this._router.navigate(['/awards']);
    }
  }
}
