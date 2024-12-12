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
  constructor(private http: HttpClient) {}

  getTransacoes(): Observable<TransacaoMilhas[]> {
    return this.http.get<TransacaoMilhas[]>(`${this.baseUrl}`);
  }

  getTransacoesCliente(clienteId: string): Observable<TransacaoMilhas[]> {
    return this.getTransacoes().pipe(
      map((transacoes) => {
        console.log(transacoes)
        return transacoes.filter((transacao) => transacao.idCliente === clienteId) }
      )
    );
  }

  novaTransacao(clienteId: string, milhas: number, descricao: string, codigoReserva?: string): Observable<TransacaoMilhas> {
    const transacao: TransacaoMilhas = new TransacaoMilhas (clienteId,new Date(),milhas,true,descricao, codigoReserva);
    return this.http.post<TransacaoMilhas>(this.baseUrl, transacao, { headers: new HttpHeaders({ 'Authorization': "Bearer " + localStorage.getItem('authToken') })});
  }
}