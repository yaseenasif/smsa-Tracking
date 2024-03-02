import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddInternationalByRoadEmailListComponent } from './add-international-by-road-email-list.component';

describe('AddInternationalByRoadEmailListComponent', () => {
  let component: AddInternationalByRoadEmailListComponent;
  let fixture: ComponentFixture<AddInternationalByRoadEmailListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddInternationalByRoadEmailListComponent]
    });
    fixture = TestBed.createComponent(AddInternationalByRoadEmailListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
