import { Component, OnInit, ViewChild } from '@angular/core';
import { AwardService } from 'src/app/services/award/award.service';
import { Award } from 'src/app/models/award.model';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { SnackbarService } from 'src/app/services/snackbar/snackbar.service';

import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { MomentDateAdapter, MAT_MOMENT_DATE_FORMATS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
// import { MatSnackBar} from '@angular/material/snack-bar';
import * as _moment from 'moment';

import { AwardTypeService } from 'src/app/services/awardType/awardType.service';
import { EmployeeService } from 'src/app/services/employee/employee.service';
import { UserService } from 'src/app/services/user/user.service';
import { User } from 'src/app/models/user.model';
import { AwardType } from 'src/app/models/awardType.model';
import { Employee } from 'src/app/models/employee.model';
import { ValidateDate } from 'src/app/validators/date.validator';
import { ValidateTime } from 'src/app/validators/time.validator';

const moment = _moment;

@Component({
	selector: 'erp-award',
	templateUrl: './award.component.html',
  styleUrls: [ './award.component.scss' ],
  providers: [
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS },
  ],
})
export class AwardComponent implements OnInit {
	@ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;

  //
  // tabs
  //
  selectedTab: number = 0;
  isAwardListEdit = false;


  // 
  // award form
  // 

  formContext = {
    titleText: null,
    subtitleText: null,
    submitButtonText: null,
  };

	createAwardForm: FormGroup;
  submitted = false;

  // used to populate html select elements
	awardTypes = [];
	employees = [];
  users = [];

  // objects to use for HTTP requests involving relational data
  awardType: AwardType;
  employee: Employee;
  user: User;


  // 
  // award table
  // 
	awards: any = null;
	errorMessage: string;
	displayedColumns: string[] = [
    'id',
    'awardType',
    'employee',
    'awardedDate',
    'awardedTime',
    'edit',
    'delete'
  ];

	pageSizeOptions: number[];
	pageSize: number;
	length: number;
  dataSource = new MatTableDataSource([]);
  

  //
  // methods
  //

	constructor(
    private _authService: AuthenticationService,
    private _awardService: AwardService,
		private _awardTypeService: AwardTypeService,
		private _employeeService: EmployeeService,
    private _formBuilder: FormBuilder,
    private _snackBar: SnackbarService,
    private _userService: UserService,
  ) {}

  ngOnInit() {
    this.setAwardFormContext(false);

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

    this.getAllAwards();
  }
  
  
  ngAfterViewInit (){
    this.dataSource.sort = this.sort;
  }


	/**
   * helper for reference to form fields
   */
	get f() {
		return this.createAwardForm.controls;
  }


	getAllAwards(): void {
		this._awardService.getAllAwards().subscribe(awards => {
      this.awards = awards;
      this.length = awards.length;
      this.dataSource = new MatTableDataSource(this.awards);

      this.dataSource.sortingDataAccessor = (item, property) => {
        switch(property) {
          case 'awardType': return item.awardType.name.toLocaleLowerCase();
          case 'employee': return item.employee.firstName.toLocaleLowerCase();
          default: return item[property];
        }
      };
      
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;

    }, error => {
      this.errorMessage = 'Failed to load awards';
    });
  }
  

  // TODO: Make this work w/ employee names and award types
	public filter(value: string) {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  

  awardListEdit(id) {
    const award = this.awards.filter(a => a.id === id);
    if (award.length) {
      this.loadAward(award[0]);
    }
  }


  awardListDelete(id) {
    this._awardService.deleteAward(id);
    console.log('delete award', id);
  }

  
  /**
   * Loads the award associated with a specified award id into the form
   * @param {Award} award - the award object to load
   */
  loadAward(award: Award) {
    if (award.hasOwnProperty('id')) {
      this.createAwardForm.get('id').setValue(award.id);
      this.createAwardForm.get('user').setValue(award.userAccount.id);
      this.createAwardForm.get('employee').setValue(award.employee.id);
      this.createAwardForm.get('awardType').setValue(award.awardType.id);
      this.createAwardForm.get('description').setValue(award.description);
      this.createAwardForm.get('awardedDate').setValue(award.awardedDate);
      this.createAwardForm.get('awardedTime').setValue(award.awardedTime);

      // set the form context to 'Update' and move to the form.
      this.setAwardFormContext(true);
      this.isAwardListEdit = true;
      this.selectedTab = 0;
    }
  }


  /**
   * Shows a snackbar with a success message
   * @param {string} message - the message to display on the smackbar
   */
  showSnackbarSuccess(message, action = 'Okay') {
    this._snackBar.showSuccess(message, action, {
      duration: 3000,
      panelClass: [ 'snackbar-success' ],
    });
  }


  /**
   * Shows a snackbar with an error message
   * @param {string} message - the message to display on the smackbar
   */
  showSnackbarError(message, action = 'Okay') {
    this._snackBar.showError(message, action, {
      duration: 3000,
      panelClass: [ 'snackbar-error' ],
    });
  }



  /**
   * Triggers an HTTP request to create a new award record.
   * @param {object} context - the body of the HTTP request (the award data)
   */
  createNewAward(context) {
    this._awardService.createAward(context).subscribe(response => {
      if (response && response.id) {
        this.createAwardForm.get('id').setValue(response.id);
        this.showSnackbarSuccess('Success! The award has been created.');
        this.setAwardFormContext(true);
      } else {
        this.showSnackbarError('Something has gone wrong. Award creation failed.');
      }
    });
  }


  /**
   * Triggers an HTTP request to update an existing award record.
   * @param {object} context - the body of the HTTP request (the award data)
   */
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


  /**
   * Form submission event handler.
   * Triggered when the "Create Award" button is pressed.
   */
  onSubmit() {
		this.submitted = true;

    // if the form isn't valid, stop processing
		if (this.createAwardForm.invalid) {
			return;
    }

    // grad the current award date & time values
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
      
    // if the award record doesn't yet have an id, then it's a new award.
    // otherwise, it's an existing award.
    if (this.createAwardForm.get('id').value) {
      this.updateExistingAward(context);
    } else {
      this.createNewAward(context);
    }

    this.isAwardListEdit = false;
	}


  /**
   * Updates the class-level selected user object whenever 
   * a change is made on the input form.
   */
  onUserSelectChange() {
    const curr = this.f.user.value;
    this.user = this.users.filter(obj => obj.id === curr)[0];
  }
  
  /**
   * Updates the class-level selected award type object whenever 
   * a change is made on the input form.
   */
  onAwardTypeSelectChange() {
    const curr = this.f.awardType.value;
    this.awardType = this.awardTypes.filter(obj => obj.id === curr)[0];
  }
  
  /**
   * Updates the class-level selected employee object whenever 
   * a change is made on the input form.
   */
  onEmployeeSelectChange() {
    const curr = this.f.employee.value;
    this.employee = this.employees.filter(obj => obj.id === curr)[0];
  }

  /**
   * Event handler for when the user cancels award creation.
   * Clears the form and makes sure the context is set to 'New Award'
   */
	onReset() {
		this.submitted = false;
    this.createAwardForm.reset();
    this.setAwardFormContext(false);

    if (this.isAwardListEdit) {
      this.selectedTab = 1;
    }

    this.isAwardListEdit = false;
  }
  
  /**
   * Updates the title and button labels based on the context of the visible award.
   * If it's a New Award, the title is 'New Award' and the buttons say 'Create'.
   * If it's an existing award, the title is 'Update Award' and the button say 'Save'
   */
  setAwardFormContext(isExistingAward: boolean) {
    if (isExistingAward) {
      this.formContext.titleText = 'Update Award';
      this.formContext.submitButtonText = 'Save Changes';
      this.formContext.subtitleText = 'Use the form below to make changes to this award record.';
    } else {
      this.formContext.titleText = 'New Award';
      this.formContext.submitButtonText = 'Create Award';
      this.formContext.subtitleText = 'Use the form below to create a new award for one of the employees.';
    }
  }

  /**
   * When the user clicks the tab to review awards,
   * reset the Award Form.
   */
  handleTabChange($event) {
    if ($event.index === 1) {
      this.onReset();
    }
  }
}
