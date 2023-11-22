import { TestBed } from '@angular/core/testing';

import { DomesticRoutesService } from './domestic-routes.service';

describe('DomesticRoutesService', () => {
  let service: DomesticRoutesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DomesticRoutesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
