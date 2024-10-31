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

  constructor(private vooService: VooService, private reservaService: ReservaService, private authService: AuthService) {}

  ngOnInit(): void {
    const clienteId: number = Number(this.authService.getItem('userId'));
    console.log(`Cliente id no checkin:${clienteId}`);
    this.reservasCliente.push(...this.reservaService.getReservasByClienteId(clienteId));
    console.log(this.reservasCliente);
    this.voosProximos.push(...this.vooService.getVoosProximasHoras());
    console.log(this.voosProximos)
    this.voos.push(...this.voosClienteProximas48H());
    console.log(this.voos);
    //Ordena os voos na lista
    this.voos.sort((a, b) => new Date(b.dataHora).getTime() - new Date(a.dataHora).getTime());
  }

  voosClienteProximas48H(): Array<Voo> {
    return this.voosProximos.filter(voo => 
      this.reservasCliente.some(reserva => reserva.codigoVoo === voo.codigoVoo)
    );
  }
}