import { TestBed } from '@angular/core/testing';

import { InternationalShipmentByAirService } from './international-shipment-by-air.service';

describe('InternationalShipmentByAirService', () => {
  let service: InternationalShipmentByAirService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InternationalShipmentByAirService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
