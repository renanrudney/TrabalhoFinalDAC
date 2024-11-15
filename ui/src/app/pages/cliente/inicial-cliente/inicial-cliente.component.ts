import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClienteService } from '../../../services/cliente.service';
import { Cliente } from '../../../models/cliente/cliente.model';
import { Reserva } from '../../../models/reserva/reserva.model';
import { Voo } from '../../../models/voo/voo.model';
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
  voos: Array<Voo> = [];
  errorMessage: string = "";

  constructor(private clienteService: ClienteService, private vooService: VooService, private reservaService: ReservaService, private modalService: NgbModal, private authService: AuthService) {}

  ngOnInit(): void {
    const clienteId: number | null = Number(this.authService.getItem('userId'));
    if (clienteId) {
      this.clienteLogado = this.clienteService.getClienteById(clienteId);
      this.carregarVoos();
      this.carregarReservas(clienteId);
    }
  }

  carregarVoos(): void {
    this.vooService.getVoos().subscribe(
      (data) => this.voos = data,
      (error) => this.errorMessage = 'Erro ao carregar voos, tente novamente.'
    );
  }

  carregarReservas(clienteId: number): void {
    this.reservaService.getReservasByClienteId(clienteId).subscribe(
      (data) => {
        this.Reservas = data;

        // Ordenar por dataHora após a resposta da API
        this.Reservas.sort((a, b) => new Date(a.dataHora).getTime() - new Date(b.dataHora).getTime());
      },
      (error) => {
        console.error('Erro ao carregar reservas', error);
        this.errorMessage = 'Erro ao carregar reservas, tente novamente.'
      }
    );
  }

  getVooOrigem(codigoVoo: string): string {
    const voo = this.voos.find(v => v.codigoVoo === codigoVoo);
    return voo ? voo.origem : 'N/A';
  }
  
  getVooDestino(codigoVoo: string): string {
    const voo = this.voos.find(v => v.codigoVoo === codigoVoo);
    return voo ? voo.destino : 'N/A';
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
