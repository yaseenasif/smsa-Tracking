import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DomesticAttachmentsComponent } from './domestic-attachments.component';

describe('DomesticAttachmentsComponent', () => {
  let component: DomesticAttachmentsComponent;
  let fixture: ComponentFixture<DomesticAttachmentsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DomesticAttachmentsComponent]
    });
    fixture = TestBed.createComponent(DomesticAttachmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
