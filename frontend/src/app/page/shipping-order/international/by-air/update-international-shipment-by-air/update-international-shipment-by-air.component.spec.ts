import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateInternationalShipmentByAirComponent } from './update-international-shipment-by-air.component';

describe('UpdateInternationalShipmentByAirComponent', () => {
  let component: UpdateInternationalShipmentByAirComponent;
  let fixture: ComponentFixture<UpdateInternationalShipmentByAirComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateInternationalShipmentByAirComponent]
    });
    fixture = TestBed.createComponent(UpdateInternationalShipmentByAirComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
