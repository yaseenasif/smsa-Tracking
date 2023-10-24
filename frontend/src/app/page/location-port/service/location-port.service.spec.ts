import { TestBed } from '@angular/core/testing';

import { LocationPortService } from './location-port.service';

describe('LocationPortService', () => {
  let service: LocationPortService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LocationPortService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
