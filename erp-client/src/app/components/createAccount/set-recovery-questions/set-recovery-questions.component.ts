import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {RecoveryQuestion} from '../../../models/recovery.model';

@Component({
  selector: 'erp-set-recovery-questions',
  templateUrl: './set-recovery-questions.component.html',
  styleUrls: ['./set-recovery-questions.component.scss']
})
export class SetRecoveryQuestionsComponent implements OnInit {

  @Output() event: EventEmitter<RecoveryQuestion[]> = new EventEmitter();

  question1 = 'What is your hometown?';
  question2 = 'What was your first pets name?';
  question3 = 'What was your first car?';

  answer1: string;
  answer2: string;
  answer3: string;

  constructor() { }

  ngOnInit() {
  }

  setRecoveryAnswers() {
    this.event.emit([
      {id: null, question: this.question1, answer: this.answer1},
      {id: null, question: this.question2, answer: this.answer2},
      {id: null, question: this.question3, answer: this.answer3}
    ]);
  }
}
