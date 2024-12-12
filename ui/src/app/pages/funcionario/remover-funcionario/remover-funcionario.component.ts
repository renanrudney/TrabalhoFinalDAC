import { Component, Input } from '@angular/core';
import { Funcionario } from '../../../models/funcionario/funcionario.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FuncionarioService } from '../../../services/funcionario.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-remover-funcionario',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './remover-funcionario.component.html',
  styleUrl: './remover-funcionario.component.scss'
})
export class RemoverFuncionarioComponent {
  @Input() funcionario!: Funcionario;

  constructor(public activeModal: NgbActiveModal, private funcionarioService: FuncionarioService, private router: Router) {}

  removerFuncionario(id: string | undefined): void {
    if (id) {
      console.log('excluindo ' + id);
      this.funcionarioService.deletarFuncionario(id).subscribe(
        () => {
          // Sucesso: exibe mensagem de confirmação
          alert(`Funcionário com ID ${id} removido com sucesso!`);
          this.activeModal.close(); // Fecha o modal
          this.router.navigate(['/home-funcionario'])
        },
        (error) => {
          // Erro: exibe mensagem apropriada
          console.error('Erro ao remover o funcionário:', error);
          alert('Ocorreu um erro ao remover o funcionário. Tente novamente.');
        }
      );
    }
  }
}
