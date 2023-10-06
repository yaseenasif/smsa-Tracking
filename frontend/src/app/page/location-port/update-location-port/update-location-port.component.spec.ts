import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateLocationPortComponent } from './update-location-port.component';

describe('UpdateLocationPortComponent', () => {
  let component: UpdateLocationPortComponent;
  let fixture: ComponentFixture<UpdateLocationPortComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateLocationPortComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateLocationPortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
