import { Component, OnInit, ViewChild, NgModule } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { AwardService } from 'src/app/services/award/award.service';


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
	displayedColumns: string[] = ['id', 'awardType', 'employee', 'awardedDate', 'awardedTime', 'edit', 'delete' ];
	pageSizeOptions: number[];
	pageSize: number;
	length: number;
	dataSource = new MatTableDataSource([]);

	constructor(private awardService: AwardService) {}

	ngOnInit() {
    this.getAllAwards();
  }
  
  ngAfterViewInit (){
    this.dataSource.sort = this.sort;
  }

	getAllAwards(): void {
		this.awardService.getAllAwards().subscribe(
			(awards) => {
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
			},
			(error) => {
				this.errorMessage = 'Failed to load awards';
			}
		);
	}

  // TODO: Make this work w/ employee names and award types
	public filter(value: string) {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  

  handleEdit(id) {
    console.log('edit award', id);
  }


  handleDelete(id) {
    console.log('delete award', id);
  }
}
