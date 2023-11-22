import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateInternationalRoadRoutesComponent } from './update-international-road-routes.component';

describe('UpdateInternationalRoadRoutesComponent', () => {
  let component: UpdateInternationalRoadRoutesComponent;
  let fixture: ComponentFixture<UpdateInternationalRoadRoutesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateInternationalRoadRoutesComponent]
    });
    fixture = TestBed.createComponent(UpdateInternationalRoadRoutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
