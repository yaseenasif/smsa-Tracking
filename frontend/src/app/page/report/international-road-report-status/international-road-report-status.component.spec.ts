import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternationalRoadReportStatusComponent } from './international-road-report-status.component';

describe('InternationalRoadReportStatusComponent', () => {
  let component: InternationalRoadReportStatusComponent;
  let fixture: ComponentFixture<InternationalRoadReportStatusComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternationalRoadReportStatusComponent]
    });
    fixture = TestBed.createComponent(InternationalRoadReportStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
