import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Voo } from '../../../models/voo/voo.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { VooService } from '../../../services/voo.service';

@Component({
  selector: 'app-cancelar-voo',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cancelar-voo.component.html',
  styleUrl: './cancelar-voo.component.scss'
})
export class CancelarVooComponent {
  @Input() voo!: Voo;

  constructor(public activeModal: NgbActiveModal, private vooService: VooService) {}

  cancelarVoo(codigoVoo: string): void {
    this.vooService.cancelarVoo(codigoVoo).subscribe(
      (vooCancelado) => {
        // Sucesso: Exibe mensagem de confirmação
        console.log('Voo cancelado com sucesso');
        alert(`Voo ${vooCancelado.codigoVoo} foi cancelado com sucesso.`);
        this.activeModal.close(); // Fecha o modal
      },
      (error) => {
        // Erro: Exibe mensagem apropriada
        console.error('Erro ao cancelar o voo:', error);
        alert('Ocorreu um erro ao cancelar o voo. Tente novamente.');
      }
    );
  }
  
}