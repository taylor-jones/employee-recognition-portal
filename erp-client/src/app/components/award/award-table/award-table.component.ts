import { Component, OnInit, ViewChild, NgModule } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { AwardService } from 'src/app/services/award/award.service';
import { ConvertTimePipe } from '../../../pipes/convert-time.pipe';


@Component({
	selector: 'award-table',
	templateUrl: './award-table.component.html',
	styleUrls: [ './award-table.component.scss' ]
})
export class AwardTableComponent implements OnInit {
	@ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
	@ViewChild(MatSort, { static: false }) sort: MatSort;

	awards: any = null;
	errorMessage: string;
	displayedColumns: string[] = ['id', 'awardType', 'employee', 'awardedDate', 'awardedTime' ];
	pageSizeOptions: number[];
	pageSize: number;
	length: number;
	dataSource = new MatTableDataSource([]);

	constructor(private awardService: AwardService) {}

	ngOnInit() {
    this.getAllAwards();
	}

	getAllAwards(): void {
		this.awardService.getAllAwards().subscribe(
			(awards) => {
				this.awards = awards;
				this.length = awards.length;
				this.dataSource = new MatTableDataSource(this.awards);
				this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        
        console.log(this.awards);
			},
			(error) => {
				this.errorMessage = 'Failed to load awards';
			}
		);
	}

	public filter(value: string) {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
	}
}
