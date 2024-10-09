import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-cancelar-reserva',
  standalone: true,
  imports: [],
  templateUrl: './cancelar-reserva.component.html',
  styleUrl: './cancelar-reserva.component.scss'
})
export class CancelarReservaComponent {

  constructor(public activeModal: NgbActiveModal) {}

  cancelarReserva(): void {
    
  }
}
