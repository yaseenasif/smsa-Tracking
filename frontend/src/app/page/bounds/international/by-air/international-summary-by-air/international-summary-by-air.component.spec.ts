import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternationalSummaryByAirComponent } from './international-summary-by-air.component';

describe('InternationalSummaryByAirComponent', () => {
  let component: InternationalSummaryByAirComponent;
  let fixture: ComponentFixture<InternationalSummaryByAirComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternationalSummaryByAirComponent]
    });
    fixture = TestBed.createComponent(InternationalSummaryByAirComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
