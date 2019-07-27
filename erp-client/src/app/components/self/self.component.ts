import { Component, OnInit } from '@angular/core';
import {SelfService} from '../../services/self/self.service';
import {User} from '../../models/user.model';

@Component({
  selector: 'erp-self',
  templateUrl: './self.component.html',
  styleUrls: ['./self.component.scss']
})
export class SelfComponent implements OnInit {

  self: User;
  errorMessage: string;

  constructor(private selfService: SelfService) { }

  ngOnInit() {
  }

  getSelf(): void {
    this.selfService.getSelf().subscribe(
      (self) => {
        console.log('self: ' + self);
        this.self = self;
      },
      (error) => {
        this.errorMessage = 'Failed to get self';
      },
      () => {
        // If you want to do something
      }
    );
  }
}
