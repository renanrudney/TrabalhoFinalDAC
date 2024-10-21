import { Injectable } from '@angular/core';
import { TransacaoMilhas } from '../models/transacaoMilhas/transacao-milhas.model';
import {Observable, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransacaoMilhasService {

  transactions: TransacaoMilhas[] = [
    new TransacaoMilhas('a@email.com', new Date(2024,0,1,14,30), 123345, 'entrada', 'COMPRA DE MILHAS'),
    new TransacaoMilhas('a@email.com', new Date(2024,2,1,9,0), 6789, 'saida', 'CWB->GRU'),
    new TransacaoMilhas('a@email.com', new Date(2024,3,1,22,45), 23456, 'entrada', 'COMPRA DE MILHAS'),
    new TransacaoMilhas('a@email.com', new Date(2024,4,1,1,14), 9897, 'saida', 'GRU->CWB')
  ];

  constructor() {}

  getTransacoes(): Observable<TransacaoMilhas[]> {
    return of(this.transactions);
  }

  novaTransacao(cliente: string, milhas: number, descricao: string): void {
    const transacao: TransacaoMilhas = new TransacaoMilhas (cliente,new Date(),milhas,"entrada",descricao);
    this.transactions.push(transacao);
  }
}