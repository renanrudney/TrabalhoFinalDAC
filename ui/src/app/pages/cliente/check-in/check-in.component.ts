import { Component } from '@angular/core';
import { VooService } from '../../../services/voo.service';
import { Voo } from '../../../models/voo/voo.model';
import { CommonModule } from '@angular/common';
import { ClienteService } from '../../../services/cliente.service';
import { AuthService } from '../../../services/auth.service';
import { Cliente } from '../../../models/cliente/cliente.model';

@Component({
  selector: 'app-check-in',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './check-in.component.html',
  styleUrl: './check-in.component.scss'
})
export class CheckInComponent {

  voos: Array<Voo> = [];

    constructor(private vooService: VooService, private authService: AuthService, private clienteService: ClienteService) {}

  ngOnInit(): void {
    //this.clienteLogado = this.clienteService.getClienteByLogin(this.authService.getUserLogin());
    this.carregarVoosProximos();
  }

  carregarVoosProximos(): void {
    // Filtrar voos que acontecem nas próximas 48 horas
    this.voos = this.vooService.getVoosProximasHoras();
  }
}