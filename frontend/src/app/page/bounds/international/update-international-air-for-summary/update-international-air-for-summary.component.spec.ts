import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateInternationalAirForSummaryComponent } from './update-international-air-for-summary.component';

describe('UpdateInternationalAirForSummaryComponent', () => {
  let component: UpdateInternationalAirForSummaryComponent;
  let fixture: ComponentFixture<UpdateInternationalAirForSummaryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateInternationalAirForSummaryComponent]
    });
    fixture = TestBed.createComponent(UpdateInternationalAirForSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
