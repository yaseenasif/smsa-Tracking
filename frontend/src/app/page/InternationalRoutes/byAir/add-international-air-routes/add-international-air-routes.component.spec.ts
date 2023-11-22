import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddInternationalAirRoutesComponent } from './add-international-air-routes.component';

describe('AddInternationalAirRoutesComponent', () => {
  let component: AddInternationalAirRoutesComponent;
  let fixture: ComponentFixture<AddInternationalAirRoutesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddInternationalAirRoutesComponent]
    });
    fixture = TestBed.createComponent(AddInternationalAirRoutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
