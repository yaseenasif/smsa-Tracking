import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDomesticShippingForSummaryComponent } from './view-domestic-shipping-for-summary.component';

describe('ViewDomesticShippingForSummaryComponent', () => {
  let component: ViewDomesticShippingForSummaryComponent;
  let fixture: ComponentFixture<ViewDomesticShippingForSummaryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewDomesticShippingForSummaryComponent]
    });
    fixture = TestBed.createComponent(ViewDomesticShippingForSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
