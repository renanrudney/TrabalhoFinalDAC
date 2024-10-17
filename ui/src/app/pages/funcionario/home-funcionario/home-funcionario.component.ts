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
  selector: 'app-home-funcionario',
  standalone: true,
  imports: [CommonModule,FormsModule, RouterModule],
  templateUrl: './home-funcionario.component.html',
  styleUrl: './home-funcionario.component.scss'
})

export class HomeFuncionarioComponent {
  reservas: Reserva[] = [
    { id: 1, dataHora: new Date(2024, 9, 5, 14, 30), aeroportoOrigem: 'CWB', aeroportoDestino: 'GRU' },
    { id: 2, dataHora: new Date(2024, 9, 6, 16, 0), aeroportoOrigem: 'GRU', aeroportoDestino: 'CWB' },
    { id: 3, dataHora: new Date(2024, 9, 7, 10, 15), aeroportoOrigem: 'CWB', aeroportoDestino: 'SDU' },
  ];
  
  verConfirmacao(id: number): void {
    console.log(`Ver confirmação com ID: ${id}`);
    // Lógica para ver lista de confirmação
  }

  realizarVoo(id: number): void {
    console.log(`Cancelar Voo com ID: ${id}`);
    // Lógica para realziar voo
    this.reservas = this.reservas.filter(reserva => reserva.id !== id);
  }

  cancelarVoo(id: number): void {
    console.log(`Cancelar Voo com ID: ${id}`);
    // Lógica para cancelar voo
    this.reservas = this.reservas.filter(reserva => reserva.id !== id);
  }
}