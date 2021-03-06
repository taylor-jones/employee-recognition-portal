import {Component, OnInit} from '@angular/core';
import {User} from '../../../models/user.model';
import {NewUser} from '../../../models/new-user.model';
import {RecoveryQuestion} from '../../../models/recovery.model';
import {UserService} from '../../../services/user/user.service';
import {Router} from '@angular/router';
import {AccountRecoveryService} from '../../../services/account-recovery/account-recovery.service';
import {AuthenticationService} from '../../../services/authentication/authentication.service';
import {AppComponent} from '../../../app.component';

@Component({
  selector: 'erp-create-home',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.scss']
})
export class CreateAccountComponent implements OnInit {

  accountStarted = false;
  user: NewUser = {} as NewUser;
  userToCreate: User;
  errorMessage: string;

  constructor(private userService: UserService,
              private accountRecoveryService: AccountRecoveryService,
              private authService: AuthenticationService,
              private app: AppComponent,
              private router: Router) {
  }

  ngOnInit() {
  }

  handleUsername(data: { username: string, password: string, email: string }): void {
    this.user.username = data.username;
    this.user.password = data.password;
    this.user.email = data.email;
  }

  handleRecoveryQuestions(recoveryQuestions: RecoveryQuestion[]) {
    this.user.recoveryQuestions = recoveryQuestions;
  }

  handleAccountSubmit(signature: string) {
    this.user.signature = signature;
    this.createUser();
  }

  createUser() {
    this.userToCreate = new User();
    this.userToCreate.username = this.user.username;
    this.userToCreate.password = this.user.password;
    this.userToCreate.email = this.user.email;
    this.userToCreate.signature = this.user.signature;
    this.userToCreate.isAdmin = false;

    this.userService.addUserCreatedUser(this.userToCreate).subscribe((createdUser) => {
        this.accountRecoveryService.setRecoveryQuestions(createdUser.username, this.user.recoveryQuestions).subscribe(() => {
            this.authService.authenticate({ username: createdUser.username, password: createdUser.password }).subscribe(() => {
                this.app.refreshLoginState();
                this.router.navigate(['/']);
              },
              (error) => {
                console.log('error authenticating user', error);
                this.errorMessage = error;
              });
          },
          (error) => {
            console.log('error adding account recovery', error);
            this.errorMessage = error;
          }
        );
      }, (error) => {
        console.log('error creating user', error);
        this.errorMessage = error;
      }
    );
  }

}
