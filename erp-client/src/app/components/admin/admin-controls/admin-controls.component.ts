import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { User } from 'src/app/models/user.model';
import { CanvasComponent } from 'src/app/components/canvas/canvas.component';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { SetRecoveryQuestionsComponent } from '../../createAccount/set-recovery-questions/set-recovery-questions.component';
import { RecoveryQuestion } from 'src/app/models/recovery.model';
import { AccountRecoveryService } from 'src/app/services/account-recovery/account-recovery.service';
import { SnackbarService } from 'src/app/services/snackbar/snackbar.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'admin-controls',
  templateUrl: './admin-controls.component.html',
  styleUrls: ['./admin-controls.component.scss']
})
export class AdminControlsComponent extends SetRecoveryQuestionsComponent implements OnInit {

  private _users: User[];
  private _selectedUser: User;
  private _newAdminCheck: boolean = false;
  newUserForm: FormGroup;
  existingUserForm: FormGroup;

  // Gain access the child canvas component values and functions
  @ViewChild(CanvasComponent, {static: false}) canvasChild: CanvasComponent;

  constructor(
    public cookieService: CookieService,
    public userService: UserService,
    public accountRecoveryService: AccountRecoveryService,
    public _snackbar: SnackbarService
  ) {
    super();
  }

  ngOnInit() {
    this.newUserForm = this.initUserFormGroup();
    this.existingUserForm = this.initUserFormGroup();   // existing user form should has to start out empty
    this.getAllUsersExceptMe();
  }

  initUserFormGroup() {
    return new FormGroup({
      username: new FormControl(null),
      password: new FormControl(null),
      email: new FormControl(null, [Validators.email]),
      isAdmin: new FormControl(false),
      isEnabled: new FormControl(true),
      questions: new FormGroup({
        answer1: new FormControl(null),
        answer2: new FormControl(null),
        answer3: new FormControl(null)
      })
    })
  }

  initFormGroupFromUser(initUser: User) {
    return new FormGroup({
      id: new FormControl(initUser.id),
      username: new FormControl(initUser.username),
      password: new FormControl(initUser.password),
      email: new FormControl(initUser.email, [Validators.email]),
      signature: new FormControl(initUser.signature),
      isAdmin: new FormControl(initUser.isAdmin),
      isEnabled: new FormControl(initUser.isEnabled)
    })
  }

  customSuccessSnackbar(message) {
    this._snackbar.showSuccess(message, 'Okay ✅', {duration: 1000, panelClass: [ 'snackbar-success' ]});
  }

  customErrorSnackbar(message) {
    this._snackbar.showError(message, 'Okay ⛔️', {duration: null, panelClass: [ 'snackbar-error' ]});
  }

  onSelect(value) {
    if (value == null) {
      this.resetForm(this.existingUserForm)
    } else {
      this._selectedUser = this._users.filter( u => u.id === value)[0];
      this.existingUserForm = this.initFormGroupFromUser(this._selectedUser); // sync the form and user
    }
  }

  adminToggle(): void {
    this._newAdminCheck = !this._newAdminCheck;
  }

  getAllUsersExceptMe(): void {
    this.userService.getAllUsers().subscribe (
      (users) => { 
        this._users = users.filter( me => me.username != this.cookieService.get('user') ) },
      (error) => { this.customErrorSnackbar('Failed to fetch users.') }
    );
  }

  // Don't send signatures for admin users
  getSignature() {
    if (! this.newUserForm.value.isAdmin) {
      return this.canvasChild.getCanvasData();
    }
    return null;
  }

  // Return the controls back to their original state after submit
  resetForm(form: FormGroup) {
    form.reset();
    Object.keys(form.controls).forEach(key => {
      form.get(key).setErrors(null);
    });
    if (form == this.newUserForm) {
      this.canvasChild.clearCanvas();
      let rq = form.get('questions');
      [rq.get('answer1'), rq.get('answer2'), rq.get('answer3')].forEach( a => {
        a.setValue(null);
        a.setErrors(null);
      });
      this.newUserForm.get('isAdmin').setValue(false);
      this.newUserForm.get('isEnabled').setValue(true);
    }
  }

  // Re-fetch users and reset the controls. This is meant for callbacks
  // after user creates/updates/deletes
  refresh(form: FormGroup) {
    this.getAllUsersExceptMe();
    this.resetForm(form);
  }
  
  collectAnswers() {
    let rq = this.newUserForm.value.questions;
    return [rq.answer1, rq.answer2, rq.answer3];
  }

  submitUserQuestions(username: string, questions: RecoveryQuestion[]) {
    this.accountRecoveryService.setRecoveryQuestions(username, questions).subscribe(
      user => {
        this.customSuccessSnackbar(`Created user: ${username}`);
        this.refresh(this.newUserForm);
      },
      error => { this.customErrorSnackbar('Failed to set user recovery questions.') }
    )
  }

  // Create a new user
  submitNewUser() {
    this.newUserForm.value.signature = this.getSignature();
    if (this.newUserForm.value.isAdmin == null)
      this.newUserForm.value.isAdmin = false
    let questions = this.mapRecoveryQuestionsToAnswers(this.questions, this.collectAnswers());
    delete this.newUserForm.value.questions;
    this.userService.addUser(this.newUserForm.value).subscribe(
      (ok) => { this.submitUserQuestions(this.newUserForm.value.username, questions) },
      (error) => { this.customErrorSnackbar(`Failed to create new user: ${this.newUserForm.value.username}`) }
    );
  }

  // Delete the selected user
  deleteSelectedUser() {
    this.userService.deleteUser(this._selectedUser).subscribe (
      (user) => {
        this.customSuccessSnackbar(`Deleted user: ${user.username}`);
        this.refresh(this.existingUserForm);
        this._selectedUser = null;
      },
      (error) => { this.customErrorSnackbar(`Failed to delete user: ${this._selectedUser.username}`) }
    )
  }

  // Update the selected user
  updateSelectedUser() {
    this.existingUserForm.value.id = this._selectedUser.id;
    this.existingUserForm.value.signature = null;
    this.existingUserForm.value.isEnabled = true;
    this.userService.updateUser(this.existingUserForm.value).subscribe (
      (user) => { 
        this.customSuccessSnackbar(`Updated user: ${user.username}`);
        this.getAllUsersExceptMe();
      },
      (error) => { this.customErrorSnackbar(`Failed to update user: ${this._selectedUser.username}`) }
    )
  }

}
