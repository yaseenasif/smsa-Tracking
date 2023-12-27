import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternationalAirReportPerformanceComponent } from './international-air-report-performance.component';

describe('InternationalAirReportPerformanceComponent', () => {
  let component: InternationalAirReportPerformanceComponent;
  let fixture: ComponentFixture<InternationalAirReportPerformanceComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternationalAirReportPerformanceComponent]
    });
    fixture = TestBed.createComponent(InternationalAirReportPerformanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
