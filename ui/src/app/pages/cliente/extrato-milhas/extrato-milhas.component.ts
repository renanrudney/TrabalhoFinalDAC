import { Component } from '@angular/core';
import { TransacaoMilhas } from '../../../models/transacaoMilhas/transacao-milhas.model';
import { TransacaoMilhasService } from '../../../services/transacao-milhas.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-extrato-milhas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './extrato-milhas.component.html',
  styleUrl: './extrato-milhas.component.scss'
})
export class ExtratoMilhasComponent {

  transacoes: TransacaoMilhas[] = [];

  constructor(private transacaoMilhasService: TransacaoMilhasService) {}

  ngOnInit(): void {
    this.carregarTransacoes();
  }

  carregarTransacoes(): void {
    this.transacaoMilhasService.getTransacoes().subscribe(transacoes => {
      this.transacoes = transacoes;
    });
  }
}
