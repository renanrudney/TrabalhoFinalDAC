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
    const clienteId: number = Number(this.authService.getItem('userId'));
    console.log(`Cliente id no checkin:${clienteId}`);
    this.reservaService.getReservasByClienteId(clienteId).subscribe(
      (data) => {
        this.reservasCliente = data;

        // Ordenar por dataHora após a resposta da API
        this.reservasCliente.sort((a, b) => new Date(a.dataHora).getTime() - new Date(b.dataHora).getTime());
      },
      (error) => {
        console.error('Erro ao carregar reservas', error);
        this.errorMessage = "Erro ao carregar reservas, tente novamente."
      }
    );
    console.log(this.reservasCliente);
    this.voosProximos.push(...this.vooService.getVoosProximasHoras());
    console.log(this.voosProximos)
    this.voos.push(...this.voosClienteProximas48H());
    console.log(this.voos);
  }

  voosClienteProximas48H(): Array<Voo> {
    return this.voosProximos.filter(voo => 
      this.reservasCliente.some(reserva => reserva.codigoVoo === voo.codigoVoo)
    );
  }
}