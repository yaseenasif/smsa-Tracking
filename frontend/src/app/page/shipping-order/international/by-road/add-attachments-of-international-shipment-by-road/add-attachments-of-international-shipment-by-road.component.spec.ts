import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAttachmentsOfInternationalShipmentByRoadComponent } from './add-attachments-of-international-shipment-by-road.component';

describe('AddAttachmentsOfInternationalShipmentByRoadComponent', () => {
  let component: AddAttachmentsOfInternationalShipmentByRoadComponent;
  let fixture: ComponentFixture<AddAttachmentsOfInternationalShipmentByRoadComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddAttachmentsOfInternationalShipmentByRoadComponent]
    });
    fixture = TestBed.createComponent(AddAttachmentsOfInternationalShipmentByRoadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
