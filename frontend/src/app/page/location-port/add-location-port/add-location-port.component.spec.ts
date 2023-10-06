import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddLocationPortComponent } from './add-location-port.component';

describe('AddLocationPortComponent', () => {
  let component: AddLocationPortComponent;
  let fixture: ComponentFixture<AddLocationPortComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddLocationPortComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddLocationPortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
