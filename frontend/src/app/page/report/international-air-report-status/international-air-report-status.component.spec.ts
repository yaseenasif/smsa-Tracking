import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternationalAirReportStatusComponent } from './international-air-report-status.component';

describe('InternationalAirReportStatusComponent', () => {
  let component: InternationalAirReportStatusComponent;
  let fixture: ComponentFixture<InternationalAirReportStatusComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternationalAirReportStatusComponent]
    });
    fixture = TestBed.createComponent(InternationalAirReportStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
