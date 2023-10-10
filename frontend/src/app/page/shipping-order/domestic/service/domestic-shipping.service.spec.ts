import { TestBed } from '@angular/core/testing';

import { DomesticShippingService } from './domestic-shipping.service';

describe('DomesticShippingService', () => {
  let service: DomesticShippingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DomesticShippingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
