import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateShippingOrderComponent } from './update-shipping-order.component';

describe('UpdateShippingOrderComponent', () => {
  let component: UpdateShippingOrderComponent;
  let fixture: ComponentFixture<UpdateShippingOrderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateShippingOrderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateShippingOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
