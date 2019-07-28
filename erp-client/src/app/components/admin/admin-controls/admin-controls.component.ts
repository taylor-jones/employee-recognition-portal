import { Component, OnInit } from '@angular/core';
import { UserAccountService } from 'src/app/services/user-account/user-account.service';
import { User } from 'src/app/models/user.model';
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

  constructor(private userAccountService: UserAccountService) { }

  ngOnInit() {
    this.newUserForm = new FormGroup({
      userName: new FormControl(null),
      password: new FormControl(null),
      email: new FormControl(null),
      isAdmin: new FormControl(null)
    });
    this.getAllUsers();
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

  onSubmit() {
    console.warn(this.newUserForm.value);
  }

  getAllUsers(): void {
    this.userAccountService.getAllUsers().subscribe (
      (users) => {
        this._users = users;
      },
      (error) => {
        console.log(error);
      }
    );
  }

}
