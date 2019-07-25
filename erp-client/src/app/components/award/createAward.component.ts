import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'erp-create-award',
  templateUrl: './createAward.component.html',
  styleUrls: ['./createAward.component.scss']
})

export class CreateAwardComponent implements OnInit {
  awardType: string;
  employee: string;
  description: string;
  awardedDate: string;
  awardedTime: string;

  ngOnInit() {
  }
}
