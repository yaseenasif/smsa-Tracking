import { TestBed } from '@angular/core/testing';

import { InternationalRouteService } from './international-air-route.service';

describe('InternationalRouteService', () => {
  let service: InternationalRouteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InternationalRouteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
