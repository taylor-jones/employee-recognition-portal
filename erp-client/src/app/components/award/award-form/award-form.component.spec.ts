import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAwardComponent } from './award-form.component';

describe('CreateAwardComponent', () => {
  let component: CreateAwardComponent;
  let fixture: ComponentFixture<CreateAwardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateAwardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAwardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
