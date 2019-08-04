// import {Component, OnInit} from '@angular/core';
// import {Router} from '@angular/router';
// import {CookieService} from 'ngx-cookie-service';
// import {AccountRecoveryService} from '../../../services/account-recovery/account-recovery.service';
// import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
// import {MatSnackBar} from '@angular/material';
// import {RecoveryAnswers, RecoveryQuestion} from '../../../models/recovery.model';
//
// @Component({
//   selector: 'erp-account-recovery',
//   templateUrl: './account-recovery-questions.component.html',
//   styleUrls: ['./account-recovery-questions.component.scss']
// })
// export class AccountRecoveryQuestionsComponent implements OnInit {
//
//   recoveryQuestionsForm: FormGroup;
//   submitted = false;
//
//   question1: string;
//   question2: string;
//   question3: string;
//
//   answer1: string;
//   answer2: string;
//   answer3: string;
//
//   questions: RecoveryQuestion[];
//   answers: RecoveryAnswers[];
//
//   errorMessage: string;
//
//   constructor(private accountRecoveryService: AccountRecoveryService,
//               private formBuilder: FormBuilder,
//               private router: Router,
//               private cookieService: CookieService) {
//   }
//
//   ngOnInit() {
//     this.recoveryQuestionsForm = this.formBuilder.group({
//       question1: new FormControl(null, {validators: Validators.required}),
//       question2: new FormControl(null, {validators: Validators.required}),
//       question3: new FormControl(null, {validators: Validators.required})
//     });
//
//     this.accountRecoveryService.getRecoveryQuestions("foo").subscribe((questions) => {
//       this.question1 = questions.question1;
//       this.question2 = questions.question2;
//       this.question3 = questions.question3;
//     });
//   }
//
//   get f() {
//     return this.recoveryQuestionsForm.controls;
//   }
//
//   handleAccountRecovery(): void {
//     // hard coded id
//     this.accountRecoveryService.checkRecoveryAnswers(2, {
//       answer1: this.answer1,
//       answer2: this.answer2,
//       answer3: this.answer3
//     }).subscribe((correct) => {
//         if (correct) {
//           this.router.navigate(['/changePassword']);
//         } else {
//           this.router.navigate(['/login']);
//         }
//       },
//       (error) => {
//         this.errorMessage = error;
//       },
//       () => {
//
//       });
//   }
// }
