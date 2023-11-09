import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAttachmentsOfInternationalShipmentByAirComponent } from './add-attachments-of-international-shipment-by-air.component';

describe('AddAttachmentsOfInternationalShipmentByAirComponent', () => {
  let component: AddAttachmentsOfInternationalShipmentByAirComponent;
  let fixture: ComponentFixture<AddAttachmentsOfInternationalShipmentByAirComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddAttachmentsOfInternationalShipmentByAirComponent]
    });
    fixture = TestBed.createComponent(AddAttachmentsOfInternationalShipmentByAirComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
