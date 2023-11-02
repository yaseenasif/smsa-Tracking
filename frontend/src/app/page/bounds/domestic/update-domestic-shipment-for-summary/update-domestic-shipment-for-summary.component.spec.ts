import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateDomesticShipmentForSummaryComponent } from './update-domestic-shipment-for-summary.component';

describe('UpdateDomesticShipmentForSummaryComponent', () => {
  let component: UpdateDomesticShipmentForSummaryComponent;
  let fixture: ComponentFixture<UpdateDomesticShipmentForSummaryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateDomesticShipmentForSummaryComponent]
    });
    fixture = TestBed.createComponent(UpdateDomesticShipmentForSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
