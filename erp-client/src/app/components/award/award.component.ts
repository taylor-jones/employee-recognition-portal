import { Component, OnInit } from '@angular/core';
import { AwardService } from '../../services/award/award.service';
import { Award } from '../../models/award.model';

@Component({
	selector: 'erp-award',
	templateUrl: './award.component.html',
	styleUrls: [ './award.component.scss' ]
})
export class AwardComponent implements OnInit {
	awards: Award[] = [];
	award: Award;
	errorMessage: string;
	awardId: number;

	constructor(private awardService: AwardService) {}

	ngOnInit() {}

	getAllAwards(): void {
		this.awards = [];
		this.awardService.getAllAwards().subscribe(
			(awards) => {
				this.awards = awards;
			},
			(error) => {
				this.errorMessage = 'Failed to load awards';
			},
			() => {
				// If you want to do something
			}
		);
	}

	getAwardById(id: number): void {
		this.awards = [];
		this.award = null;
		this.awardService.getAwardById(id).subscribe(
			(award) => {
				this.award = award;
			},
			(error) => {
				this.errorMessage = 'Failed to load award';
			},
			() => {
				// If you want to do something
			}
		);
	}
}
