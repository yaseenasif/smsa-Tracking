import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShippingOrderHistoryComponent } from './shipping-order-history.component';

describe('ShippingOrderHistoryComponent', () => {
  let component: ShippingOrderHistoryComponent;
  let fixture: ComponentFixture<ShippingOrderHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShippingOrderHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShippingOrderHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
