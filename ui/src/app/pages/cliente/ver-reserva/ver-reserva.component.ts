import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Reserva } from '../../../models/reserva/reserva.model';
import { Voo } from '../../../models/voo/voo.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ver-reserva',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ver-reserva.component.html',
  styleUrl: './ver-reserva.component.scss'
})
export class VerReservaComponent {
  @Input() reserva!: Reserva;
  @Input() voo!: Voo;

  constructor(public activeModal: NgbActiveModal) {}

  getVooOrigem(): string {
    return this.voo ? this.voo.aeroporto_origem : 'N/A';
  }

  getVooDestino(): string {
    return this.voo ? this.voo.aeroporto_destino : 'N/A';
  }
}