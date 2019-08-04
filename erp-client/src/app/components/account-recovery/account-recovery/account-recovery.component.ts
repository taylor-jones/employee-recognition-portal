import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';
import {AccountRecoveryService} from '../../../services/account-recovery/account-recovery.service';
import {RecoveryQuestion} from '../../../models/recovery.model';

@Component({
  selector: 'erp-account-recovery-selection',
  templateUrl: './account-recovery.component.html',
  styleUrls: ['./account-recovery.component.scss']
})
export class AccountRecoveryComponent implements OnInit {

  username: string;
  errorMessage: string;
  questions: RecoveryQuestion[];

  u = this.username;

  constructor(private accountRecoveryService: AccountRecoveryService,
              private router: Router) {
  }

  ngOnInit() {
  }

  handleAccountSelection(): void {
    this.accountRecoveryService.getRecoveryQuestions(this.username).subscribe((recoveryQuestions: RecoveryQuestion[]) => {
        this.questions = recoveryQuestions;
      },
      (error) => {
        this.errorMessage = error;
      }
    );
  }

  handleAnswersSubmit(): void {
    this.errorMessage = null;
    this.accountRecoveryService.checkRecoveryAnswers(this.username, this.questions).subscribe(
      () => {
        console.log('Success - change password');
        this.router.navigate(['/changePassword']);
      },
      (error) => {
        this.errorMessage = 'One or more questions are incorrect.';
      }
    );
  }
}
