import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateDomesticRoutesComponent } from './update-domestic-routes.component';

describe('UpdateDomesticRoutesComponent', () => {
  let component: UpdateDomesticRoutesComponent;
  let fixture: ComponentFixture<UpdateDomesticRoutesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateDomesticRoutesComponent]
    });
    fixture = TestBed.createComponent(UpdateDomesticRoutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
