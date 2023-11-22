import { TestBed } from '@angular/core/testing';

import { InternationalAirRouteService } from './international-air-route.service';

describe('InternationalAirRouteService', () => {
  let service: InternationalAirRouteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InternationalAirRouteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
