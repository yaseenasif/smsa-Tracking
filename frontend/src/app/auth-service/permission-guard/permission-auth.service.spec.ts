import { TestBed } from '@angular/core/testing';

import { PermissionAuthService } from './permission-auth.service';

describe('PermissionAuthService', () => {
  let service: PermissionAuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PermissionAuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
