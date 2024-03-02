import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddInternationalByAirEmailListComponent } from './add-international-by-air-email-list.component';

describe('AddInternationalByAirEmailListComponent', () => {
  let component: AddInternationalByAirEmailListComponent;
  let fixture: ComponentFixture<AddInternationalByAirEmailListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddInternationalByAirEmailListComponent]
    });
    fixture = TestBed.createComponent(AddInternationalByAirEmailListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
