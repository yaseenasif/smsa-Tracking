import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationPortListComponent } from './location-port-list.component';

describe('LocationPortListComponent', () => {
  let component: LocationPortListComponent;
  let fixture: ComponentFixture<LocationPortListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LocationPortListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LocationPortListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
