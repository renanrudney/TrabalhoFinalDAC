import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClienteService } from '../../../services/cliente.service';
import { Cliente } from '../../../models/cliente/cliente.model';
import { Reserva } from '../../../models/reserva/reserva.model';
import { VooService } from '../../../services/voo.service';
import { ReservaService } from '../../../services/reserva.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { VerReservaComponent } from '../ver-reserva/ver-reserva.component';
import { CancelarReservaComponent } from '../cancelar-reserva/cancelar-reserva.component';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-tela-inicial-cliente',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './inicial-cliente.component.html',
  styleUrls: ['./inicial-cliente.component.scss']
})
export class InicialClienteComponent implements OnInit
{
  clienteLogado: Cliente | undefined = undefined;
  Reservas: Array<Reserva> = [];

 constructor(private clienteService: ClienteService, private vooService: VooService, private reservaService: ReservaService, private modalService: NgbModal, private authService: AuthService) {}

  ngOnInit(): void {
    const clienteId: number | null = Number(this.authService.getItem('userId'));
    if (clienteId){
      this.clienteLogado = this.clienteService.getClienteById(clienteId);
      this.Reservas.push(...this.reservaService.getReservasByClienteId(clienteId));
      //Ordenar por dataHora
      this.Reservas.sort((a, b) => new Date(a.dataHora).getTime() - new Date(b.dataHora).getTime());
    }
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
