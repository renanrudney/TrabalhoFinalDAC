import { Component, Input } from '@angular/core';
import { Funcionario } from '../../../models/funcionario/funcionario.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FuncionarioService } from '../../../services/funcionario.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';


@Component({
  selector: 'app-remover-funcionario',
  standalone: true,
  imports: [CommonModule,HttpClientModule],
  templateUrl: './remover-funcionario.component.html',
  styleUrl: './remover-funcionario.component.scss'
})
export class RemoverFuncionarioComponent {
  @Input() funcionario!: Funcionario;

  constructor(public activeModal: NgbActiveModal, private funcionarioService: FuncionarioService) {}

  removerFuncionario(cpf: string) {
    this.funcionarioService.deletarFuncionario(cpf);
    this.activeModal.close()
  }
}
