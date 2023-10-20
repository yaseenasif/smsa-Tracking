import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateShipmentStatusComponent } from './update-shipment-status.component';

describe('UpdateShipmentStatusComponent', () => {
  let component: UpdateShipmentStatusComponent;
  let fixture: ComponentFixture<UpdateShipmentStatusComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateShipmentStatusComponent]
    });
    fixture = TestBed.createComponent(UpdateShipmentStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
