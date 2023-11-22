import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDomesticRoutesComponent } from './add-domestic-routes.component';

describe('AddDomesticRoutesComponent', () => {
  let component: AddDomesticRoutesComponent;
  let fixture: ComponentFixture<AddDomesticRoutesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddDomesticRoutesComponent]
    });
    fixture = TestBed.createComponent(AddDomesticRoutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
