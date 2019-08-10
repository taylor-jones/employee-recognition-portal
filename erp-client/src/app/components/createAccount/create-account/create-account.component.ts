import { Component, OnInit } from '@angular/core';
import {User} from '../../../models/user.model';
import {NewUser} from '../../../models/new-user.model';
import {RecoveryQuestion} from '../../../models/recovery.model';

@Component({
  selector: 'erp-create-home',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.scss']
})
export class CreateAccountComponent implements OnInit {

  hasAccount = false;
  accountStarted = false;
  recoveryStarted = false;
  user: NewUser = {} as NewUser;

  constructor() { }

  ngOnInit() {
  }

  handleUsername(data: {username: string, password: string, email: string}): void {
    this.user.username = data.username;
    this.user.password = data.password;
    this.user.email = data.email;
  }

  startedAccount(accountStarted: boolean) {
    accountStarted ? this.accountStarted = true : this.accountStarted = false;
  }

  handleRecoveryQuestions(recoveryQuestions: RecoveryQuestion[]) {
    this.user.recoveryQuestions = recoveryQuestions;
  }
}
