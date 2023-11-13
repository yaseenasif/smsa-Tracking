import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateInternationalRoadForSummaryComponent } from './update-international-road-for-summary.component';

describe('UpdateInternationalRoadForSummaryComponent', () => {
  let component: UpdateInternationalRoadForSummaryComponent;
  let fixture: ComponentFixture<UpdateInternationalRoadForSummaryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateInternationalRoadForSummaryComponent]
    });
    fixture = TestBed.createComponent(UpdateInternationalRoadForSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
