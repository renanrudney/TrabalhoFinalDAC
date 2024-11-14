import { Component, OnInit } from '@angular/core';
import { Voo } from '../../../models/voo/voo.model';
import { Cliente } from '../../../models/cliente/cliente.model';
import { VooService } from '../../../services/voo.service';
import { ReservaService } from '../../../services/reserva.service';
import { ClienteService } from '../../../services/cliente.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { TransacaoMilhasService } from '../../../services/transacao-milhas.service';
import { AeroportoService } from '../../../services/aeroporto.service';
import { Aeroporto } from '../../../models/aeroporto/aeroporto.model';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-efetuar-reserva',
  standalone: true,
  imports: [CommonModule,FormsModule,HttpClientModule],
  templateUrl: './efetuar-reserva.component.html',
  styleUrl: './efetuar-reserva.component.scss'
})
export class EfetuarReservaComponent implements OnInit{
  origem: string = '';
  destino: string = '';
  voos: Array<Voo> = [];
  vooSelecionado: Voo | null = null;
  cliente: Cliente | undefined = undefined;
  quantidade: number = 1;
  valorTotal: number = 0;
  milhasUsadas: number = 0;
  valorEmDinheiro: number = 0;
  aeroportos: Aeroporto[] = [];

  constructor(private vooService: VooService, private reservaService: ReservaService, private clienteService: ClienteService, 
    private authService: AuthService,private transacaoMilhas: TransacaoMilhasService, private aeroportoService: AeroportoService,
    private router: Router) {}

  ngOnInit() {
    const clienteId: number = Number(this.authService.getItem('userId'));
    this.cliente = this.clienteService.getClienteById(clienteId);
    this.aeroportos = this.aeroportoService.getAeroportos();
  }

  buscarVoos() {
    this.voos.push(...this.vooService.getVoosCompra(this.origem,this.destino));
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
    if (this.vooSelecionado && this.cliente) {
      const clienteId: number = Number(this.cliente.id);
      const codigo: string = this.reservaService.novaReserva(this.vooSelecionado.codigoVoo,this.vooSelecionado.dataHora,clienteId,this.quantidade,this.valorTotal,this.milhasUsadas);
      this.clienteService.substrairMilhas(this.milhasUsadas, clienteId);
      const descricao: string = this.vooSelecionado.origem + '->' + this.vooSelecionado.destino;
      this.transacaoMilhas.novaTransacao(clienteId,this.milhasUsadas,descricao,codigo);
      alert(`Reserva confirmada! Codigo da reserva: ${codigo}`);
      this.router.navigate(['/home-cliente']);
    } else {
      alert("Erro ao reservar, tente novamente");
    }
  }
}