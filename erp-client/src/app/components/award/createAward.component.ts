import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
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
      id: new FormControl({ value: null, disabled: true }),
      user: new FormControl({ value: '', Validators: true }),
      awardType: new FormControl({ value: '', Validators: true }),
      employee: new FormControl({ value: '', Validators: true }),
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
  



  createNewAward(context) {    
    // make the call to create the award
    this.awardService.createAward(context).subscribe(
      response => this.createAwardForm.get('id').setValue(response.id)
    );
  }



  updateExistingAward(context) {
    // make the call to create the award
    this.awardService.updateAward(context).subscribe(
      response => this.createAwardForm.get('id').setValue(response.id)
    );
  }

  
  onSubmit() {
		this.submitted = true;

		if (this.createAwardForm.invalid) {
			console.log('INVALID!');
			return;
    }


    // build the post body
    const context = {
      id: this.f.id.value,
      awardType: this.awardType,
      employee: this.employee,
      userAccount: this.user,
      description: this.f.description.value,
      awardedDate: this.f.awardedDate.value,
      awardedTime: this.f.awardedTime.value,
    };


    if (this.createAwardForm.get('id').value) {
      this.updateExistingAward(context);
    } else {
      this.createNewAward(context);
    }

    // console.log(context);
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
