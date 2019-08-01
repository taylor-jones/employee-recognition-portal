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

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.initUserForm();
    this.initExistingUserForm();
    this.getAllUsers();
  }

  initUserFormGroup() {
    return new FormGroup({
      username: new FormControl(null),
      password: new FormControl(null),
      email: new FormControl(null),
      isAdmin: new FormControl(false)
    });
  }

  initUserForm(): void {
    this.newUserForm = this.initUserFormGroup();
  }

  initExistingUserForm(): void {
    this.existingUserForm = this.initUserFormGroup();
  }

  onSelect(value) {
    this._selectedUser = this._users.filter( u => u.id === value)[0];
  }

  // Handles both checkbox toggles
  adminToggle(current, isNew): void {
    if (isNew) {
      this._newAdminCheck = !current;
    } else {
      this._existingAdminCheck = !current;
    }
  }

  getAllUsers(): void {
    this.userService.getAllUsers().subscribe (
      (users) => {
        this._users = users;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  resetForm(form: FormGroup) {
    form.reset();
    Object.keys(form.controls).forEach(key => {
      form.get(key).setErrors(null);
    });
  }

  refresh(form: FormGroup) {
    this.getAllUsers();
    this.resetForm(form);
  }

  submitNewUser() {
    this.newUserForm.value.isEnabled = true;
    this.newUserForm.value.signature = this.canvasChild.getCanvasData();
    this.userService.addUser(this.newUserForm.value).subscribe(
      (ok) => { this.refresh(this.newUserForm) },
      (error) => { console.log(error) }     // do something more useful with this
    );
  }

  deleteSelectedUser() {
    this.userService.deleteUser(this._selectedUser).subscribe (
      (ok) => { 
        this.refresh(this.existingUserForm);
        this._selectedUser = null;
      },
      (error) => { console.log(error) }     // do something more useful with this
    )
  }



}
