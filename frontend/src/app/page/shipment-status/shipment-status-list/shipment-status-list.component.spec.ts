import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShipmentStatusListComponent } from './shipment-status-list.component';

describe('ShipmentStatusListComponent', () => {
  let component: ShipmentStatusListComponent;
  let fixture: ComponentFixture<ShipmentStatusListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShipmentStatusListComponent]
    });
    fixture = TestBed.createComponent(ShipmentStatusListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
