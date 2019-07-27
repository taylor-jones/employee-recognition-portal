import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { MomentDateAdapter, MAT_MOMENT_DATE_FORMATS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import * as _moment from 'moment';
import { 
  MatSnackBar, 
  MatSnackBarConfig, 
  MatSnackBarHorizontalPosition, 
  MatSnackBarVerticalPosition 
} from '@angular/material/snack-bar';

import { AwardService } from '../../services/award/award.service';
import { AwardTypeService } from '../../services/awardType/awardType.service';
import { EmployeeService } from '../../services/employee/employee.service';
import { UserService } from '../../services/user/user.service';
import { User } from '../../models/user.model';
import { AwardType } from '../../models/awardType.model';
import { Employee } from '../../models/employee.model';
import { ValidateDate } from '../../validators/date.validator';
import { ValidateTime } from '../../validators/time.validator';

const moment = _moment;
@Component({
	selector: 'erp-create-award',
	templateUrl: './createAward.component.html',
  styleUrls: [ './createAward.component.scss' ],
  providers: [
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS },
  ],
})
export class CreateAwardComponent implements OnInit {
	createAwardForm: FormGroup;
	submitted = false;
	awardTypes = [];
	employees = [];
  users = [];
  
  awardType: AwardType;
  employee: Employee;
  user: User;

  // snackbarConfig = {
  //   horizontalPosition: 'center',
  //   verticalPosition: 'bottom',
  //   duration: 3000,
  //   actionButtonLabel: 'Okay',
  //   extraClasses: null,
  // }; 

	constructor(
		private _formBuilder: FormBuilder,
		private _awardService: AwardService,
		private _awardTypeService: AwardTypeService,
		private _employeeService: EmployeeService,
    private _userService: UserService,
    private _snackBar: MatSnackBar
	) {}


	ngOnInit() {
		this.createAwardForm = this._formBuilder.group({
      id: new FormControl({ value: null, disabled: true }),
      user: new FormControl(null, { validators: Validators.required }),
      awardType: new FormControl(null, { validators: Validators.required }),
      employee: new FormControl(null, { validators: Validators.required }),
			description: new FormControl(null),
			awardedDate: new FormControl(null, { validators: ValidateDate }),
			awardedTime: new FormControl(null, { validators: ValidateTime }),
    });
    
		this._userService.getAllUsers().subscribe((users) => {
      users.sort((a, b): number =>  a.username.toLowerCase() < b.username.toLowerCase() ? -1 : 1);
			this.users = users;
    });

		this._awardTypeService.getAllAwardTypes().subscribe((awardTypes) => {
      awardTypes.sort((a, b): number =>  a.name.toLowerCase() < b.name.toLowerCase() ? -1 : 1);
			this.awardTypes = awardTypes;
    });
    
		this._employeeService.getAllEmployees().subscribe((employees) => {
      employees.sort((a, b): number =>  a.firstName.toLowerCase() < b.firstName.toLowerCase() ? -1 : 1);
      this.employees = employees;
		});
	}


	// helper for reference to form fields
	get f() {
		return this.createAwardForm.controls;
  }
  

  showSnackbarSuccess(message) {
    this._snackBar.open(message, 'Okay', {
      duration: 3000,
      panelClass: [ 'snackbar-success' ],
    });
  }

  showSnackbarError(message) {
    this._snackBar.open(message, 'Okay', {
      duration: 3000,
      panelClass: [ 'snackbar-error' ],
    });
  }

  createNewAward(context) {
    this._awardService.createAward(context).subscribe(response => {
      if (response && response.id) {
        this.createAwardForm.get('id').setValue(response.id);
        this.showSnackbarSuccess('Success! The award has been created.');
      } else {
        this.showSnackbarError('Something has gone wrong. Award creation failed.');
      }
    });
  }


  updateExistingAward(context) {
    this._awardService.updateAward(context).subscribe(response => {
      if (response && response.id) {
        this.createAwardForm.get('id').setValue(response.id);
        this.showSnackbarSuccess('Success! The award has been updated.');
      } else {
        this.showSnackbarError('Something has gone wrong. Award updating failed');
      }
    });
  }

  
  onSubmit() {
		this.submitted = true;

		if (this.createAwardForm.invalid) {
			console.log('FORM IS INVALID!');
			return;
    }

    const formDate = this.f.awardedDate.value;
    const formTime = this.f.awardedTime.value;
    
    // build the post body
    const context = {
      id: this.f.id.value,
      awardType: this.awardType,
      employee: this.employee,
      userAccount: this.user,
      description: this.f.description.value,
      awardedDate: formDate ? moment(formDate).format('YYYY-MM-DD') : null,
      awardedTime: formTime ? moment(formTime, ["h:mm A"]).format('HH:mm') : null,
    };
      
    // console.log(context);

    if (this.createAwardForm.get('id').value) {
      this.updateExistingAward(context);
    } else {
      this.createNewAward(context);
    }
	}


  onAwardTimeChange() {
    console.log('award time change')
  }

  // update the selected user object when a change is made on the form.
  onUserSelectChange() {
    const curr = this.f.user.value;
    this.user = this.users.filter(obj => obj.id === curr)[0];
  }
  
  // update the selected award type object when a change is made on the form.
  onAwardTypeSelectChange() {
    const curr = this.f.awardType.value;
    this.awardType = this.awardTypes.filter(obj => obj.id === curr)[0];
  }
  
  // update the selected employee object when a change is made on the form.
  onEmployeeSelectChange() {
    const curr = this.f.employee.value;
    this.employee = this.employees.filter(obj => obj.id === curr)[0];
  }

	onReset() {
		this.submitted = false;
		this.createAwardForm.reset();
	}
}
