import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DomesticSummaryComponent } from './domestic-summary.component';

describe('DomesticSummaryComponent', () => {
  let component: DomesticSummaryComponent;
  let fixture: ComponentFixture<DomesticSummaryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DomesticSummaryComponent]
    });
    fixture = TestBed.createComponent(DomesticSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
