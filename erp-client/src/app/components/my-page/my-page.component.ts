import {Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import {CookieService} from 'ngx-cookie-service';
import {AdminControlsComponent} from '../admin/admin-controls/admin-controls.component';
import {UserService} from 'src/app/services/user/user.service';
import {User, UserWithTimeStamp} from 'src/app/models/user.model';
import {SnackbarService} from 'src/app/services/snackbar/snackbar.service';
import {FormGroup} from '@angular/forms';
import {MatInput} from '@angular/material';
import { AccountRecoveryService } from 'src/app/services/account-recovery/account-recovery.service';
import { CanvasService } from 'src/app/services/canvas/canvas.service';

@Component({
  selector: 'erp-my-page',
  templateUrl: './my-page.component.html',
  styleUrls: ['./my-page.component.scss']
})
export class MyPageComponent extends AdminControlsComponent implements OnInit {
  private currentUser: string;
  private meWithTimestamp: UserWithTimeStamp;
  private me: User;
  private signatureData = '';
  private readOnly: boolean;
  private passwordType: string;
  accountForm: FormGroup;

  constructor(
    private cookieService: CookieService,
    private canvasService: CanvasService,
    private sanitizer: DomSanitizer, 
    public _snackbar: SnackbarService,
    public userService: UserService,
    public accountRecoveryService: AccountRecoveryService
  ) {
    super(userService, accountRecoveryService, _snackbar);
    this.currentUser = cookieService.get('user');
    this.readOnly = true;
    this.passwordType = 'password';
    this.accountForm = this.initUserFormGroup();
  }

  mapUserWithTimestamp(response) {
    return {
      id: response[0],
      email: response[1],
      username: response[2],
      password: response[3],
      signature: response[4],
      isAdmin: response[5],
      isEnabled: response[6],
      timestamp: response[7]
    }
  }

  userFromUserWithTimeStamp(uwt: UserWithTimeStamp) {
    return {id: uwt.id, email: uwt.email, username: uwt.username, password: uwt.password, signature: uwt.signature, isAdmin: uwt.isAdmin, isEnabled: uwt.isEnabled};
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
      this.passwordType = 'password';
    } else {
      this.passwordType = 'text';
    }
  }

  resetForm() {
    this.readOnly = true;
    this.setPasswordType();
    this.accountForm = this.initFormGroupFromUser(this.me);
  }

  getMySignature() {
    this.canvasService.getSignature().subscribe(
      ok => {
        this.signatureData = 'data:image/jpeg;base64,' + ok.toString().split('"')[1].split('"')[0];
      },
      error => {this.customErrorSnackbar(`Failed to get signature for current user.)`)}
    );
  }

  getMyUser() {
    this.userService.getUserDetailsByUsername(this.currentUser).subscribe(
      user => {
        this.meWithTimestamp = this.mapUserWithTimestamp(user);
        this.me = this.userFromUserWithTimeStamp(this.meWithTimestamp);
        this.accountForm = this.initFormGroupFromUser(this.me);
        if (this.meWithTimestamp.isAdmin == false) {
          this.getMySignature(); 
        }
      },
      error => {
        this.customErrorSnackbar(`Unable to get current user: ${error})`);
      }
    );
  }

  updateMyUser() {
    this.userService.updateUser(this.accountForm.value).subscribe(
      (user) => {
        this.customSuccessSnackbar(`Updated user, ${user.username}!`);
        this.me = user;
        let timestamp = this.meWithTimestamp.timestamp;
        this.meWithTimestamp = this.me;
        this.meWithTimestamp.timestamp = timestamp
        this.cookieService.set('user', this.me.username);
        this.toggleReadOnly();
      },
      (error) => {
        this.customErrorSnackbar(`Failed to updated user: ${error}`);
      }
    );
  }

}
