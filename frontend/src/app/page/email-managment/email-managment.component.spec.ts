import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailManagmentComponent } from './email-managment.component';

describe('EmailManagmentComponent', () => {
  let component: EmailManagmentComponent;
  let fixture: ComponentFixture<EmailManagmentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmailManagmentComponent]
    });
    fixture = TestBed.createComponent(EmailManagmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
