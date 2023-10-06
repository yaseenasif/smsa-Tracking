import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductFieldListComponent } from './product-field-list.component';

describe('ProductFieldListComponent', () => {
  let component: ProductFieldListComponent;
  let fixture: ComponentFixture<ProductFieldListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductFieldListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductFieldListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
