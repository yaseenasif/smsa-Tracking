import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDomesticEmailListComponent } from './add-domestic-email-list.component';

describe('AddDomesticEmailListComponent', () => {
  let component: AddDomesticEmailListComponent;
  let fixture: ComponentFixture<AddDomesticEmailListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddDomesticEmailListComponent]
    });
    fixture = TestBed.createComponent(AddDomesticEmailListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
