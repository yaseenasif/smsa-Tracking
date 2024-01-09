import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddFacilityComponent } from './add-facility.component';

describe('AddFacilityComponent', () => {
  let component: AddFacilityComponent;
  let fixture: ComponentFixture<AddFacilityComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddFacilityComponent]
    });
    fixture = TestBed.createComponent(AddFacilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
