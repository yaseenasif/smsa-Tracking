import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DomesticReportPerformanceComponent } from './domestic-report-performance.component';

describe('DomesticReportPerformanceComponent', () => {
  let component: DomesticReportPerformanceComponent;
  let fixture: ComponentFixture<DomesticReportPerformanceComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DomesticReportPerformanceComponent]
    });
    fixture = TestBed.createComponent(DomesticReportPerformanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
