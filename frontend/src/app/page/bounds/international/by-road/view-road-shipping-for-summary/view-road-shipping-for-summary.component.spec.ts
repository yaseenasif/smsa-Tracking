import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewRoadShippingForSummaryComponent } from './view-road-shipping-for-summary.component';

describe('ViewRoadShippingForSummaryComponent', () => {
  let component: ViewRoadShippingForSummaryComponent;
  let fixture: ComponentFixture<ViewRoadShippingForSummaryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewRoadShippingForSummaryComponent]
    });
    fixture = TestBed.createComponent(ViewRoadShippingForSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
