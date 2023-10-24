import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddShipmentStatusComponent } from './add-shipment-status.component';

describe('AddShipmentStatusComponent', () => {
  let component: AddShipmentStatusComponent;
  let fixture: ComponentFixture<AddShipmentStatusComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddShipmentStatusComponent]
    });
    fixture = TestBed.createComponent(AddShipmentStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
