import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDomesticShippingComponent } from './add-domestic-shipping.component';

describe('AddDomesticShippingComponent', () => {
  let component: AddDomesticShippingComponent;
  let fixture: ComponentFixture<AddDomesticShippingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddDomesticShippingComponent]
    });
    fixture = TestBed.createComponent(AddDomesticShippingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
