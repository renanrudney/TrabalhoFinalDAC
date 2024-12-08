import { Injectable } from '@angular/core';
import { Reserva } from '../models/reserva/reserva.model';
import { HistoricoReservaService } from './historico-reserva.service';
import { ClienteService } from './cliente.service';
import { TransacaoMilhasService } from './transacao-milhas.service';
import { HttpClient } from '@angular/common/http';
import { environment } from '../shared/environment/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {

  private baseUrl = `${environment.apiGatewayUrl}/reservas`;

  Reservas: Array<Reserva> = [
    new Reserva('AAA001','TADS0001',new Date('2024-10-15T14:00:00'),'RESERVADO',1,1,100,5),
    new Reserva('AAA002','TADS0007', new Date('2024-10-30T22:00:00'),'RESERVADO',1,1,500,0)
  ]; 

  constructor(private historicoReservaService: HistoricoReservaService, private clienteService: ClienteService, 
    private transacaoMilhasService: TransacaoMilhasService, private http: HttpClient) { }

  cancelarReserva(codigoReserva: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${codigoReserva}`);
    /*// Procura a reserva pelo código
    const reservaEncontrada = this.Reservas.find(reserva => reserva.codigoReserva === codigoReserva);
    
    // Se a reserva for encontrada, altera o estado
    if (reservaEncontrada) {
      //Mudança de estado da reserva
      const estadoAnterior: string = reservaEncontrada.estado;
      reservaEncontrada.estado = "CANCELADO";
      //Salvando mudança de estado no historico
      this.historicoReservaService.novoHistorico(reservaEncontrada.codigoReserva,estadoAnterior,reservaEncontrada.estado);
      //Devolvendo milhas do cliente
      this.clienteService.devolderMilhas(reservaEncontrada.milhasUsadas,reservaEncontrada.clienteId);
      //Registro da devolução
      this.transacaoMilhasService.novaTransacao(reservaEncontrada.clienteId,reservaEncontrada.milhasUsadas,"CANCELADO",reservaEncontrada.codigoReserva);
      console.log(`Estado da reserva ${codigoReserva} alterado para ${reservaEncontrada.estado}`);
    } else {
      console.log(`Reserva com código ${codigoReserva} não encontrada.`);
    }*/
  }

  novaReserva(codigoVoo: string, dataHora: Date, clienteId: number, qntdPassagens: number, custoTotal: number, milhasUsadas: number): string {
    const codigo: string = this.gerarCodigoReserva();
    this.Reservas.push(new Reserva (codigo,codigoVoo,dataHora,'RESERVADO',clienteId,qntdPassagens,custoTotal,milhasUsadas));
    return codigo;
  }

  getReserva(codigo: string): Reserva | null {
    return this.Reservas.find(reserva => reserva.codigoReserva == codigo) || null
  }

  getReservasByClienteId(clienteId: number): Observable<Reserva[]>{
    return this.http.get<Reserva[]>(`${this.baseUrl}/cliente/${clienteId}`);
  }

  embarque(codigoReserva: string, codigoVoo: string): string {
    const reservaFind: Reserva | null = this.getReserva(codigoReserva);
    if (reservaFind) {
      if (reservaFind.codigoVoo === codigoVoo){
        reservaFind.estado = "EMBARCADO";
        console.log(`Estado da reserva ${codigoReserva} alterado para EMBARCADO`);
        return "Cliente embarcado com sucesso!";
      } else {
        console.log(`Reserva com código ${codigoReserva} não é do voo ${codigoVoo}.`);
        return "Reserva não é deste voo!";
      }
    } else {
      console.log(`Reserva com código ${codigoReserva} não encontrada.`);
      return "Reserva não encontrada";
    }
  }
  
  gerarCodigoReserva(): string {
    const letras = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    const numeros = '0123456789';
    const randomLetra = () => letras[Math.floor(Math.random() * letras.length)];
    const randomNumero = () => numeros[Math.floor(Math.random() * numeros.length)];
    return `${randomLetra()}${randomLetra()}${randomLetra()}${randomNumero()}${randomNumero()}${randomNumero()}`;
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
