import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddInternationalRoadRoutesComponent } from './add-international-road-routes.component';

describe('AddInternationalRoadRoutesComponent', () => {
  let component: AddInternationalRoadRoutesComponent;
  let fixture: ComponentFixture<AddInternationalRoadRoutesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddInternationalRoadRoutesComponent]
    });
    fixture = TestBed.createComponent(AddInternationalRoadRoutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
