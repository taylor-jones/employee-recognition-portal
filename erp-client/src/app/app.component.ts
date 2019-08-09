import {Component} from '@angular/core';
import {AuthenticationService} from './services/authentication/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'erp-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'employee-recognition-portal';

  constructor (
    private authService: AuthenticationService,
    private router: Router
  ) {}

  handleLogout(): void {
    this.authService.logout();
  }
}
