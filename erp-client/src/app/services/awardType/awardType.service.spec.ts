import { TestBed } from '@angular/core/testing';

import { AwardTypeService } from './awardType.service';

describe('AwardTypeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AwardTypeService = TestBed.get(AwardTypeService);
    expect(service).toBeTruthy();
  });
});
