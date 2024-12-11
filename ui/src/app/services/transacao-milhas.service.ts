import { Injectable } from '@angular/core';
import { TransacaoMilhas } from '../models/transacaoMilhas/transacao-milhas.model';
import { Observable, map } from 'rxjs';
import { environment } from '../shared/environment/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TransacaoMilhasService {

  private baseUrl = `${environment.apiGatewayUrl}/milhas`;

  transactions: TransacaoMilhas[] = [
    new TransacaoMilhas('1', new Date(2024,0,1,14,30), 123345, 'entrada', 'COMPRA DE MILHAS'),
    new TransacaoMilhas('2', new Date(2024,2,1,9,0), 6789, 'saida', 'CWB->GRU','AAA001'),
    new TransacaoMilhas('3', new Date(2024,3,1,22,45), 23456, 'entrada', 'COMPRA DE MILHAS'),
    new TransacaoMilhas('4', new Date(2024,4,1,1,14), 9897, 'saida', 'GRU->CWB','AAA001')
  ];

  constructor(private http: HttpClient) {}

  getTransacoes(): Observable<TransacaoMilhas[]> {
    return this.http.get<TransacaoMilhas[]>(`${this.baseUrl}`);
  }

  getTransacoesCliente(clienteId: string): Observable<TransacaoMilhas[]> {
    return this.getTransacoes().pipe(
      map((transacoes) =>
        transacoes.filter((transacao) => transacao.clienteId === clienteId)
      )
    );
  }

  novaTransacao(clienteId: string, milhas: number, descricao: string, codigoReserva?: string): Observable<TransacaoMilhas> {
    const transacao: TransacaoMilhas = new TransacaoMilhas (clienteId,new Date(),milhas,"entrada",descricao, codigoReserva);
    return this.http.post<TransacaoMilhas>(this.baseUrl, transacao, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') || '' })});
  }
}