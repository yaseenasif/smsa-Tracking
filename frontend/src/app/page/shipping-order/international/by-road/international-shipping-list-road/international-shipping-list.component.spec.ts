import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternationalShippingListComponent } from './international-shipping-list.component';

describe('InternationalShippingListComponent', () => {
  let component: InternationalShippingListComponent;
  let fixture: ComponentFixture<InternationalShippingListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternationalShippingListComponent]
    });
    fixture = TestBed.createComponent(InternationalShippingListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
