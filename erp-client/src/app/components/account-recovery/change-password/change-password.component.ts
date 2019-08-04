import {Component, Input, OnInit} from '@angular/core';
import {AccountRecoveryService} from '../../../services/account-recovery/account-recovery.service';
import {Router} from '@angular/router';
import {PasswordReset} from '../../../models/recovery.model';

@Component({
  selector: 'erp-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  @Input() username: string;
  newPassword: string;
  reNewPassword: string;
  errorMessage: string;
  updateUser: PasswordReset;

  constructor(private accountRecoveryService: AccountRecoveryService,
              private router: Router) {
  }

  ngOnInit() {
  }

  handleNewPassword(user: string): void {
    this.accountRecoveryService.setNewPassword({
      username: this.username,
      newPassword: this.newPassword,
      reNewPassword: this.reNewPassword
    }).subscribe(
      () => {
        this.router.navigate(['/login]']);
      },
      (error) => {
        this.errorMessage = error;
      }
    );
  }
}
