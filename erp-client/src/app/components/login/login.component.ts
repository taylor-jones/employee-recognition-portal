import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'erp-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  login = 'Hi Tucker';

  username: string;
  password: string;
  errorMessage: string;

  constructor(private authService: AuthenticationService,
              private router: Router) { }
  // constructor(private loginService: LoginService) {}

  ngOnInit() {
  }

  handleLogin(): void {
    this.authService.authenticate({username: this.username, password: this.password}).subscribe(() => {
      this.router.navigate(['/']);
    },
      (error) => {
        this.errorMessage = error;
      });
  //   this.loginService.userLogin(this.username, this.password).subscribe(
  //     () => {
  //       console.log('next');
  //     },
  //     (error) => {
  //       this.errorMessage = 'Login Failed';
  //     },
  //     () => {
  //       console.log('complete');
  //     }
  //   );
  }
}
