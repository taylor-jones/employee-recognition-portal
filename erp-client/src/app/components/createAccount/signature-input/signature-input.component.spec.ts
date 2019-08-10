import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignatureInputComponent } from './signature-input.component';

describe('SignatureInputComponent', () => {
  let component: SignatureInputComponent;
  let fixture: ComponentFixture<SignatureInputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignatureInputComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignatureInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
