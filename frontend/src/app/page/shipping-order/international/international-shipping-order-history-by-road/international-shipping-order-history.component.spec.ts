import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternationalShippingOrderHistoryComponent } from './international-shipping-order-history.component';

describe('InternationalShippingOrderHistoryComponent', () => {
  let component: InternationalShippingOrderHistoryComponent;
  let fixture: ComponentFixture<InternationalShippingOrderHistoryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternationalShippingOrderHistoryComponent]
    });
    fixture = TestBed.createComponent(InternationalShippingOrderHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
