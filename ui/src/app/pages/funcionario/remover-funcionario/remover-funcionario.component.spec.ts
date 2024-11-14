import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoverFuncionarioComponent } from './remover-funcionario.component';

describe('RemoverFuncionarioComponent', () => {
  let component: RemoverFuncionarioComponent;
  let fixture: ComponentFixture<RemoverFuncionarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RemoverFuncionarioComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RemoverFuncionarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
