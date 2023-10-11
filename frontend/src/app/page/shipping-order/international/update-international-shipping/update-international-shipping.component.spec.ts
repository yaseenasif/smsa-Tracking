import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateInternationalShippingComponent } from './update-international-shipping.component';

describe('UpdateInternationalShippingComponent', () => {
  let component: UpdateInternationalShippingComponent;
  let fixture: ComponentFixture<UpdateInternationalShippingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateInternationalShippingComponent]
    });
    fixture = TestBed.createComponent(UpdateInternationalShippingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
