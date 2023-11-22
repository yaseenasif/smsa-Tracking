import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetInternationalRoadRoutesComponent } from './get-international-road-routes.component';

describe('GetInternationalRoadRoutesComponent', () => {
  let component: GetInternationalRoadRoutesComponent;
  let fixture: ComponentFixture<GetInternationalRoadRoutesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GetInternationalRoadRoutesComponent]
    });
    fixture = TestBed.createComponent(GetInternationalRoadRoutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
