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
  private _formBuilder: FormBuilder;

  // Gain access the child canvas component values and functions
  @ViewChild(CanvasComponent, {static: false}) canvasChild: CanvasComponent;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.newUserForm = new FormGroup({
      username: new FormControl(null),
      password: new FormControl(null),
      email: new FormControl(null),
      isAdmin: new FormControl(false)
    });
    this.getAllUsers();
  }

  onSelect(value) {
    this._selectedUser = this._users.filter( u => u.id === value)[0];
  }

  resetForm() {
    this.newUserForm.reset();
    Object.keys(this.newUserForm.controls).forEach(key => {
      this.newUserForm.get(key).setErrors(null) ;
    });
  }

  // Handles both checkbox toggles
  adminToggle(current, isNew): void {
    if (isNew) {
      this._newAdminCheck = !current;
    } else {
      this._existingAdminCheck = !current;
    }
  }

  onSubmit() {
    this.newUserForm.value.isEnabled = true;
    this.newUserForm.value.signature = this.canvasChild.getCanvasData();
    this.userService.addUser(this.newUserForm.value).subscribe(
      (ok) => {
        this.getAllUsers();
        this.resetForm();
      },
      (error) => {console.log(error)}
    );
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

}
