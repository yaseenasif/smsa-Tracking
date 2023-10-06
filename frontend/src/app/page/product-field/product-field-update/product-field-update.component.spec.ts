import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductFieldUpdateComponent } from './product-field-update.component';

describe('ProductFieldUpdateComponent', () => {
  let component: ProductFieldUpdateComponent;
  let fixture: ComponentFixture<ProductFieldUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductFieldUpdateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductFieldUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
