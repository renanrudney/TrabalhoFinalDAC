import { Injectable } from '@angular/core';
import { Reserva } from '../models/reserva/reserva.model';
import { HistoricoReservaService } from './historico-reserva.service';
import { ClienteService } from './cliente.service';
import { TransacaoMilhasService } from './transacao-milhas.service';
import { HttpClient } from '@angular/common/http';
import { environment } from '../shared/environment/environment';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {

  private baseUrl = `${environment.apiGatewayUrl}/reservas`;

  Reservas: Array<Reserva> = [
    new Reserva('TADS0001',new Date('2024-10-15T14:00:00'),'RESERVADO',1,1,100,5,'AAA001'),
    new Reserva('TADS0007', new Date('2024-10-30T22:00:00'),'RESERVADO',1,1,500,0,'AAA002')
  ]; 

  constructor(private historicoReservaService: HistoricoReservaService, private clienteService: ClienteService, 
    private transacaoMilhasService: TransacaoMilhasService, private http: HttpClient) { }

  cancelarReserva(codigoReserva: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${codigoReserva}/cancelar`);
  }

  novaReserva(codigoVoo: string, dataHora: Date, clienteId: number, qntdPassagens: number, custoTotal: number, milhasUsadas: number): Observable<Reserva> {
    const reserva: Reserva = new Reserva (codigoVoo,dataHora,'RESERVADO',clienteId,qntdPassagens,custoTotal,milhasUsadas);
    return this.http.post<Reserva>(this.baseUrl, reserva);
  }

  getReserva(codigo: string): Observable<Reserva> {
    return this.http.get<Reserva>(`${this.baseUrl}/${codigo}`);
  }

  getReservas(): Observable<Reserva[]>{
    return this.http.get<Reserva[]>(`${this.baseUrl}`);
  }

  getReservasByClienteId(clienteId: number): Observable<Reserva[]> {
    return this.getReservas().pipe(
      map((reservas: Reserva[]) => 
        reservas.filter((reserva: Reserva) => reserva.clienteId === clienteId)
      )
    );
  }

  embarque(reserva: string, codigo: string): Observable<Reserva>{
    return this.http.post<Reserva>(`${this.baseUrl}/${codigo}/embarque`, reserva);
  }

  vooCancelado(codigoVoo: string): void {
    this.Reservas.forEach(reserva => {
        if (reserva.codigoVoo === codigoVoo) {
            reserva.estado = "CANCELADO VOO";
        }
    });
  }

  vooRealizado(codigoVoo: string): void {
    this.Reservas.forEach(reserva => {
        if (reserva.codigoVoo === codigoVoo) {
          if (reserva.estado === "EMBARCADO"){
            reserva.estado = "REALIZADO";
          } else {
            reserva.estado === "NÃO REALIZADO"
          }
        }
    });
  }
}
