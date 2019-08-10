import { Component, OnInit } from '@angular/core';
import { AppComponent } from 'src/app/app.component';
import { Router } from '@angular/router';

@Component({
  selector: 'erp-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.scss']
})
export class AdminHomeComponent implements OnInit {

  constructor(
    private _app: AppComponent,
    private _router: Router,
  ) {}

  ngOnInit() {
    if (!this._app.isAdmin) {
      this._router.navigate(['/']);
    }
  }
}
