import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

interface Reserva {
  id: number;
  dataHora: Date;
  aeroportoOrigem: string;
  aeroportoDestino: string;
}

@Component({
  selector: 'app-home-cliente',
  standalone: true,
  imports: [CommonModule,FormsModule, RouterModule],
  templateUrl: './home-cliente.component.html',
  styleUrl: './home-cliente.component.scss'
})

export class HomeClienteComponent {
  saldoMilhas: number = 1500; // Exemplo de saldo em milhas
  reservas: Reserva[] = [
    { id: 1, dataHora: new Date(2024, 9, 5, 14, 30), aeroportoOrigem: 'CWB', aeroportoDestino: 'GRU' },
    { id: 2, dataHora: new Date(2024, 9, 6, 16, 0), aeroportoOrigem: 'GRU', aeroportoDestino: 'CWB' },
    { id: 3, dataHora: new Date(2024, 9, 7, 10, 15), aeroportoOrigem: 'CWB', aeroportoDestino: 'SDU' },
  ];
  
  verReserva(id: number): void {
    console.log(`Ver reserva com ID: ${id}`);
    // Lógica para ver detalhes da reserva
  }

  cancelarReserva(id: number): void {
    console.log(`Cancelar reserva com ID: ${id}`);
    // Lógica para cancelar a reserva
    this.reservas = this.reservas.filter(reserva => reserva.id !== id);
  }
}