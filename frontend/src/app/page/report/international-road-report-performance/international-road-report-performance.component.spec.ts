import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternationalRoadReportPerformanceComponent } from './international-road-report-performance.component';

describe('InternationalRoadReportPerformanceComponent', () => {
  let component: InternationalRoadReportPerformanceComponent;
  let fixture: ComponentFixture<InternationalRoadReportPerformanceComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternationalRoadReportPerformanceComponent]
    });
    fixture = TestBed.createComponent(InternationalRoadReportPerformanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
