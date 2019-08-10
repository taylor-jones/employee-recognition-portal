import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../../../services/user/user.service';
import {User} from '../../../models/user.model';
import {NewUser} from '../../../models/new-user.model';

@Component({
  selector: 'erp-username-selection',
  templateUrl: './username-selection.component.html',
  styleUrls: ['./username-selection.component.scss']
})
export class UsernameSelectionComponent implements OnInit {

  @Output() event: EventEmitter<{ username: string, password: string, email: string }> = new EventEmitter();

  username: string;
  password: string;
  rePassword: string;
  email: string;

  validUsername = false;
  errorMessage: string;

  constructor(private router: Router,
              private userService: UserService) {
  }

  ngOnInit() {
  }

  checkUsername(): void {
    this.userService.checkUsername(this.username).subscribe((isValid: boolean) => {
        if (isValid) {
          this.sendEvent();
        }  else {
          this.errorMessage = 'Username already taken!';
        }
      },
      (error) => {
        this.errorMessage = error;
      }
    );
  }

  isValid(): boolean {
    return this.email && this.validUsername && (this.password != null) && (this.password.length > 0) && (this.password === this.rePassword);
  }

  sendEvent() {
    this.event.emit({
      username: this.username,
      password: this.password,
      email: this.email
    });
  }
}
