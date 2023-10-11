import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddInternationalShippingComponent } from './add-international-shipping.component';

describe('AddInternationalShippingComponent', () => {
  let component: AddInternationalShippingComponent;
  let fixture: ComponentFixture<AddInternationalShippingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddInternationalShippingComponent]
    });
    fixture = TestBed.createComponent(AddInternationalShippingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
