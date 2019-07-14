import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../services/authentication/authentication.service';

@Component({
  selector: 'erp-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  login = 'Hi Tucker';

  username: string;
  password: string;

  constructor(private authService: AuthenticationService) { }

  ngOnInit() {
  }

  handleLogin(): void {
    this.authService.authenticate(this.username, this.password);
  }
}
