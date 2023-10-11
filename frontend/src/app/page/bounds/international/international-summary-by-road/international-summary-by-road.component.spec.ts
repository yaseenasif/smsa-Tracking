import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternationalSummaryByRoadComponent } from './international-summary-by-road.component';

describe('InternationalSummaryByRoadComponent', () => {
  let component: InternationalSummaryByRoadComponent;
  let fixture: ComponentFixture<InternationalSummaryByRoadComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InternationalSummaryByRoadComponent]
    });
    fixture = TestBed.createComponent(InternationalSummaryByRoadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
