import { TestBed } from '@angular/core/testing';

import { ShipmentStatusService } from './shipment-status.service';

describe('ShipmentStatusService', () => {
  let service: ShipmentStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShipmentStatusService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
