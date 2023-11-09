import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternationalSummaryByRoadAttachmentsComponent } from './international-summary-by-road-attachments.component';

describe('InternationalSummaryByRoadAttachmentsComponent', () => {
  let component: InternationalSummaryByRoadAttachmentsComponent;
  let fixture: ComponentFixture<InternationalSummaryByRoadAttachmentsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternationalSummaryByRoadAttachmentsComponent]
    });
    fixture = TestBed.createComponent(InternationalSummaryByRoadAttachmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
