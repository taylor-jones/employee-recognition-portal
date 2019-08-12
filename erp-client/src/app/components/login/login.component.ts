import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../services/authentication/authentication.service';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';
import { SnackbarService } from 'src/app/services/snackbar/snackbar.service';

@Component({
  selector: 'erp-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  submitted = false;
  showSpinner = false;
  loginForm: FormGroup;

  errorStatusFeedback = {
    401: 'Either the username or password is incorrect.',
  }

  constructor(
    private _authService: AuthenticationService,
    private _router: Router,
    private _app: AppComponent,
    private _formBuilder: FormBuilder,
    private _snackBar: SnackbarService,
  ) {}


  ngOnInit(): void {
    this.loginForm = this._formBuilder.group({
      username: new FormControl(null, { validators: Validators.required }),
      password: new FormControl(null, { validators: Validators.required }),
    })
  }

  
	/**
   * helper for reference to form fields
   */
	get f() {
		return this.loginForm.controls;
  }


  getResponseFeedback(responseCode: number, showOnSuccess: boolean = false) {
    if (responseCode >= 200 && responseCode < 300 && showOnSuccess === false) {
      return null;
    }

    if (this.errorStatusFeedback.hasOwnProperty(responseCode)) {
      return this.errorStatusFeedback[responseCode];
    }

    if (responseCode >= 300 && responseCode < 400) {
      return 'Hmmm...it didn\'t work. That\'s weird';
    } else if (responseCode >= 400 && responseCode < 500) {
      return 'Something wasn\'t right on your end';
    } else {
      return 'This is our fault. Something went wrong on the server.';
    }
  }


  handleLogin(): void {
    this.submitted = true;
    this.showSpinner = true;
    
    // if there are input errors, don't try to login.
    if (this.loginForm.invalid) {
      this.showSpinner = false;
      return;
    }
    
    this._authService.authenticate({
      username: this.f.username.value,
      password: this.f.password.value,
    }).subscribe(() => {
      this._app.refreshLoginState();
      this._router.navigate(['/']);
    },
    (error) => {
      const status = error.status;
      const message = this.getResponseFeedback(status);

      if (message !== null) {
        this._snackBar.showError(message);
      }
    });

    this.showSpinner = false;
  }
}
