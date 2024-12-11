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
    const login: string | null = this.authService.getItem('login');
    if (login) {
      this.clienteService.getClienteByEmail(encodeURIComponent(login)).subscribe(
        (cliente) => {
          if (cliente) {
            this.clienteLogado = cliente; // Atribui o cliente retornado
            this.carregarReservas(cliente.id as string);
          } else {
            console.error('Cliente não encontrado.');
          }
        },
        (error) => {
          console.error('Erro ao buscar cliente:', error);
        }
      );
    }
    this.carregarVoos();
  }

  carregarVoos(): void {
    console.log('voos')
    this.vooService.getVoos().subscribe(
      (data) => this.voos = data,
      (error) => {
        console.error('Erro ao carregar voos', error);
        this.errorMessage = 'Erro ao carregar voos, tente novamente.';
      }
    );
  }

  carregarReservas(clienteId: string): void {
    this.reservaService.getReservasByClienteId(this.clienteLogado?.id as string).subscribe(
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
    const voo = this.voos.find(v => v.cod === codigoVoo);
    return voo ? voo.aeroporto_origem : 'N/A';
  }
  
  getVooDestino(codigoVoo: string): string {
    const voo = this.voos.find(v => v.cod === codigoVoo);
    return voo ? voo.aeroporto_destino : 'N/A';
  }

  getVoo(codigoVoo: string): Voo | undefined{
    const voo = this.voos.find(v => v.cod === codigoVoo);
    if (voo){
      return voo;
    } else 
    return undefined;
  }

  abrirModalVerReserva(reserva: Reserva) {
    const modalRef = this.modalService.open(VerReservaComponent);
    modalRef.componentInstance.reserva = reserva;
    modalRef.componentInstance.voo = this.getVoo(reserva.codigoVoo);
  }

  abrirModalCancelarReserva(reserva: Reserva) {
    const modalRef = this.modalService.open(CancelarReservaComponent);
    modalRef.componentInstance.reserva = reserva;
    modalRef.componentInstance.voo = this.getVoo(reserva.codigoVoo);
  }
}
