import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DomesticShippingOrderHistoryComponent } from './domestic-shipping-order-history.component';

describe('DomesticShippingOrderHistoryComponent', () => {
  let component: DomesticShippingOrderHistoryComponent;
  let fixture: ComponentFixture<DomesticShippingOrderHistoryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DomesticShippingOrderHistoryComponent]
    });
    fixture = TestBed.createComponent(DomesticShippingOrderHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
