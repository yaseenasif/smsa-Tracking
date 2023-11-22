import { TestBed } from '@angular/core/testing';

import { InternationalRoadRouteService } from './international-road-route.service';

describe('InternationalRoadRouteService', () => {
  let service: InternationalRoadRouteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InternationalRoadRouteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
