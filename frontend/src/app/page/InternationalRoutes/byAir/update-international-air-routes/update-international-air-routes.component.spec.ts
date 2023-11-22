import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateInternationalAirRoutesComponent } from './update-international-air-routes.component';

describe('UpdateInternationalAirRoutesComponent', () => {
  let component: UpdateInternationalAirRoutesComponent;
  let fixture: ComponentFixture<UpdateInternationalAirRoutesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateInternationalAirRoutesComponent]
    });
    fixture = TestBed.createComponent(UpdateInternationalAirRoutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
