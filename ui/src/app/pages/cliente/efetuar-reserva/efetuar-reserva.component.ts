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

@Component({
  selector: 'app-efetuar-reserva',
  standalone: true,
  imports: [CommonModule,FormsModule],
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
  errorMessage: string = "";

  constructor(private vooService: VooService, private reservaService: ReservaService, private clienteService: ClienteService, 
    private authService: AuthService,private transacaoMilhas: TransacaoMilhasService, private aeroportoService: AeroportoService,
    private router: Router) {}

  ngOnInit() {
    const clienteId: string = this.authService.getItem('userId') || '';
    this.clienteService.getClienteById(clienteId).subscribe(
      (cliente) => {
        this.cliente = cliente;
        console.log('Cliente carregado:', cliente);
      },
      (error) => {
        console.error('Erro ao carregar cliente:', error);
        this.errorMessage = 'Erro ao carregar cliente, tente novamente';
      }
    );
    this.aeroportoService.getAeroportos().subscribe(
      (aeroportos) => {
        this.aeroportos = aeroportos;
        console.log('Aeroportos carregados:', aeroportos);
      },
      (error) => {
        console.error('Erro ao carregar aeroportos:', error);
        this.errorMessage = 'Erro ao carregar aeroportos, tente novamente';
      }
    );
  }

  buscarVoos() {
    this.vooService.getVoosCompra(this.origem,this.destino).subscribe(
      (voos) => {
        this.voos = voos;
        console.log('Voos para compra carregados:', voos);
      },
      (error) => {
        console.error('Erro ao carregar voos para compra:', error);
        this.errorMessage = 'Erro ao carregar voos para compra, tente novamente';
      }
    );
  }

  selecionarVoo(voo: Voo) {
    this.vooSelecionado = voo;
    this.calcularTotal();
  }

  calcularTotal() {
    if (this.vooSelecionado) {
      this.valorTotal = this.vooSelecionado.valor_passagem * this.quantidade;
      this.calcularDiferenca();
    }
  }

  calcularDiferenca() {
    this.valorEmDinheiro = this.valorTotal - (this.milhasUsadas*5)
  }

  confirmarReserva() {
    if (this.vooSelecionado && this.cliente) {
      const clienteId: string = this.cliente.id || '';
      this.reservaService.novaReserva(this.vooSelecionado.cod,this.vooSelecionado.data,clienteId,this.quantidade,this.valorTotal,this.milhasUsadas).subscribe(
        (reserva) => {
          // Sucesso
          alert(`Reserva confirmada! Código da reserva: ${reserva.codigoReserva}`);
          this.router.navigate(['/home-cliente']);
        },
        (error) => {
          // Erro
          console.error('Erro ao criar a reserva:', error);
          alert("Erro ao reservar, tente novamente");
        }
      );
    } else {
      alert("Erro ao reservar, tente novamente");
    }
  }
}