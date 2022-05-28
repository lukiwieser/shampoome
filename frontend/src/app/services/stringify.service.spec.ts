import { TestBed } from '@angular/core/testing';

import { StringifyService } from './stringify.service';

describe('StringifyHelperService', () => {
  let service: StringifyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StringifyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
