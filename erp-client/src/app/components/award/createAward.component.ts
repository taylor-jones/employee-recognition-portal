import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AwardService } from '../../services/award/award.service';
import { AwardTypeService } from '../../services/awardType/awardType.service';
import { EmployeeService } from '../../services/employee/employee.service';
import { UserService } from '../../services/user/user.service';
import { User } from '../../models/user.model';
import { AwardType } from '../../models/awardType.model';
import { Employee } from '../../models/employee.model';

@Component({
	selector: 'erp-create-award',
	templateUrl: './createAward.component.html',
	styleUrls: [ './createAward.component.scss' ]
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

  
	constructor(
		private formBuilder: FormBuilder,
		private awardService: AwardService,
		private awardTypeService: AwardTypeService,
		private employeeService: EmployeeService,
		private userService: UserService,
	) {}


	ngOnInit() {
		this.createAwardForm = this.formBuilder.group({
			user: [ '', Validators.required ],
			awardType: [ '', Validators.required ],
			employee: [ '', Validators.required ],
			description: [ null ],
			awardedDate: [ null ],
			awardedTime: [ null ]
    });
    
		this.userService.getAllUsers().subscribe((users) => {
      users.sort((a, b): number =>  a.username.toLowerCase() < b.username.toLowerCase() ? -1 : 1);
			this.users = users;
    });

		this.awardTypeService.getAllAwardTypes().subscribe((awardTypes) => {
      awardTypes.sort((a, b): number =>  a.name.toLowerCase() < b.name.toLowerCase() ? -1 : 1);
			this.awardTypes = awardTypes;
    });
    
		this.employeeService.getAllEmployees().subscribe((employees) => {
      employees.sort((a, b): number =>  a.firstName.toLowerCase() < b.firstName.toLowerCase() ? -1 : 1);
      this.employees = employees;
		});
	}


	// helper for reference to form fields
	get f() {
		return this.createAwardForm.controls;
	}

  
  onSubmit() {
		this.submitted = true;

		if (this.createAwardForm.invalid) {
			console.log('INVALID!');
			return;
    }
    
    // load the award type from the award type selection
    this.awardTypeService.getAwardTypeById(this.f.awardType.value)
    .subscribe(awardType => this.awardType = awardType);
    
    // load the employee from the employee selection
    this.employeeService.getEmployeeById(this.f.employee.value)
    .subscribe(employee => this.employee = employee);
    
    // load the user from the current user value
    this.userService.getUserById(this.f.user.value)
      .subscribe(user => this.user = user);

    // make the call to create the award
    this.awardService.createAward({
      id: null,
      awardType: this.awardType,
      employee: this.employee,
      user: this.user,
      description: <string>this.f.description.value,
      awardedDate: <string>this.f.awardedDate.value,
      awardedTime: <string>this.f.awardedTime.value,
    });
	}


	onReset() {
		this.submitted = false;
		this.createAwardForm.reset();
	}
}
