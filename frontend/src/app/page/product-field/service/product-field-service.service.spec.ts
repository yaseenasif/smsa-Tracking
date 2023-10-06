import { TestBed } from '@angular/core/testing';

import { ProductFieldServiceService } from './product-field-service.service';

describe('ProductFieldServiceService', () => {
  let service: ProductFieldServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductFieldServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
