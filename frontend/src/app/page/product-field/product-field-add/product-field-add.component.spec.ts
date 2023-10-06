import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductFieldAddComponent } from './product-field-add.component';

describe('ProductFieldAddComponent', () => {
  let component: ProductFieldAddComponent;
  let fixture: ComponentFixture<ProductFieldAddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductFieldAddComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductFieldAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
