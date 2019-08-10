import { Component } from '@angular/core';
import { AuthenticationService } from '../../services/authentication/authentication.service';
import { Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';

@Component({
  selector: 'erp-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username: string;
  password: string;
  errorMessage: string;
  showSpinner = false;

  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private app: AppComponent
  ) {}


  handleLogin(): void {
    this.showSpinner = true;
    this.authService.authenticate({
      username: this.username, 
      password: this.password
    }).subscribe(() => {
      this.app.refreshLoginState();
      this.router.navigate(['/']);
    },
    (error) => {
      this.errorMessage = error;
    });

    this.showSpinner = false;
  }
}
