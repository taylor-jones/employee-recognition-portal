import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'erp-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  // loginForm: FormGroup;
  username: string;
  password: string;
  errorMessage: string;
  showSpinner = false;

  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private cookieService: CookieService
  ) {}

  // constructor(private loginService: LoginService) {}

  ngOnInit() {
    // this.loginForm = this.formBuilder.group({
    //   username: ['', Validators.required],
    //   password: ['', Validators.required]
    // });
  }

  handleLogin(): void {
    this.showSpinner = true;
    this.authService.authenticate({username: this.username, password: this.password}).subscribe((user) => {
        // should add a way to redirect admin to dashboard and user to home page
      if (this.cookieService.get('admin') == 'true') {
        this.router.navigate(['/admin']);
      } else {
        this.router.navigate(['/awards']);
      }
        this.showSpinner = false;
      },
      (error) => {
        this.errorMessage = error;
        this.showSpinner = false;
      },
      () => {
        this.showSpinner = false;
      });
  }
}
