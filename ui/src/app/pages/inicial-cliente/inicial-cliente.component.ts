import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { AuthService } from '../../services/auth.service';
import { ClienteService } from '../../services/cliente.service';
import { Cliente } from '../../models/cliente/cliente.model';
import { Reserva } from '../../models/reserva/reserva.model';
import { VooService } from '../../services/voo.service';
import { Voo } from '../../models/voo/voo.model';
import { ReservaService } from '../../services/reserva.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { VerReservaComponent } from '../ver-reserva/ver-reserva.component';
import { CancelarReservaComponent } from '../cliente/cancelar-reserva/cancelar-reserva.component';

@Component({
  selector: 'app-tela-inicial-cliente',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './inicial-cliente.component.html',
  styleUrls: ['./inicial-cliente.component.scss']
})
export class InicialClienteComponent {
  clienteLogado: Cliente | undefined = undefined;
  Reservas: Array<Reserva> = [];
  Voos: Array<Voo> = [];

 constructor(private authService: AuthService, private clienteService: ClienteService, private vooService: VooService, private reservaService: ReservaService, private modalService: NgbModal) {}

  ngOnInit(): void {
    this.clienteLogado = this.clienteService.getClienteByLogin(this.authService.getUserLogin());
    this.Voos.push(...this.vooService.getVoos());
  }

  getVooOrigem(codigoVoo: string): string | undefined {
    return this.vooService.getOrigem(codigoVoo);
  }

  getVooDestino(codigoVoo: string): string | undefined{
    return this.vooService.getDestino(codigoVoo);
  }

  abrirModalVerReserva(reserva: Reserva) {
    const modalRef = this.modalService.open(VerReservaComponent);
    modalRef.componentInstance.reserva = reserva;
  }

  abrirModalCancelarReserva(reserva: Reserva) {
    const modalRef = this.modalService.open(CancelarReservaComponent);
    modalRef.componentInstance.reserva = reserva;
  }
}
