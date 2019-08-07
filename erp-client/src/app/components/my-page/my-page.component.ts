import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { AdminControlsComponent } from '../admin/admin-controls/admin-controls.component';
import { UserService } from 'src/app/services/user/user.service';
import { User } from 'src/app/models/user.model';
import { SnackbarService } from 'src/app/services/snackbar/snackbar.service';
import { FormGroup } from '@angular/forms';
import { MatInput } from '@angular/material';

@Component({
  selector: 'erp-my-page',
  templateUrl: './my-page.component.html',
  styleUrls: ['./my-page.component.scss']
})
export class MyPageComponent extends AdminControlsComponent implements OnInit {
  private currentUser: string;
  private me: User;
  private readOnly: boolean;
  private passwordType: string;
  accountForm: FormGroup;

  constructor (
    private cookieService: CookieService,
    private snackbarService: SnackbarService,
    public userService: UserService
  ) {
    super(userService);
    this.currentUser = cookieService.get('user');
    this.readOnly = true;
    this.passwordType = 'password';
    this.accountForm = this.initUserFormGroup();
  }

  ngOnInit() {
    this.getMyUser();
  }

  toggleReadOnly() {
    this.readOnly = !this.readOnly;
    this.setPasswordType();
  }

  setPasswordType() {
    if (this.readOnly == true) {
      this.passwordType = 'password'
    } else {
      this.passwordType = 'text'
    }
  }

  resetForm() {
    this.readOnly = true;
    this.setPasswordType();
    this.accountForm = this.initFormGroupFromUser(this.me);
  }

  getMyUser() {
    this.userService.getUserByUsername(this.currentUser).subscribe (
      user => { 
        this.me = user;
        this.accountForm = this.initFormGroupFromUser(this.me);
      },
      error => { this.onError(`Unable to get current user: ${error})`)}
    )
  }

  updateMyUser() {
    this.userService.updateUser(this.accountForm.value).subscribe (
      (user) => { 
        this.me = user;
        this.toggleReadOnly();
      },
      (error) => { this.onError(`Failed to updated user: ${error}`) }
    )
  }

  onError(message: string) {
    this.snackbarService.showError(message, 'Dang it', {panelClass: [ 'snackbar-error' ]})
  }

}
