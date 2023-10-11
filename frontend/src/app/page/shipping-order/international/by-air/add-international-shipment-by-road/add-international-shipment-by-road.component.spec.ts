import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddInternationalShipmentByRoadComponent } from './add-international-shipment-by-road.component';

describe('AddInternationalShipmentByRoadComponent', () => {
  let component: AddInternationalShipmentByRoadComponent;
  let fixture: ComponentFixture<AddInternationalShipmentByRoadComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddInternationalShipmentByRoadComponent]
    });
    fixture = TestBed.createComponent(AddInternationalShipmentByRoadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
