import { TestBed } from '@angular/core/testing';

import { TransacaoMilhasService } from './transacao-milhas.service';

describe('TransacaoMilhasService', () => {
  let service: TransacaoMilhasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransacaoMilhasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
