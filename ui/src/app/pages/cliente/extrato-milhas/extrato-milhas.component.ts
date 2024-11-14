import { Component, OnInit } from '@angular/core';
import { TransacaoMilhas } from '../../../models/transacaoMilhas/transacao-milhas.model';
import { TransacaoMilhasService } from '../../../services/transacao-milhas.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ClienteService } from '../../../services/cliente.service';
import { AuthService } from '../../../services/auth.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-extrato-milhas',
  standalone: true,
  imports: [CommonModule, RouterModule, HttpClientModule],
  templateUrl: './extrato-milhas.component.html',
  styleUrl: './extrato-milhas.component.scss'
})
export class ExtratoMilhasComponent implements OnInit{
  saldoMilhas: number = 1500; // Exemplo de saldo em milhas
  transacoes: TransacaoMilhas[] = [];

  constructor(private transacaoMilhasService: TransacaoMilhasService, private clienteService: ClienteService, private authService: AuthService) {}

  ngOnInit(): void {
    const clienteId: number = Number(this.authService.getItem('userId'))
    this.saldoMilhas = this.clienteService.getClienteMilhas(clienteId);
    this.transacoes.push(...this.transacaoMilhasService.getTransacoesCliente(clienteId));
  }

  voltar():void {
    window.history.back();
  }
}
