import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';

import { ReportService } from 'src/app/services/report/report.service';
import { SnackbarService } from 'src/app/services/snackbar/snackbar.service';
import { User } from 'src/app/models/user.model';
import { AwardType } from 'src/app/models/awardType.model';
import { Employee } from 'src/app/models/employee.model';
import { ValidateDate } from 'src/app/validators/date.validator';
import { ValidateTime } from 'src/app/validators/time.validator';



@Component({
  selector: 'erp-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {

  //
  // cookies
  //
  cookieUser: string;
  isAdmin: boolean;
  userId: number;

  //
  // reports
  //
  awardTypeCounts: null;
  userAwardCounts: null;
  regionAwardCounts: null;
  employeeAwardCounts: null;
  employeeAwardDiversity: null;


  constructor(
    private _cookieService: CookieService,
    private _reportService: ReportService,
    private _snackbar: SnackbarService,
  ) { 
    // check user credentials
    const cookies: {} = _cookieService.getAll();
    this.cookieUser = cookies['user'];
    this.isAdmin = cookies['admin'] == 'true';
    this.userId = cookies['userId'] || 0;
  }

  ngOnInit() {
    this.getUserAwardCounts();
    this.getRegionAwardCounts();
    this.getAwardTypeCounts();
    this.getEmployeeAwardCounts();
    this.getEmployeeAwardDiversity();
  }


  // TODO: If time, abstract these snackbar functions to somewhere
  // nices, since they're duplicates of what's in the award component.

  /**
   * Shows a snackbar with a success message
   * @param {string} message - the message to display on the smackbar
   */
  showSnackbarSuccess(message, action = 'Okay') {
    this._snackbar.showSuccess(message, action, {
      duration: 3000,
      panelClass: [ 'snackbar-success' ],
    });
  }

  /**
   * Shows a snackbar with an error message
   * @param {string} message - the message to display on the smackbar
   */
  showSnackbarError(message, action = 'Okay') {
    this._snackbar.showError(message, action, {
      duration: 3000,
      panelClass: [ 'snackbar-error' ],
    });
  }


  /************************
   *   Report Queries
   ************************/

  /**
   * Fetch the user award count totals.
   */
  getUserAwardCounts() {
    this._reportService.getUserAwardCounts().subscribe(response => {
      this.userAwardCounts = response.map(item => {
        return {
          name: item[1],
          value: item[2]
        };
      });

      console.log(this.userAwardCounts);
    }, err => {
      this.showSnackbarError('Failed to get user award totals.');
    });
  }
  

  /**
   * Fetch the employee award count totals.
   */
  getEmployeeAwardCounts() {
    this._reportService.getEmployeeAwardCounts().subscribe(response => {
      this.employeeAwardCounts = response.map(item => {
        return {
          id: item[0],
          firstName: item[1],
          lastName: item[2],
          total: item[3],
        };
      });

      console.log(this.employeeAwardCounts);
    }, err => {
      this.showSnackbarError('Failed to get employee award totals.');
    });
  }


  /**
   * Fetch the award type count totals.
   */
  getAwardTypeCounts() {
    this._reportService.getAwardTypeCounts().subscribe(response => {
      this.awardTypeCounts = response.map(item => {
        return {
          id: item[0],
          name: item[1],
          total: item[2],
        };
      });

      console.log(this.awardTypeCounts);
    }, err => {
      this.showSnackbarError('Failed to get award type totals.');
    });
  }


  /**
   * Fetch the award type count totals.
   */
  getRegionAwardCounts() {
    this._reportService.getRegionAwardCounts().subscribe(response => {
      this.regionAwardCounts = response.map(item => {
        return {
          id: item[0],
          name: item[1],
          total: item[2],
        };
      });

      console.log(this.regionAwardCounts);
    }, err => {
      this.showSnackbarError('Failed to get region award totals.');
    });
  }


  /**
   * Fetch the employee award diversity data.
   */
  getEmployeeAwardDiversity() {
    this._reportService.getEmployeeAwardDiversity().subscribe(response => {
      this.employeeAwardDiversity = response.map(item => {
        return {
          id: item[0],
          firstName: item[1],
          lastName: item[2],
          award_types: item[3],
        };
      });

      console.log(this.employeeAwardDiversity);
    }, err => {
      this.showSnackbarError('Failed to get employee award diversity data.');
    });
  }
  


}
