import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAirShippingForSummaryComponent } from './view-air-shipping-for-summary.component';

describe('ViewAirShippingForSummaryComponent', () => {
  let component: ViewAirShippingForSummaryComponent;
  let fixture: ComponentFixture<ViewAirShippingForSummaryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewAirShippingForSummaryComponent]
    });
    fixture = TestBed.createComponent(ViewAirShippingForSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
