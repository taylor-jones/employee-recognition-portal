import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { User } from 'src/app/models/user.model';
import { CanvasComponent } from 'src/app/components/canvas/canvas.component';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';

@Component({
  selector: 'admin-controls',
  templateUrl: './admin-controls.component.html',
  styleUrls: ['./admin-controls.component.scss']
})
export class AdminControlsComponent implements OnInit {

  private _users: User[];
  private _selectedUser: User;
  private _newAdminCheck: boolean = false;
  private _existingAdminCheck: boolean = false;
  newUserForm: FormGroup;
  existingUserForm: FormGroup;
  private _formBuilder: FormBuilder;

  // Gain access the child canvas component values and functions
  @ViewChild(CanvasComponent, {static: false}) canvasChild: CanvasComponent;

  constructor(public userService: UserService) { }

  ngOnInit() {
    this.newUserForm = this.initUserFormGroup();
    this.existingUserForm = this.initUserFormGroup();   // existing user form should has to start out empty
    this.getAllUsers();
  }

  initUserFormGroup() {
    return new FormGroup({
      username: new FormControl(null),
      password: new FormControl(null),
      email: new FormControl(null),
      isAdmin: new FormControl(false)
    })
  }

  initFormGroupFromUser(initUser: User) {
    return new FormGroup({
      id: new FormControl(initUser.id),
      username: new FormControl(initUser.username),
      password: new FormControl(initUser.password),
      email: new FormControl(initUser.email),
      signature: new FormControl(initUser.signature),
      isAdmin: new FormControl(initUser.isAdmin),
      isEnabled: new FormControl(initUser.isEnabled)
    })
  }

  onSelect(value) {
    this._selectedUser = this._users.filter( u => u.id === value)[0];
    this.existingUserForm = this.initFormGroupFromUser(this._selectedUser); // sync the form and user
  }

  // Handles both checkbox toggles
  adminToggle(): void {
    this._newAdminCheck = !this._newAdminCheck;
  }

  getAllUsers(): void {
    this.userService.getAllUsers().subscribe (
      (users) => { this._users = users },
      (error) => { console.log(error) }
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
  }

  // Re-fetch users and reset the controls. This is meant for callbacks
  // after user creates/updates/deletes
  refresh(form: FormGroup) {
    this.getAllUsers();
    this.resetForm(form);
    this.canvasChild.clearCanvas();
  }

  // Create a new user
  submitNewUser() {
    this.newUserForm.value.isEnabled = true;
    this.newUserForm.value.signature = this.getSignature();
    this.userService.addUser(this.newUserForm.value).subscribe(
      (ok) => { this.refresh(this.newUserForm) },
      (error) => { console.error(error) }
    );
  }

  // Delete the selected user
  deleteSelectedUser() {
    this.userService.deleteUser(this._selectedUser).subscribe (
      (user) => { 
        this.refresh(this.existingUserForm);
        this._selectedUser = null;
      },
      (error) => { console.error(error) }
    )
  }

  // Update the selected user
  updateSelectedUser() {
    this.existingUserForm.value.id = this._selectedUser.id;
    this.existingUserForm.value.signature = null;
    this.existingUserForm.value.isEnabled = true;
    this.userService.updateUser(this.existingUserForm.value).subscribe (
      (user) => { this.getAllUsers() },
      (error) => {console.error(error)}
    )
  }



}
