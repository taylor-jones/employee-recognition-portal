import {Component, Input, OnInit} from '@angular/core';
import {AccountRecoveryService} from '../../../services/account-recovery/account-recovery.service';
import {Router} from '@angular/router';
import {PasswordReset} from '../../../models/recovery.model';
import {ErrorStateMatcher, ShowOnDirtyErrorStateMatcher} from '@angular/material';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';

// /** Error when invalid control is dirty, touched, or submitted. */
// export class MyErrorStateMatcher implements ErrorStateMatcher {
//   isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
//     const isSubmitted = form && form.submitted;
//     return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
//   }
// }

@Component({
  selector: 'erp-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss'],
  // providers: [
  //   {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher}
  // ]
})
export class ChangePasswordComponent implements OnInit {

  // newPasswordFormControl = new FormControl('', [
  //   Validators.required,
  // ]);
  //
  // reNewPasswordFormControl = new FormControl('', [
  //   Validators.required,
  // ]);
  //
  // matcher = new MyErrorStateMatcher();

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

  handleNewPassword(username: string): void {
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
