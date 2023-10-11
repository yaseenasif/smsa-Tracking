import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternationalShipmentOrderHistoryByAirComponent } from './international-shipment-order-history-by-air.component';

describe('InternationalShipmentOrderHistoryByAirComponent', () => {
  let component: InternationalShipmentOrderHistoryByAirComponent;
  let fixture: ComponentFixture<InternationalShipmentOrderHistoryByAirComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternationalShipmentOrderHistoryByAirComponent]
    });
    fixture = TestBed.createComponent(InternationalShipmentOrderHistoryByAirComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
