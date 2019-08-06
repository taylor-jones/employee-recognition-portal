import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';

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


  constructor(
    private _cookieService: CookieService,
  ) { 
    // check user credentials
    const cookies: {} = _cookieService.getAll();
    this.cookieUser = cookies['user'];
    this.isAdmin = cookies['admin'] == 'true';
    this.userId = cookies['userId'] || 0;
  }

  ngOnInit() {}
  
}
