import { Component, OnInit } from '@angular/core';
import { TransacaoMilhas } from '../../../models/transacaoMilhas/transacao-milhas.model';
import { TransacaoMilhasService } from '../../../services/transacao-milhas.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ClienteService } from '../../../services/cliente.service';
import { AuthService } from '../../../services/auth.service';
import { Cliente } from '../../../models/cliente/cliente.model';

@Component({
  selector: 'app-extrato-milhas',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './extrato-milhas.component.html',
  styleUrl: './extrato-milhas.component.scss'
})
export class ExtratoMilhasComponent implements OnInit{
  clienteLogado: Cliente | undefined = undefined;
  transacoes: TransacaoMilhas[] = [];
  errorMessage: string = "";

  constructor(private transacaoMilhasService: TransacaoMilhasService, private clienteService: ClienteService, private authService: AuthService) {}

  ngOnInit(): void {
    const clienteId: string  = this.authService.getItem('clienteId') || '';
  
    // Assinando o Observable para obter o saldo de milhas
    this.clienteService.getClienteById(clienteId).subscribe(
      (data) => {
        this.clienteLogado = data;
      },
      (error) => {
        console.error('Erro ao obter saldo de milhas:', error);
        this.errorMessage = "Erro ao obter saldo de milhas, tente novamente.";
      }
    );
  
    // Assinando o Observable para obter as transações de milhas
    this.transacaoMilhasService.getTransacoesCliente(clienteId).subscribe(
      (transacoes) => {
        this.transacoes.push(...transacoes); // Usa espalhamento após os dados serem obtidos
      },
      (error) => {
        console.error('Erro ao obter transações de milhas:', error);
        this.errorMessage = "Erro ao obter transações de milhas, tente novamente.";
      }
    );
  }
  

  voltar():void {
    window.history.back();
  }
}
