import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetInternationalAirRoutesComponent } from './get-international-air-routes.component';

describe('GetInternationalAirRoutesComponent', () => {
  let component: GetInternationalAirRoutesComponent;
  let fixture: ComponentFixture<GetInternationalAirRoutesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GetInternationalAirRoutesComponent]
    });
    fixture = TestBed.createComponent(GetInternationalAirRoutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
