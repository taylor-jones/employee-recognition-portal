import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import { UserService } from '../../../services/user/user.service';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { ValidateEmail } from 'src/app/validators/email.validator';

@Component({
  selector: 'erp-username-selection',
  templateUrl: './username-selection.component.html',
  styleUrls: ['./username-selection.component.scss']
})
export class UsernameSelectionComponent implements OnInit {
  @Output() event: EventEmitter<{ username: string, password: string, email: string }> = new EventEmitter();

  submitted = false;
  selectionForm: FormGroup;
  errorMessage: string;

  username: string;
  password: string;
  rePassword: string;
  email: string;

  existingUsers = [];
  isAvailableEmail: boolean;
  isAvailableUsername: boolean;


  constructor(
    private router: Router,
    private userService: UserService,
    private formBuilder: FormBuilder,
  ) {}

  ngOnInit() {
    // setup the form
    this.selectionForm = this.formBuilder.group({
      username: new FormControl(null, { validators: Validators.required }),
      password: new FormControl(null, { validators: Validators.required }),
      rePassword: new FormControl(null, { validators: Validators.required }),
      email: new FormControl(null, Validators.compose([
        Validators.required,
        ValidateEmail,
      ])),
    });

    // grab the existing usernames and emails
    this.userService.getAvailability().subscribe((users) => {
      this.existingUsers = users.map((user) => {
        return {
          username: user[0],
          email: user[1],
        }
      });
    });
  }


	/**
   * helper for reference to form fields
   */
	get f() {
		return this.selectionForm.controls;
  }


  /**
   * Checks if the current form username is available.
   */
  checkUsernameAvailable() {
    const value = this.f.username.value;
    const isTaken = this.existingUsers.some((user) => user.username === value);

    if (isTaken) {
      this.f.username.setErrors({ 
        ...this.f.username.errors,
        unavailable: isTaken,
      });
    } else {
      this.f.username.markAsTouched();
    }
  }


  /**
   * Checks if the current form email is available.
   */
  checkEmailAvailable() {
    const value = this.f.email.value;
    const isTaken = this.existingUsers.some((user) => user.email === value);

    if (isTaken) {
      this.f.email.setErrors({ 
        ...this.f.email.errors,
        unavailable: isTaken
      });
    } else {
      this.f.email.markAsTouched();
    }
  }


  goToNextStep(): void {
    this.submitted = true;

    // if the form isn't valid, stop processing
    if (this.selectionForm.invalid) {
      return;
    }

    this.sendEvent();
  }


  /**
   * Keeps track of the initial password whenever a change occurs
   * so that the re-password can be checked against it
   */
  updatePassword() {
    this.password = this.f['password'].value || '';
  }


  sendEvent() {
    this.event.emit({
      username: this.f.username.value,
      password: this.f.password.value,
      email: this.f.email.value,
    });
  }
}
