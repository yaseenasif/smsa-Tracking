import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewShipmentRoadComponent } from './view-shipment-road.component';

describe('ViewShipmentRoadComponent', () => {
  let component: ViewShipmentRoadComponent;
  let fixture: ComponentFixture<ViewShipmentRoadComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewShipmentRoadComponent]
    });
    fixture = TestBed.createComponent(ViewShipmentRoadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
