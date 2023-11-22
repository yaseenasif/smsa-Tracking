import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetDomesticRoutesComponent } from './get-domestic-routes.component';

describe('GetDomesticRoutesComponent', () => {
  let component: GetDomesticRoutesComponent;
  let fixture: ComponentFixture<GetDomesticRoutesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GetDomesticRoutesComponent]
    });
    fixture = TestBed.createComponent(GetDomesticRoutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
