import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternationalSummaryByAirAttachmentsComponent } from './international-summary-by-air-attachments.component';

describe('InternationalSummaryByAirAttachmentsComponent', () => {
  let component: InternationalSummaryByAirAttachmentsComponent;
  let fixture: ComponentFixture<InternationalSummaryByAirAttachmentsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternationalSummaryByAirAttachmentsComponent]
    });
    fixture = TestBed.createComponent(InternationalSummaryByAirAttachmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
