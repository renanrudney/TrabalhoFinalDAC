import { Component, OnInit } from '@angular/core';
import { VooService } from '../../../services/voo.service';
import { Voo } from '../../../models/voo/voo.model';
import { CommonModule } from '@angular/common';
import { ReservaService } from '../../../services/reserva.service';
import { Reserva } from '../../../models/reserva/reserva.model';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-check-in',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './check-in.component.html',
  styleUrl: './check-in.component.scss'
})
export class CheckInComponent implements OnInit {

  voos: Array<Voo> = [];
  reservasCliente: Array<Reserva> = [];
  voosProximos: Array<Voo> = [];
  errorMessage: string = "";

  constructor(private vooService: VooService, private reservaService: ReservaService, private authService: AuthService) {}

  ngOnInit(): void {
    const clienteId: string = this.authService.getItem('userId') || '';
    this.carregarReservas(clienteId);
    this.carregarVoosProximos();
    this.voos.push(...this.voosClienteProximas48H());
    // Ordenar por dataHora após a resposta da API
    this.voos.sort((a, b) => new Date(a.data).getTime() - new Date(b.data).getTime());
  }

  carregarReservas(clienteId: string): void {
    this.reservaService.getReservasByClienteId(clienteId).subscribe(
      (data) => {
        this.reservasCliente = data;
      },
      (error) => {
        console.error('Erro ao carregar reservas', error);
        this.errorMessage = "Erro ao carregar reservas, tente novamente."
      }
    );
  }

  carregarVoosProximos(): void {
    this.vooService.getVoosProximasHoras().subscribe(
      (data) => {
        this.voosProximos = data;
      },
      (error) => {
        console.error('Erro ao carregar voos proximas 48h', error);
        this.errorMessage = "Erro ao carregar voos proximas 48h, tente novamente."
      }
    )
  }

  voosClienteProximas48H(): Array<Voo> {
    return this.voosProximos
    // return this.voosProximos.filter(voo => 
    //   this.reservasCliente.some(reserva => reserva.codVoo === voo.cod)
    // );
  }
}