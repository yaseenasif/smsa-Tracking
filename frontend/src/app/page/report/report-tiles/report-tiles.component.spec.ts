import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportTilesComponent } from './report-tiles.component';

describe('ReportTilesComponent', () => {
  let component: ReportTilesComponent;
  let fixture: ComponentFixture<ReportTilesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReportTilesComponent]
    });
    fixture = TestBed.createComponent(ReportTilesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
