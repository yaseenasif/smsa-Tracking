import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewShipmentAirComponent } from './view-shipment-air.component';

describe('ViewShipmentAirComponent', () => {
  let component: ViewShipmentAirComponent;
  let fixture: ComponentFixture<ViewShipmentAirComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewShipmentAirComponent]
    });
    fixture = TestBed.createComponent(ViewShipmentAirComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
