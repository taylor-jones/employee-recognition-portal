import {Component, OnInit} from '@angular/core';
import {User} from '../../../models/user.model';
import {NewUser} from '../../../models/new-user.model';
import {RecoveryQuestion} from '../../../models/recovery.model';
import {UserService} from '../../../services/user/user.service';
import {Router} from '@angular/router';

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

    // this.userService.addUser(this.newUserForm.value).subscribe(
    //   (ok) => { this.refresh(this.newUserForm) },
    //   (error) => { console.error(error) }
    // );

    this.userService.addUserCreatedUser(this.userToCreate).subscribe(() => {
        this.router.navigate(['login']);
      },
      (error) => {
        this.errorMessage = error;
      }
    );
  }

}
