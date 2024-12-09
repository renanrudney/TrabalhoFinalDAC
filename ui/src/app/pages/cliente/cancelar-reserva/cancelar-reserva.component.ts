import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Reserva } from '../../../models/reserva/reserva.model';
import { ReservaService } from '../../../services/reserva.service';
import { VooService } from '../../../services/voo.service';
import { CommonModule } from '@angular/common';
import { Voo } from '../../../models/voo/voo.model';

@Component({
  selector: 'app-cancelar-reserva',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cancelar-reserva.component.html',
  styleUrl: './cancelar-reserva.component.scss',
})

export class CancelarReservaComponent {
  @Input() reserva!: Reserva;
  @Input() voo!: Voo;
  
  constructor(public activeModal: NgbActiveModal, private reservaService: ReservaService, private vooService: VooService) {}

  cancelarReserva(codigoReserva: string): void {
    this.reservaService.cancelarReserva(codigoReserva).subscribe({
      next: () => {
        console.log('Reserva cancelada com sucesso');
        alert('Reserva cancelada com sucesso.');
      },
      error: (err: any) => {
        console.error('Erro ao cancelar reserva:', err);
        alert('Ocorreu um erro ao tentar cancelar a reserva. Por favor, tente novamente.');
      }
    });    
    this.activeModal.close()
  }

  getVooOrigem(): string {
    return this.voo ? this.voo.origem : 'N/A';
  }

  getVooDestino(): string {
    return this.voo ? this.voo.destino : 'N/A';
  }
}
