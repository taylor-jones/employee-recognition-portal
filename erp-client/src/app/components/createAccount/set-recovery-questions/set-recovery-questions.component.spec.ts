import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SetRecoveryQuestionsComponent } from './set-recovery-questions.component';

describe('SetRecoveryQuestionsComponent', () => {
  let component: SetRecoveryQuestionsComponent;
  let fixture: ComponentFixture<SetRecoveryQuestionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetRecoveryQuestionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SetRecoveryQuestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
