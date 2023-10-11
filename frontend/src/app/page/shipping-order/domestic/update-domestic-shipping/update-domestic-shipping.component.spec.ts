import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateDomesticShippingComponent } from './update-domestic-shipping.component';

describe('UpdateDomesticShippingComponent', () => {
  let component: UpdateDomesticShippingComponent;
  let fixture: ComponentFixture<UpdateDomesticShippingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateDomesticShippingComponent]
    });
    fixture = TestBed.createComponent(UpdateDomesticShippingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
