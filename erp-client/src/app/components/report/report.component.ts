import { Component, OnInit, ViewChild } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { MatTableDataSource, MatSort } from '@angular/material';
import { Router } from '@angular/router';

import { ReportService } from 'src/app/services/report/report.service';
import { SnackbarService } from 'src/app/services/snackbar/snackbar.service';
import { AppComponent } from 'src/app/app.component';


@Component({
  selector: 'erp-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {
  @ViewChild(MatSort, { static: false }) sort: MatSort;

  //
  // report data
  //
  awardTypeCounts: any[];
  userAwardCounts: any[];
  regionAwardCounts: any[];
  employeeAwardCounts: any[];
  employeeAwardDiversity: any[];
  regionAwards: any = {};
  regionEmployees: any = {};
  regionEmployeeAwards: any = {};

  // 
  // report table
  // 
  employeeAwards = new MatTableDataSource([]);
  displayedColumns: string[] = [
    'id',
    'description',
    'awardedDate',
    'awardedTime',
  ];

  // user selections from the charts
  selected: any = {
    region: {
      id: null,
      name: null,
    },
    employee: {
      id: null,
      name: null,
    },
    awardType: {
      id: null,
      name: null,
    }
  }

  selectedChart: any = {
    regions: true,
    byUser: false,
    byType: false
  }


  // 
  // report chards / graphs
  // 
  regionAwardTotalsChart = {
    data: [],
    showXAxis: true,
    showYAxis: true,
    gradient: false,
    showLegend: true,
    showXAxisLabel: true,
    showYAxisLabel: true,
    xAxisLabel: 'Region',
    yAxisLabel: 'Total Awards',
    scheme: 'vivid',
    showAll: true,
  };

  regionEmployeeAwardsChart = {
    data: [],
    gradient: false,
    showXAxis: true,
    showYAxis: true,
    showLegend: true,
    showXAxisLabel: true,
    showYAxisLabel: true,
    xAxisLabel: 'Total Awards',
    yAxisLabel: 'Employee',
    scheme: 'vivid',
    showAll: true,
  };


  constructor(
    private _app: AppComponent,
    private _reportService: ReportService,
    private _snackbar: SnackbarService,
    private _router: Router,
  ) {

    Object.assign(this.regionAwardTotalsChart, this.regionAwardCounts);
  }

  ngOnInit() {
    if (!this._app.isAdmin) {
      this._router.navigate(['/']);
    }

    this.getRegionAwardCounts();
    this.getUserAwardCounts();
    this.getAwardTypeCounts();
    this.regionAwardTotalsChart['data'] = [...this.regionAwardTotalsChart['data']];
  }

  ngAfterViewInit(): void {
    this.employeeAwards.sort = this.sort;
  }


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
   *  Report Charts/Graphs
   ************************/

  // Switch chart views
  toggleSelectedChart(selectedChartKey: string) {
    Object.keys(this.selectedChart).forEach( k => {
      this.selectedChart[k] = false;
    })
    this.selectedChart[selectedChartKey] = true;
  }

  onRegionAwardsChartSelect($event) {
    const regionName = typeof $event === 'string' ? $event : $event.name;
    const regionId = this.regionAwardCounts.find(region => region.name === regionName).id;

    this.selected = {
      region: {
        id: regionId,
        name: regionName,
      },
      employee: {
        id: null,
        name: null,
      },
      awardType: {
        id: null,
        name: null,
      }
    };

    this.getRegionAwards(regionId);
  }


  onRegionAwardsSlideToggleChange(isChecked) {
    if (isChecked) {
      this.regionAwardTotalsChart['data'] = this.regionAwardCounts;
    } else {
      this.regionAwardTotalsChart['data'] = this.regionAwardCounts
        .filter(region => region.value > 0);
    }
  }


  /**
   * Builds a list of the number and type of awards for the
   * employees at the selected region.
   * @param regionId id of the selected region
   */
  buildRegionEmployeeAwardsHeatMap(regionId) {
    // if the region awards have already been determined, use those..
    if (this.regionEmployeeAwards[regionId]) {
      this.regionEmployeeAwardsChart['data'] = this.regionEmployeeAwards[regionId];
      return;
    }

    // if the region employee awards data hasn't yet been gathered...
    const ra = this.regionAwards[regionId];
    const re = this.regionEmployees[regionId];
    const data = [];

    re.forEach((e) => {
      const name = `${e.firstName} ${e.lastName}`;

      // build the series of awards for this employee
      const series = [];

      /**
       * Filter all of this region's awards to only those that belong
       * this this employee. Then check this employee's series of awards
       * to see if this award type is already accounted for. If it is,
       * then just increment the total count of this award type for this
       * employee. If this employee does not have any of this award type
       * yet, then add this award type to the series of awards for this
       * employee.
       */
      ra.filter((a) => a.employee.id === e.id).forEach((b) => {
        const ex = series.findIndex(obj => obj.name === b.awardType.name);
        if (ex > -1) {
          series[ex].value += 1;
        } else {
          series.push({
            name: b.awardType.name,
            value: 1,
          });
        }
      });

      // push the series of awards to the region employee awards data
      if (series.length > 0) {
        data.push({
          name: name,
          series: series,
        });
      }
    });


    // update the cached awards list for this region's employees so
    // that we only need to do this process once.
    this.regionEmployeeAwards[regionId] = data.sort((a, b) => a.name > b.name ? 1 : -1);
    this.regionEmployeeAwardsChart['data'] = this.regionEmployeeAwards[regionId];
  }


  /**
   * Builds a list of award details for the selected
   * employee and award type.
   * @param $event the selected chart element
   */
  onRegionEmployeeAwardsChartSelect($event) {
    if (typeof $event === 'string') return;

    // find the associated awards for this employee 
    // and display the details.
    this.selected.employee.name = $event.series;
    this.selected.awardType.name = $event.label;

    this.selected.employee.id = (this.regionEmployees[this.selected.region.id])
      .find(e => e.fullName === this.selected.employee.name).id;

    this.employeeAwards = new MatTableDataSource(this
      .regionAwards[this.selected.region.id]
      .filter(a => a.employee.id === this.selected.employee.id
        && a.awardType.name === this.selected.awardType.name));

    this.employeeAwards.sort = this.sort;
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
          name: item[1],
          value: item[2]
        };
      });
    }, err => {
      this.showSnackbarError('Failed to get award type totals.');
    });
  }


  /**
   * Fetch the awards for a particular region.
   */
  getRegionAwards(regionId: number) {
    if (this.regionAwards[regionId]) {
      this.getRegionEmployees(regionId);
    }

    this._reportService.getRegionAwards(regionId).subscribe(response => {
      this.regionAwards[regionId] = response;
      this.getRegionEmployees(regionId);
    }, err => {
      this.showSnackbarError('Failed to get region awards.');
    });
  }


  /**
   * Fetch the employees from a particular region.
   */
  getRegionEmployees(regionId: number) {
    if (this.regionEmployees[regionId]) {
      this.buildRegionEmployeeAwardsHeatMap(regionId);
    }

    this._reportService.getRegionEmployees(regionId).subscribe(response => {
      this.regionEmployees[regionId] = response;
      this.buildRegionEmployeeAwardsHeatMap(regionId);
    }, err => {
      this.showSnackbarError('Failed to get region awards.');
    });
  }


  /**
   * Fetch the total awards for a particular region.
   */
  getRegionAwardCounts() {
    this._reportService.getRegionAwardCounts().subscribe(response => {
      this.regionAwardCounts = response.map(item => {
        return {
          id: item[0],
          name: item[1],
          value: item[2],
        };
      });

      this.regionAwardTotalsChart['data'] = this.regionAwardCounts;
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
    }, err => {
      this.showSnackbarError('Failed to get employee award diversity data.');
    });
  }
}
