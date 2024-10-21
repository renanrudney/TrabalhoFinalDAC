import { Injectable } from '@angular/core';
import { Reserva } from '../models/reserva/reserva.model';
import { HistoricoReservaService } from './historico-reserva.service';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {

  Reservas: Array<Reserva> = [
    new Reserva('AAA001','TADS0001',new Date('2024-10-15T14:00:00'),'RESERVADO')
  ]; 

  constructor(private historicoReservaService: HistoricoReservaService) { }

  cancelarReserva(codigoReserva: string): void {
    // Procura a reserva pelo código
    const reservaEncontrada = this.Reservas.find(reserva => reserva.codigoReserva === codigoReserva);
    
    // Se a reserva for encontrada, altera o estado
    if (reservaEncontrada) {
      const estadoAnterior: string = reservaEncontrada.estado;
      reservaEncontrada.estado = "CANCELADO";
      this.historicoReservaService.novoHistorico(reservaEncontrada.codigoReserva,estadoAnterior,reservaEncontrada.estado);
      console.log(`Estado da reserva ${codigoReserva} alterado para ${reservaEncontrada.estado}`);
    } else {
      console.log(`Reserva com código ${codigoReserva} não encontrada.`);
    }
  }

  novaReserva(codigoVoo: string, dataHora: Date): void {
    const codigo: string = this.gerarCodigoReserva();
    this.Reservas.push(new Reserva (codigo,codigoVoo,dataHora,'RESERVADO'));
  }

  getReserva(codigo: string): Reserva | null {
    return this.Reservas.find(reserva => reserva.codigoReserva === codigo) || null
  }
  
  gerarCodigoReserva(): string {
    const letras = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    const numeros = '0123456789';
    const randomLetra = () => letras[Math.floor(Math.random() * letras.length)];
    const randomNumero = () => numeros[Math.floor(Math.random() * numeros.length)];
    return `${randomLetra()}${randomLetra()}${randomLetra()}${randomNumero()}${randomNumero()}${randomNumero()}`;
  }
}
