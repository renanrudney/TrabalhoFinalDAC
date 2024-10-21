import { Component } from '@angular/core';
import { Voo } from '../models/voo/voo.model';
import { Cliente } from '../models/cliente/cliente.model';
import { VooService } from '../services/voo.service';
import { ReservaService } from '../services/reserva.service';
import { ClienteService } from '../services/cliente.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { TransacaoMilhasService } from '../services/transacao-milhas.service';

@Component({
  selector: 'app-efetuar-reserva',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './efetuar-reserva.component.html',
  styleUrl: './efetuar-reserva.component.scss'
})
export class EfetuarReservaComponent {
  origem: string = '';
  destino: string = '';
  voos: Array<Voo> = [];
  vooSelecionado: Voo | null = null;
  cliente: Cliente | null = null;
  quantidade: number = 1;
  valorTotal: number = 0;
  milhasNecessarias: number = 0;
  milhasUsadas: number = 0;
  valorEmDinheiro: number = 0;

  constructor(private vooService: VooService, private reservaService: ReservaService, private clienteService: ClienteService, private authService: AuthService,private transacaoMilhas: TransacaoMilhasService) {}

  ngOnInit() {
    this.clienteService.getClienteByLogin(this.authService.getUserLogin());
  }

  buscarVoos() {
    this.voos.push(...this.vooService.getVoosCompra());
  }

  selecionarVoo(voo: Voo) {
    this.vooSelecionado = voo;
    this.calcularTotal();
  }

  calcularTotal() {
    if (this.vooSelecionado) {
      this.valorTotal = this.vooSelecionado.valorPassagem * this.quantidade;
      this.calcularDiferenca();
    }
  }

  calcularDiferenca() {
    this.valorEmDinheiro = this.valorTotal - (this.milhasUsadas*5)
  }

  confirmarReserva() {
    if (this.vooSelecionado) {
      this.reservaService.novaReserva(this.vooSelecionado.codigoVoo,this.vooSelecionado.dataHora);
      if (this.cliente) {
        this.clienteService.substrairMilhas(this.milhasUsadas, this.cliente.email);
        const descricao: string = this.vooSelecionado.origem + '->' + this.vooSelecionado.destino;
        this.transacaoMilhas.novaTransacao(this.cliente.email,this.milhasUsadas,descricao);
      }
    }
    console.log('Reserva confirmada!');
  }
}