import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArchiveUserComponent } from './archive-user.component';

describe('ArchiveUserComponent', () => {
  let component: ArchiveUserComponent;
  let fixture: ComponentFixture<ArchiveUserComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ArchiveUserComponent]
    });
    fixture = TestBed.createComponent(ArchiveUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
