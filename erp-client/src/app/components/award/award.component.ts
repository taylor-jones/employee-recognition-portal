import { Component, OnInit, ViewChild } from '@angular/core';
import { AwardService } from 'src/app/services/award/award.service';
import { Award } from 'src/app/models/award.model';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { SnackbarService } from 'src/app/services/snackbar/snackbar.service';
import { CookieService } from 'ngx-cookie-service';

import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { MomentDateAdapter, MAT_MOMENT_DATE_FORMATS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
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
  // cookies
  //
  cookieUser: string;
  isAdmin: boolean;
  userId: number;


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
	awards: any = [];
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
  filterControl = new FormControl('');
  filterValue = '';


  //
  // methods
  //

	constructor(
    private _awardService: AwardService,
		private _awardTypeService: AwardTypeService,
		private _employeeService: EmployeeService,
    private _formBuilder: FormBuilder,
    private _snackBar: SnackbarService,
    private _userService: UserService,
    private _cookieService: CookieService,
  ) {
    this.dataSource.data = this.awards;
    this.dataSource.filterPredicate = this.handleTableFilter();
    
    // check user credentials
    const cookies: {} = _cookieService.getAll();
    this.cookieUser = cookies['user'];
    this.isAdmin = cookies['admin'] == 'true';
    this.userId = cookies['userId'] || 0;
    // console.log(cookies);
  }

  ngOnInit() {
    this.setAwardFormContext(false);

    // setup the award form
		this.createAwardForm = this._formBuilder.group({
      id: new FormControl({ value: null, disabled: true }),
      user: new FormControl({ value: null, disabled: true }),
      awardType: new FormControl(null, { validators: Validators.required }),
      employee: new FormControl(null, { validators: Validators.required }),
			description: new FormControl(null),
			awardedDate: new FormControl(null, { validators: ValidateDate }),
      awardedTime: new FormControl(null, { validators: ValidateTime }),
    });

    if (this.isAdmin) {
      this.f.awardType.disable();
      this.f.employee.disable();
      this.f.description.disable();
      this.f.awardedDate.disable();
      this.f.awardedTime.disable();
    }


    // fetch all the users
		this._userService.getAllUsers().subscribe((users) => {
      users.sort((a, b): number =>  a.username.toLowerCase() < b.username.toLowerCase() ? -1 : 1);
      this.users = users;
      this.onUserChange(this.cookieUser);
    });



    // fetch all the award types
		this._awardTypeService.getAllAwardTypes().subscribe((awardTypes) => {
      awardTypes.sort((a, b): number =>  a.name.toLowerCase() < b.name.toLowerCase() ? -1 : 1);
			this.awardTypes = awardTypes;
    });
    
    // fetch all the employees
		this._employeeService.getAllEmployees().subscribe((employees) => {
      employees.sort((a, b): number =>  a.firstName.toLowerCase() < b.firstName.toLowerCase() ? -1 : 1);
      this.employees = employees;
		});

    // fetch all the awards
    this.getAllAwards();

    // setup the table filter
    this.filterControl.valueChanges
      .subscribe(value => {
        this.filterValue = value;
        this.dataSource.filter = JSON.stringify(this.filterValue);
      })
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


  /**
   * Processes the table filter to check for any columns having
   * a match to the filter text.
   */
  handleTableFilter(): (data: any, filter: string) => boolean {
    const toHumanTime = t => {
      if (!t) return '';
      const timeArr = t.split(':');
      const meridiem = timeArr[0] > '11' ? 'pm' : 'am';
      return `${(Number(timeArr[0]) + 11) % 12 + 1}:${timeArr[1]} ${meridiem}`;
    };

    const toHumanDate = d => {
      if (!d) return '';
      return moment(d).format('L');
    }

    const filterFunction = function(data, filter): boolean {
      const search = JSON.parse(filter).toLowerCase();
      const fullNames = data.employee.firstName.toLowerCase() + ' ' + data.employee.lastName.toLowerCase();
      const awardedDates = toHumanDate(data.awardedDate);
      const awardedTimes = toHumanTime(data.awardedTime);

      return !filter 
        || data.awardType.name.toLowerCase().indexOf(search) !== -1
        || data.id.toString().toLowerCase().indexOf(search) !== -1
        || fullNames.indexOf(search) !== -1
        || awardedDates.indexOf(search) !== -1
        || awardedTimes.indexOf(search) !== -1;
    };

    return filterFunction;
  }


  /**
   * Refreshes the list of awards based on the current awards in the
   * awards array. Updates the sorting and paginator;
   */
  refreshAwardList(): void {
    this.length = this.awards.length;
    this.dataSource.data = this.awards;

    this.dataSource.sortingDataAccessor = (item, property) => {
      switch(property) {
        case 'awardType': return item.awardType.name.toLocaleLowerCase();
        case 'employee': return item.employee.firstName.toLocaleLowerCase();
        default: return item[property];
      }
    };
    
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }


  /**
   * Gets the existing awards from the db.
   */
	getAllAwards(): void {
		this._awardService.getAllAwards().subscribe(awards => {
      this.awards = awards;
      this.length = awards.length;
      this.refreshAwardList();
    }, error => {
      this.showSnackbarError('Failed to load awards');
    });
  }
  

  /**
   * Deletes the chosen award from the list of awards.
   * @param {Award} award - the award object to delete.
   */
  deleteAward(award: Award) {
    this._awardService.deleteAward(award.id).subscribe(response => {
      this.showSnackbarSuccess('Award successfully deleted');

      // find and delete the award from the datasource
      // without needing to make another call to the API
      const index: number = this.awards.findIndex(d => d === award);
      this.awards.splice(index, 1);
      this.refreshAwardList();

    }, err => {
      this.showSnackbarError('Award not deleted');
    });
  }

  
  /**
   * Loads the award associated with a specified award id into the form
   * @param {Award} award - the award object to load
   */
  loadAward(award: Award) {
    if (award.hasOwnProperty('id')) {
      this.f.id.setValue(award.id);
      this.f.user.setValue(award.userAccount.username);
      this.f.employee.setValue(award.employee.id);
      this.f.awardType.setValue(award.awardType.id);
      this.f.description.setValue(award.description);
      this.f.awardedDate.setValue(award.awardedDate);
      this.f.awardedTime.setValue(award.awardedTime);

      this.employee = award.employee;
      this.awardType = award.awardType;
      this.user = award.userAccount;

      // set the form context to 'Update' and move to the form.
      this.setAwardFormContext(true);
      this.isAwardListEdit = true;
      this.selectedTab = 0;
      this.submitted = false;
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
    this._awardService.createAward(context).subscribe(award => {
      this.f.id.setValue(award.id);
      this.showSnackbarSuccess('Success! The award has been created.');
      this.setAwardFormContext(true);
      this.awards.push(award);
      this.refreshAwardList();
    }, err => {
        this.showSnackbarError('Something has gone wrong. Award creation failed.');
    });
  }


  /**
   * Triggers an HTTP request to update an existing award record.
   * @param {object} context - the body of the HTTP request (the award data)
   */
  updateExistingAward(context) {
    if (this.user.username != this.cookieUser) {
      this.showSnackbarError('Whoops! You can\'t edit another user\'s awards!')
      return;
    }

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
    
    // make sure it's not an admin user
    if (this.isAdmin) {
      this.showSnackbarError('Whoops! Awards are read-only for admin accounts.')
      return;
    }
    
    // if the form isn't valid, stop processing
		if (this.createAwardForm.invalid) {
			return;
    }
    
    // grab the current award date & time values
    const formDate = this.f.awardedDate.value;
    const formTime = this.f.awardedTime.value;

    // build the post body
    const award = {
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
      this.updateExistingAward(award);
    } else {
      this.createNewAward(award);
    }

    this.isAwardListEdit = false;
	}


  /**
   * Updates the class-level selected user object whenever 
   * a change is made on the input form.
   */
  onUserChange(value) {
    this.user = this.users.filter(obj => obj.username === value)[0];
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
    if (this.isAdmin) {
      this.formContext.titleText = 'View Award';
      this.formContext.submitButtonText = 'Save Changes';
      this.formContext.subtitleText = 'As an admin, you may view all award details, but you cannot make change to the award record.';
    } else if (isExistingAward) {
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
    this.submitted = false;

    if ($event.index === 1) {
      this.onReset();
      this.f.user.setValue(this.cookieUser);
    }
  }
}
