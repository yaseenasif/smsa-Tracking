import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternationalShipmentListAirComponent } from './international-shipment-list-air.component';

describe('InternationalShipmentListAirComponent', () => {
  let component: InternationalShipmentListAirComponent;
  let fixture: ComponentFixture<InternationalShipmentListAirComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternationalShipmentListAirComponent]
    });
    fixture = TestBed.createComponent(InternationalShipmentListAirComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
