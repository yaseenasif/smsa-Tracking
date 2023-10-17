import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DomesticShippingListComponent } from './domestic-shipping-list.component';

describe('DomesticShippingListComponent', () => {
  let component: DomesticShippingListComponent;
  let fixture: ComponentFixture<DomesticShippingListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DomesticShippingListComponent]
    });
    fixture = TestBed.createComponent(DomesticShippingListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
